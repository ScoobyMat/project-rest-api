package com.project.service;

import com.project.model.Zadanie;
import com.project.repository.ZadanieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ZadanieServiceImpl implements ZadanieService{
    private ZadanieRepository zadanieRepository;

    public ZadanieServiceImpl(ZadanieRepository zadanieRepository) {
        this.zadanieRepository = zadanieRepository;
    }

    @Override
    public Optional<Zadanie> getZadanie(Integer zadanieId) {
        return zadanieRepository.findById(zadanieId);
    }

    @Override
    public Zadanie createZadanie(Zadanie zadanie) {
        zadanieRepository.save(zadanie);
        return zadanie;
    }

    @Override
    public Zadanie updateZadanie(Zadanie zadanie, Integer zadanieId) {
        zadanie.setZadanieId(zadanieId);
        zadanieRepository.save(zadanie);
        return zadanie;
    }

    @Override
    public void deleteZadanie(Integer zadanieId) {
        zadanieRepository.deleteById(zadanieId);
    }

    @Override
    public Page<Zadanie> getZadania(Pageable pageable) {
        return zadanieRepository.findAll(pageable);
    }
}
