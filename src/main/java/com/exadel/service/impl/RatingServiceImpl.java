package com.exadel.service.impl;

import com.exadel.model.entity.training.Rating;
import com.exadel.repository.RatingRepository;
import com.exadel.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public boolean addRating(Rating rating) {
        //if (ratingRepository.) todo: check unique combination.
        ratingRepository.saveAndFlush(rating);
        return true;
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
