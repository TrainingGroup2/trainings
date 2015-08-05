package com.exadel.service.impl;

import com.exadel.model.entity.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Service
@PropertySource("classpath:application.properties")
public class SmtpMailSender {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private static JavaMailSenderImpl mailSender;

    static{
        mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        mailSender.setJavaMailProperties(prop);
    }

    SmtpMailSender(){}

    public void send(String to, String subject, String body){
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void send(Collection<String> receivers, String subject, String body){
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(receivers.toArray(new String[receivers.size()]));
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendToUsers(List<User> users, String subject, String body){
        for (User user: users){
            this.send(user.getEmail(), subject, "Hi, " + user.getName() + "!\n" + body);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
