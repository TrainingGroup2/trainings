package com.exadel.model.entity;

import com.exadel.model.constants.TrainingStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trainings")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private ExternalTrainer trainer;

    @Column(name = "target_audience")
    private String targetAudience;

    private String language;

    @Column(name = "is_external")
    private boolean isExternal;

    private String description;
    private TrainingStatus status;

    @Column(name = "members_count_max")
    private int membersCountMax;

    @Column(name = "members_count")
    private int membersCount;

    @Transient
    private double rating;

    @ManyToMany
    @JoinTable(name = "training_users", joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> participants;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<TrainingFeedback> feedbacks;

    public void addFeedback(TrainingFeedback feedback) {
        feedback.setTraining(this);
        feedbacks.add(feedback);
    }

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<Entry> entries;

    public void addEntry(Entry entry) {
        entry.setTraining(this);
        entries.add(entry);
    }

    public Training() {}

    public void addParticipant(User user){
        this.participants.add(user);
    }

    @Override
    public String toString() {
        return "Training{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trainer=" + trainer +
                ", targetAudience='" + targetAudience + '\'' +
                ", language='" + language + '\'' +
                ", isExternal=" + isExternal +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", membersCountMax=" + membersCountMax +
                ", membersCount=" + membersCount +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExternalTrainer getTrainer() {
        return trainer;
    }

    public void setTrainer(ExternalTrainer trainer) {
        this.trainer = trainer;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public int getMembersCountMax() {
        return membersCountMax;
    }

    public void setMembersCountMax(int membersCountMax) {
        this.membersCountMax = membersCountMax;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public TrainingStatus getStatus() {
        return status;
    }

    public void setStatus(TrainingStatus status) {
        this.status = status;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }

    public List<TrainingFeedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<TrainingFeedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public boolean IsExternal() {
        return isExternal;
    }

    public void setIsExternal(boolean isExternal) {
        this.isExternal = isExternal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
