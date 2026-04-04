package com.example.internships.service;

import com.example.internships.dto.application.ApplicationResponseDTO;
import com.example.internships.dto.enums.ApplicationStatus;
import com.example.internships.dto.internship.InternshipApplicationsDTO;
import com.example.internships.dto.specific.CompanyApplicationViewDTO;
import com.example.internships.dto.specific.InternshipApplicationsGroupDTO;
import com.example.internships.entity.Application;
import com.example.internships.entity.Company;
import com.example.internships.entity.Internship;
import com.example.internships.entity.Student;
import com.example.internships.mapper.ApplicationMapper;
import com.example.internships.mapper.InternshipMapper;
import com.example.internships.repository.ApplicationRepository;
import com.example.internships.repository.CompanyRepository;
import com.example.internships.repository.InternshipRepository;
import com.example.internships.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;
    private final CompanyRepository companyRepository;
    private final ApplicationMapper applicationMapper;
    private final InternshipMapper internshipMapper;

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    @Transactional
    public Application createApplication(Long userId, Long internshipId) {
        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        Application application = new Application();
        application.setStudent(student);
        application.setInternship(internship);
        application.setStatus(ApplicationStatus.PENDING);

        return applicationRepository.save(application);
    }

    @Transactional
    public Application acceptApplication(Long id) {
        Application application = getApplicationById(id);
        application.setStatus(ApplicationStatus.ACCEPTED);
        return applicationRepository.save(application);
    }

    @Transactional
    public Application rejectApplication(Long id) {
        Application application = getApplicationById(id);
        application.setStatus(ApplicationStatus.REJECTED);
        return applicationRepository.save(application);
    }

    @Transactional
    public void deleteApplication(Long id) {
        Application application = getApplicationById(id);
        applicationRepository.delete(application);
    }

    public List<Application> getApplicationsByUserId(Long userId) {
        Long studentId = studentRepository.findByUserId(userId).orElseThrow().getId();
        return applicationRepository.findByStudentId(studentId);
    }

    public List<InternshipApplicationsDTO> getByCompany(Long userId) {
        Long companyId = companyRepository.findByUserId(userId).orElseThrow().getId();

        List<Application> applications = applicationRepository.findByCompanyId(companyId);

        Map<Long, List<Application>> grouped = applications
                .stream()
                .collect(Collectors.groupingBy(a -> a.getInternship().getId()));

        List<InternshipApplicationsDTO> result = new ArrayList<>();

        for (Map.Entry<Long, List<Application>> entry : grouped.entrySet()) {
            List<Application> apps = entry.getValue();
            Internship internship = apps.get(0).getInternship();

            InternshipApplicationsDTO dto = new InternshipApplicationsDTO();
            dto.setInternship(internshipMapper.toResponse(internship));

            List<ApplicationResponseDTO> appDtos = apps.stream()
                    .map(applicationMapper::toResponse)
                    .toList();

            dto.setApplications(appDtos);
            result.add(dto);
        }

        return result;
    }

    public List<InternshipApplicationsGroupDTO> getGroupedApplicationsForCompany(Long userId) {
        Long companyId = companyRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Company not found"))
                .getId();

        List<Application> allApps = applicationRepository.findByInternship_Company_Id(companyId);

        Map<Internship, List<Application>> groupedByInternship = allApps.stream()
                .collect(Collectors.groupingBy(Application::getInternship));

        return groupedByInternship.entrySet().stream()
                .map(entry -> {
                    InternshipApplicationsGroupDTO group = new InternshipApplicationsGroupDTO();
                    group.setInternshipId(entry.getKey().getId());
                    group.setInternshipTitle(entry.getKey().getTitle());

                    List<CompanyApplicationViewDTO> appDtos = entry.getValue().stream()
                            .map(applicationMapper::toViewDto)
                            .collect(Collectors.toList());

                    group.setApplications(appDtos);
                    return group;
                })
                .collect(Collectors.toList());
    }
}