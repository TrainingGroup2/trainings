package com.exadel.service.impl;

import com.exadel.model.entity.Rating;
import com.exadel.repository.RatingRepository;
import com.exadel.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void addRating(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public Collection<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Collection<Rating> getAllRatingsByTrainingId(long trainingId) {
        return ratingRepository.findByTrainingId(trainingId);
    }

    @Override
    public Optional<Rating> getRatingById(long id) {
        return Optional.ofNullable(ratingRepository.findOne(id));
    }
}
