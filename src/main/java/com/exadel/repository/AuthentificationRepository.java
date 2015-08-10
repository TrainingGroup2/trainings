package com.exadel.repository;

import com.exadel.model.entity.user.Authentification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthentificationRepository extends JpaRepository<Authentification, Long> {
}
