package com.example.internships.service;

import com.example.internships.entity.CvSkill;
import com.example.internships.repository.CvSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CvSkillService {

    private final CvSkillRepository cvSkillRepository;

    public List<CvSkill> getAllCvSkills() {
        return cvSkillRepository.findAll();
    }

    public CvSkill getCvSkillById(Long id) {
        return cvSkillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CvSkill not found with id: " + id));
    }

    public CvSkill createCvSkill(CvSkill cvSkill) {
        return cvSkillRepository.save(cvSkill);
    }

    public CvSkill updateCvSkill(Long id, CvSkill updatedCvSkill) {
        CvSkill existing = getCvSkillById(id);

        existing.setCv(updatedCvSkill.getCv());
        existing.setSkillName(updatedCvSkill.getSkillName());
        existing.setSkillLevel(updatedCvSkill.getSkillLevel());

        return cvSkillRepository.save(existing);
    }

    public void deleteCvSkill(Long id) {
        CvSkill cvSkill = getCvSkillById(id);

        cvSkillRepository.delete(cvSkill);
    }
}