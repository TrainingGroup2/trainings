package com.exadel.repository;

import com.exadel.model.entity.training.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    List<Reserve> findByTrainingId(long trainingId);

    Reserve findFirstByTrainingId(long trainingId);

    Reserve findByTrainingIdAndReservistId(long trainingId, long userId);
}
