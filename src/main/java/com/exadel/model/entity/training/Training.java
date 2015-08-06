package com.exadel.model.entity.training;

import com.exadel.dto.TrainingDTO;
import com.exadel.model.entity.feedback.TrainingFeedback;
import com.exadel.model.entity.user.ExternalTrainer;
import com.exadel.model.entity.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trainings")
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

    @Transient
    private double rating;

    @Column(name = "rating_sum")
    private int ratingSum;

    @Column(name = "valuer_count")
    private int valuerCount;

    private boolean repeated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "training_users", joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> participants;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<TrainingFeedback> feedbacks;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<Entry> entries;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL)
    private List<Reserve> reserves;

    public void addAttachment(Attachment attachment) {
        attachment.setTraining(this);
        attachments.add(attachment);
    }

    public Training() {
    }

    public Training(long id) {
        this.id = id;
    }

    public Training(TrainingDTO trainingDTO) {
        this.id = trainingDTO.getId();
        this.name = trainingDTO.getName();
        this.targetAudience = trainingDTO.getTargetAudience();
        this.language = trainingDTO.getLanguage();
        this.isExternal = trainingDTO.isExternal();
        this.description = trainingDTO.getDescription();
        this.status = trainingDTO.getStatus();
        this.membersCountMax = trainingDTO.getMembersCountMax();
        this.repeated = trainingDTO.isRepeated();

        this.trainer = new ExternalTrainer();
        this.trainer.setId(trainingDTO.getTrainer().getId());

        this.participants = new ArrayList<>();
        this.feedbacks = new ArrayList<>();
        this.entries = new ArrayList<>();
        this.reserves = new ArrayList<>();
    }

    public void updateTraining(TrainingDTO trainingDTO) {
        this.name = trainingDTO.getName();
        this.targetAudience = trainingDTO.getTargetAudience();
        this.language = trainingDTO.getLanguage();
        this.isExternal = trainingDTO.isExternal();
        this.description = trainingDTO.getDescription();
        this.status = trainingDTO.getStatus();
        this.membersCountMax = trainingDTO.getMembersCountMax();
    }

    public void addFeedback(TrainingFeedback feedback) {
        feedback.setTraining(this);
        feedbacks.add(feedback);
    }

    public void addEntry(Entry entry) {
        entry.setTraining(this);
        entries.add(entry);
    }

    public void addParticipant(User user) {
        this.participants.add(user);
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

    public boolean isExternal() {
        return isExternal;
    }

    public int getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(int ratingSum) {
        this.ratingSum = ratingSum;
    }

    public int getValuerCount() {
        return valuerCount;
    }

    public void setValuerCount(int valuerCount) {
        this.valuerCount = valuerCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public List<Reserve> getReserves() {
        return reserves;
    }

    public void setReserves(List<Reserve> reserves) {
        this.reserves = reserves;
    }
}
