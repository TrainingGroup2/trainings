package com.exadel.service.impl;

import com.exadel.exception.TrainerNotFoundException;
import com.exadel.exception.UserHasNotMentoringTrainingsException;
import com.exadel.exception.UserHasNotVisitingTrainingsException;
import com.exadel.exception.UserNotFoundException;
import com.exadel.model.entity.feedback.UserFeedback;
import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.*;
import com.exadel.repository.UserRepository;
import com.exadel.service.UserFeedbackService;
import com.exadel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    UserFeedbackService userFeedbackService;

    @Override
    public Long getUserIdByFeedbackId(String id){
        long userId = this.jdbcTemplate.queryForObject(
                "select user_id from user_feedbacks where id = ?",
                Long.class, Long.parseLong(id));
       return userId;
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
    public List<User> getAllUsers() {
        return userRepository.findAll(new Sort("surname"));
    }

    @Override
    public long addUserExt(User user) {
        userRepository.saveAndFlush(user);
        return user.getId();
    }

    @Override
    @Modifying
    public void updateUser(User user) {
        User oldUser = userRepository.findOne(user.getId());
        oldUser.update(user);
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
    return id;
    }

    @Override
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> getTrainers() {
        return userRepository.findByRoleNot(UserRole.EXTERNAL_VISITOR);
    }

    public Page<User> getUsers(Integer first, Integer size, String sort, boolean isReversed) {
        Integer pageNumber = first / size;
        Sort.Direction direction;
        PageRequest request;
        if(isReversed) {
             direction = Sort.Direction.DESC;
        }
        else
        direction =  Sort.Direction.ASC;
        if(sort!=null) {
            request =
                    new PageRequest(pageNumber, size, direction, sort);
        }
        else
            request =
                    new PageRequest(pageNumber, size);
        return userRepository.findAll(request);
    }

    public User addUser(User user) {
        return userRepository.saveAndFlush(user);
    }
}
