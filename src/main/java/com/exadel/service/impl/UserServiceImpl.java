package com.exadel.service.impl;

import com.exadel.exception.TrainerNotFoundException;
import com.exadel.exception.UserHasNotMentoringTrainingsException;
import com.exadel.exception.UserHasNotVisitingTrainingsException;
import com.exadel.exception.UserNotFoundException;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.*;
import com.exadel.repository.UserRepository;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = {UserNotFoundException.class, UserHasNotVisitingTrainingsException.class})
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User getUserById(String id) {
        try {
            long userId = Long.parseLong(id);
            return getUserById(userId);
        } catch (NumberFormatException ex) {
            throw new UserNotFoundException(id);
        }
    }


    public User getUserById(long id) {
        User user = userRepository.findOne(id);
        if (user != null) {
            return user;
        }
        else {
            throw new UserNotFoundException(String.valueOf(id));
        }
    }
    public User getUserByLogin(String login){
        long id = this.jdbcTemplate.queryForObject(
                "select user_id from authentification where login = ?",
                Long.class, login);
        User user = userRepository.findOne(id);
        if (user != null) {
            return user;
        }
        else {
            throw new UserNotFoundException(String.valueOf(id));
        }
    }
    @Override
    public ExternalTrainer getTrainerById(String id) {
        User trainer = getUserById(id);
        if (trainer.getRole() != UserRole.EXTERNAL_VISITOR)
            return (ExternalTrainer)trainer;

        throw new TrainerNotFoundException(id);
    }

    @Override
    public Employee getEmployeeById(String id) {
        User employee = getUserById(id);
        if (employee.getRole() == UserRole.ADMIN || employee.getRole() == UserRole.EMPLOYEE)
            return (Employee)employee;

        throw new TrainerNotFoundException(id);
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findOneByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }
        else {
            throw new UserNotFoundException(email);
        }
    }

    @Override
    public Collection<User> getAllUsers() {
        return userRepository.findAll(new Sort("surname"));
    }

    @Override
    public void addUser(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    public void updateUser(User user) {
        if (userRepository.exists(user.getId())) {
            userRepository.save(user);
        }
        else
            throw new UserNotFoundException(String.valueOf(user.getId()));
    }

    @Override
    public List<Training> getVisitingTrainings(String id) {
        User user = getUserById(id);
        UserRole role = user.getRole();

        if (role == UserRole.ADMIN || role == UserRole.EMPLOYEE)
            return ((Employee)user).getVisitingTrainings();
        else if (role == UserRole.EXTERNAL_VISITOR)
            return ((ExternalVisitor)user).getVisitingTrainings();
        else
            throw new UserHasNotVisitingTrainingsException(String.valueOf(id));
    }

    @Override
    public List<Training> getMentoringTrainings(String id) {
        User user = getUserById(id);
        UserRole role = user.getRole();

        if (role == UserRole.ADMIN || role == UserRole.EMPLOYEE || role == UserRole.EXTERNAL_TRAINER)
            return ((ExternalTrainer)user).getMentoringTrainings();
        else
            throw new UserHasNotMentoringTrainingsException(String.valueOf(id));
    }
    public long getCurrentId() {
    org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String currentName = user.getUsername();
    long id = this.jdbcTemplate.queryForObject(
            "select user_id from authentification where login = ?",
            Long.class,currentName);
    /*String name = this.jdbcTemplate.queryForObject(
            "select name from users where id = ?",
            String.class,id);*/
    return id;
    }

    public static boolean hasRole(Integer role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            if( role==Integer.parseInt(authority.getAuthority())) {
                hasRole=true;
                break;
            }
        }
        return hasRole;
    }

}
