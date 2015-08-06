package com.exadel.service;

import com.exadel.model.entity.training.Reserve;

import java.util.List;

public interface ReserveService {

    List<Reserve> getAllReservesByTrainingId(long trainingId);

    void addReserve(Reserve reserve);

    Reserve getReserveById(String id);

    Reserve getReserveById(long id);

    Reserve getNextReserveByTrainingId(long id);

    void deleteReserve(long id);

    Reserve getReserveByTrainingIdAndUserId(long trainingId, long userId);
}
