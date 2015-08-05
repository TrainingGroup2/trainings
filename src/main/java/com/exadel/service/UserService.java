package com.exadel.service;


import com.exadel.model.entity.training.Training;
import com.exadel.model.entity.user.Employee;
import com.exadel.model.entity.user.ExternalTrainer;
import com.exadel.model.entity.user.User;
import com.exadel.model.entity.user.UserRole;

import java.util.Collection;
import java.util.List;
public interface UserService {

    User getUserById(String id);

    User getUserById(long id);

    User getUserByLogin(String login);

    ExternalTrainer getTrainerById(String id);

    Employee getEmployeeById(String id);

    Collection<User> getAllUsers();

    void addUser(User user);

    void updateUser(User user);

    List<Training> getVisitingTrainings(String id);

    List<Training> getMentoringTrainings(String id);

    long getCurrentId();

    List<User> getUsersByRole(UserRole role);
}
