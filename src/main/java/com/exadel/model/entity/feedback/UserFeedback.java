package com.exadel.model.entity.feedback;

import com.exadel.dto.UserFeedbackDTO;
import com.exadel.model.entity.user.Employee;
import com.exadel.model.entity.user.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Table(name = "user_feedbacks")
public class UserFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, insertable = true)
    private Employee visitor;  //we can leave feedback only on employee

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private User trainer;   //who leaves feedback

    private String attendance;
    private String attitude;

    @Column(name = "communication_skills")
    private String communicationSkills;

    private boolean questions;

    @Column (name = "interested")
    private boolean interest;

    @Column(name = "focus_on_result")
    private boolean focusOnResult;

    private EnglishLevel level;
    @Min(1)
    @Max(4)
    private int grade;    //for English from 1 to 4

    @Column(name = "other_info")
    private String otherInfo;

    private Date date;

    public UserFeedback() {
    }

    public UserFeedback(UserFeedbackDTO feedbackDTO) {
        this.id = feedbackDTO.getId();
        this.attendance = feedbackDTO.getAttendance();
        this.attitude = feedbackDTO.getAttitude();
        this.communicationSkills = feedbackDTO.getCommunicationSkills();
        this.questions = feedbackDTO.isQuestions();
        this.interest = feedbackDTO.isInterest();
        this.focusOnResult = feedbackDTO.isFocusOnResult();
        this.level = feedbackDTO.getLevel();
        this.grade = feedbackDTO.getGrade();
        this.otherInfo = feedbackDTO.getOtherInfo();
        this.date = feedbackDTO.getDate();
    }

    public UserFeedback(long id) {
        this.id = id;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getAttitude() {
        return attitude;
    }

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    public String getCommunicationSkills() {
        return communicationSkills;
    }

    public void setCommunicationSkills(String communicationSkills) {
        this.communicationSkills = communicationSkills;
    }

    public boolean isQuestions() {
        return questions;
    }

    public void setQuestions(boolean questions) {
        this.questions = questions;
    }

    public boolean isInterest() {
        return interest;
    }

    public void setInterest(boolean interest) {
        this.interest = interest;
    }

    public boolean isFocusOnResult() {
        return focusOnResult;
    }

    public void setFocusOnResult(boolean focusOnResult) {
        this.focusOnResult = focusOnResult;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public User getTrainer() {
        return trainer;
    }

    public void setTrainer(User trainer) {
        this.trainer = trainer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Employee getVisitor() {
        return visitor;
    }

    public void setVisitor(Employee visitor) {
        this.visitor = visitor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public EnglishLevel getLevel() {
        return level;
    }

    public void setLevel(EnglishLevel level) {
        this.level = level;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

}
