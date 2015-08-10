package com.exadel.service.impl;

import com.exadel.model.entity.user.Authentification;
import com.exadel.model.entity.user.User;
import com.exadel.repository.AuthentificationRepository;
import com.exadel.repository.UserRepository;
import com.exadel.service.AuthentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthentificationServiceImpl implements AuthentificationService {

    @Autowired
    private AuthentificationRepository authentificationRepository;

    @Override
    public void addAuthentification(Authentification authentification) {
        authentificationRepository.saveAndFlush(authentification);
    }
}
