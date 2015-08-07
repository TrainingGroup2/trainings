package com.exadel.service;

import com.exadel.model.entity.user.Absentee;

import java.util.List;

public interface AbsenteeService {
    List<Absentee> getAllAbsenteesByEntryId(long entryId);

    Absentee addAbsentee(Absentee absentee);

    void updateAbsentee(Absentee absentee);

    Absentee getAbsenteeById(String id);

    Absentee getAbsentee(long userId, long entryId);

    Absentee getAbsenteeById(long id);

    void deleteAbsentee(String id);
}
