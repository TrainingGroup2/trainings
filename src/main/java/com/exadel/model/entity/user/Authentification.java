package com.exadel.model.entity.user;

import javax.persistence.*;

/**
 * Created by Виктория on 07.08.2015.
 */

@Entity
@Table(name = "authentification")
public class Authentification {

    @Id
    @Column(name ="user_id")
    private long id;

    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "login", nullable = false)
    private String login;

    public  Authentification() {

    }
    public Authentification(String password, long id, String login) {
        this.password = password;
        this.id = id;
        this.login = login;
    }

    public Authentification(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
