package com.exadel.service.impl;

import com.exadel.model.entity.Entry;
import com.exadel.repository.EntryRepository;
import com.exadel.service.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class EntryServiceImpl implements EntryService{
    private final EntryRepository entryRepository;

    @Autowired
    public EntryServiceImpl(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    public Optional<Entry> getEntryById(long id) {
        return Optional.ofNullable(entryRepository.findOne(id));
    }

    @Override
    public Collection<Entry> getAllEntriesByTrainingId(long trainingId) {
        return entryRepository.findByTrainingId(trainingId);
    }

    @Override
    public Collection<Entry> getAllEntries() {
        return entryRepository.findAll();
    }

    @Override
    public Optional<Entry> addEntry(Entry entry) {
        return Optional.ofNullable(entryRepository.save(entry));
    }
}
