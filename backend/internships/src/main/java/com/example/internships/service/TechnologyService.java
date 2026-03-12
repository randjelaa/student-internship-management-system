package com.example.internships.service;

import com.example.internships.entity.Technology;
import com.example.internships.repository.TechnologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyService {

    private final TechnologyRepository technologyRepository;

    public List<Technology> getAllTechnologies() {
        return technologyRepository.findAll();
    }

    public Technology getTechnologyById(Long id) {
        return technologyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technology not found with id: " + id));
    }

    public Technology createTechnology(Technology technology) {
        return technologyRepository.save(technology);
    }

    public Technology updateTechnology(Long id, Technology updatedTechnology) {
        Technology existing = getTechnologyById(id);
        existing.setName(updatedTechnology.getName());

        return technologyRepository.save(existing);
    }

    public void deleteTechnology(Long id) {
        Technology technology = getTechnologyById(id);

        technologyRepository.delete(technology);
    }
}