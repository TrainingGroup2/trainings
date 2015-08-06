package com.exadel.service.impl;

import com.exadel.model.entity.training.Reserve;
import com.exadel.repository.ReserveRepository;
import com.exadel.service.ReserveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReserveServiceImpl implements ReserveService {
    @Autowired
    private ReserveRepository reserveRepository;

    @Override
    public List<Reserve> getAllReservesByTrainingId(long trainingId) {
        return reserveRepository.findByTrainingId(trainingId);
    }

    @Override
    public void addReserve(Reserve reserve) {
        reserveRepository.save(reserve);
    }

    @Override
    public Reserve getReserveById(String id) {
        return null;
    }

    @Override
    public Reserve getReserveById(long id) {
        return null;
    }

    @Override
    public Reserve getNextReserveByTrainingId(long trainingId) {
        return reserveRepository.findFirstByTrainingId(trainingId);
    }

    @Override
    public void deleteReserve(long id) {
        reserveRepository.delete(id);
    }

    @Override
    public Reserve getReserveByTrainingIdAndUserId(long trainingId, long userId) {
        return reserveRepository.findByTrainingIdAndReservistId(trainingId, userId);
    }
}
