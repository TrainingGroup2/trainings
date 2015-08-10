package com.exadel.controller;

import com.exadel.dto.UserDTO;
import com.exadel.model.entity.user.ExternalTrainer;
import com.exadel.model.entity.user.Authentification;
import com.exadel.model.entity.user.User;
import com.exadel.search.UserSearch;
import com.exadel.service.AuthentificationService;
import com.exadel.service.UserService;
import com.exadel.service.impl.EmailMessages;
import com.exadel.service.impl.SmtpMailSender;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.NoSuchElementException;



@PreAuthorize("hasRole('0')")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SmtpMailSender smtpMailSender;
    @Autowired
    private EmailMessages emailMessages;

    private AuthentificationService authentificationService;
    @Autowired
    SessionFactory sessionFactory;

    public static int size;

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserSearch userSearch;

public String generatePass(UserDTO userDTO) {
     Long id = userDTO.getId();
     String password = "";
     BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
     String hashedPassword = passwordEncoder.encode(password);
     Authentification authentification = new Authentification(hashedPassword,userDTO.getId(),userDTO.getEmail());
     authentificationService.addAuthentification(authentification);
     return password;
}

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/newTrainer", method = RequestMethod.POST)
    public void addExternalTrainer(@RequestBody UserDTO trainerDTO) {
        ExternalTrainer trainer = (ExternalTrainer)userService.addUser(new ExternalTrainer(trainerDTO));

        String password = UUID.randomUUID().toString().substring(0, 16);
        smtpMailSender.send(trainer.getEmail(), "Exadel-Trainings System", emailMessages.newExternalTrainer(trainer, password));
        long id = userService.addUserExt(new User(trainerDTO));
        trainerDTO.setId(id);
        String pass=generatePass(trainerDTO);
                //TODO: send password
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/newVisitor", method = RequestMethod.POST)
    public void addExternalVisitor(@RequestBody UserDTO visitorDTO) {
        userService.addUserExt(new User(visitorDTO));
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/editTrainer", method = RequestMethod.PUT)
    public void editExternalTrainer(@RequestBody UserDTO trainerDTO) {
        User user = new User(trainerDTO);
        userService.updateUser(user);
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/editVisitor", method = RequestMethod.PUT)
    public void editExternalVisitor(@RequestBody UserDTO visitorDTO) {
        User user = new User(visitorDTO);
        userService.updateUser(user);
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(method = RequestMethod.GET)
    public List<UserDTO> showUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }

    @PreAuthorize("hasRole('0')")
    @RequestMapping(value = "/trainers", method = RequestMethod.GET)
    public List<UserDTO> showTrainers() {
        List<User> users = userService.getTrainers();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }

    @RequestMapping(value = "/pages/count/{pageNumber}", method = RequestMethod.GET)
    public Integer getCount(@PathVariable Integer pageNumber, @RequestParam Integer size, @RequestParam String sortParam, @RequestParam (required = false) boolean isReversed) {
        return this.size;
    }

    @Transactional
    @RequestMapping(value = "/pages/{pageNumber}", method = RequestMethod.GET)
    public List<UserDTO> getUsersPaging(@PathVariable Integer pageNumber, @RequestParam Integer size,
                                        @RequestParam (required = false)String name,
                                        @RequestParam (required = false)String surname,
                                        @RequestParam (required = false)String phone,
                                        @RequestParam (required = false)String email,
                                        @RequestParam (required = false) boolean isReversed,
                                        @RequestParam (required = false) String sortParam) {

        this.size=0;
        List<User> users = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(User.class);
        Criterion nameCriterion, surnameCriterion, phoneCriterion, emailCriterion;
        List<User> userList = new ArrayList<>();
        if(name != null) {
          nameCriterion = userSearch.searchByName(name);
            criteria.add(nameCriterion);
        }
        if(surname != null) {
            surnameCriterion = userSearch.searchBySurname(surname);
            criteria.add(surnameCriterion);
        }
        if(phone != null) {
            phoneCriterion = userSearch.searchByPhone(phone);
            criteria.add(phoneCriterion);
        }
        if(email != null) {
            emailCriterion = userSearch.searchByEmail(email);
            criteria.add(emailCriterion);
        }
        if(sortParam!=null) {
            Sort sort = new Sort(
                    new SortField(sortParam, SortField.Type.STRING));

            if (isReversed) {
                criteria.addOrder(Order.desc(sortParam));
            } else
                criteria.addOrder(Order.asc(sortParam));
        }
            this.size = criteria.list().size();
            if (this.size < size) {
                this.size = 1;
            } else
                this.size = this.size / size;
            criteria.setMaxResults(size);
            criteria.setFirstResult(pageNumber);
            users = criteria.list();
            session.close();

        if(name == null && surname==null && email ==null && phone==null) {
            Page<User> page = userService.getUsers(pageNumber, size,sortParam,isReversed);
            users = page.getContent();
            this.size = page.getTotalPages();
        }
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(new UserDTO(user));
        }
        return userDTOs;
    }
}