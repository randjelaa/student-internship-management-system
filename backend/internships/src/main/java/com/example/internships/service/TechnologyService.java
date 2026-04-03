package com.example.internships.service;

import com.example.internships.dto.technology.CreateTechnologyDTO;
import com.example.internships.dto.technology.TechnologyResponseDTO;
import com.example.internships.entity.Technology;
import com.example.internships.mapper.TechnologyMapper;
import com.example.internships.repository.TechnologyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyService {

    private final TechnologyRepository technologyRepository;
    private final TechnologyMapper technologyMapper;

    public List<Technology> getAllTechnologies() {
        return technologyRepository.findAll();
    }

    public Technology getTechnologyById(Long id) {
        return technologyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technology not found with id: " + id));
    }

    @Transactional
    public TechnologyResponseDTO createTechnology(CreateTechnologyDTO technology) {
        return technologyMapper.toDto(technologyRepository.save(technologyMapper.toEntity(technology)));
    }

    @Transactional
    public Technology updateTechnology(Long id, Technology updatedTechnology) {
        Technology existing = getTechnologyById(id);
        existing.setName(updatedTechnology.getName());

        return technologyRepository.save(existing);
    }

    @Transactional
    public void deleteTechnology(Long id) {
        Technology technology = getTechnologyById(id);
        technologyRepository.delete(technology);
    }
}