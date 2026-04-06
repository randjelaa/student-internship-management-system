package com.example.internships.service;

import com.example.internships.dto.enums.ApplicationStatus;
import com.example.internships.dto.grade.*;
import com.example.internships.dto.specific.InternshipGradingGroupDTO;
import com.example.internships.dto.specific.StudentGradingDetailDTO;
import com.example.internships.dto.worklog.WorkLogResponseDTO;
import com.example.internships.entity.Application;
import com.example.internships.entity.Grade;
import com.example.internships.entity.Student;
import com.example.internships.entity.Internship;
import com.example.internships.mapper.GradeMapper;
import com.example.internships.mapper.InternshipMapper;
import com.example.internships.mapper.StudentMapper;
import com.example.internships.mapper.WorkLogMapper;
import com.example.internships.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final InternshipRepository internshipRepository;
    private final WorkLogRepository workLogRepository;
    private final CompanyRepository companyRepository;
    private final ApplicationRepository applicationRepository;
    private final GradeMapper gradeMapper;
    private final WorkLogMapper workLogMapper;
    private final StudentMapper studentMapper;
    private final InternshipMapper internshipMapper;

    @Transactional
    public GradeResponseDTO createGrade(CreateGradeRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Internship internship = internshipRepository.findById(request.getInternshipId())
                .orElseThrow(() -> new RuntimeException("Internship not found"));

        Grade grade = gradeMapper.toEntity(request);
        grade.setStudent(student);
        grade.setInternship(internship);

        Grade saved = gradeRepository.save(grade);
        return gradeMapper.toResponse(saved);
    }

    public List<GradeResponseDTO> getGradesByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return gradeRepository.findByStudent(student)
                .stream()
                .map(gradeMapper::toResponse)
                .toList();
    }

    public List<GradeDetails> getAllGradeDetails() {
        List<Grade> grades = gradeRepository.findAll();

        return grades.stream().map(grade -> {
            List<WorkLogResponseDTO> workLogs = workLogRepository.findByStudentIdAndInternshipId(grade.getStudent().getId(), grade.getInternship().getId())
                    .stream()
                    .map(workLogMapper::toResponse)
                    .toList();

            return new GradeDetails(
                    grade.getId(),
                    studentMapper.toSummary(grade.getStudent()),
                    internshipMapper.toSummary(grade.getInternship()),
                    grade.getCompanyComment(),
                    grade.getFacultyGrade(),
                    workLogs
            );

        }).toList();
    }

    public GradeResponseDTO updateGrade(Long id, CreateGradeRequest request) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        grade.setFacultyGrade(request.getFacultyGrade());
        gradeRepository.save(grade);

        return gradeMapper.toResponse(grade);
    }

    public List<InternshipGradingGroupDTO> getGradingDashboard(Long userId) {
        Long companyId = companyRepository.findByUserId(userId).orElseThrow().getId();

        // 1. Dohvatamo sve prihvaćene aplikacije za tu kompaniju
        List<Application> acceptedApps = applicationRepository
                .findByInternship_Company_IdAndStatus(companyId, ApplicationStatus.ACCEPTED);

        // 2. Grupišemo po Internship-u
        Map<Internship, List<Application>> grouped = acceptedApps.stream()
                .collect(Collectors.groupingBy(Application::getInternship));

        return grouped.entrySet().stream().map(entry -> {
            Internship i = entry.getKey();

            InternshipGradingGroupDTO groupDto = new InternshipGradingGroupDTO();
            groupDto.setInternshipId(i.getId());
            groupDto.setInternshipTitle(i.getTitle());

            // 3. Za svaku aplikaciju (studenta) unutar te prakse izvlačimo detalje
            List<StudentGradingDetailDTO> studentDetails = entry.getValue().stream().map(app -> {
                Student s = app.getStudent();
                StudentGradingDetailDTO sDto = new StudentGradingDetailDTO();
                sDto.setStudentId(s.getId());
                sDto.setStudentFullName(s.getFirstName() + " " + s.getLastName());

                // Izvlačimo worklogove (ovdje možeš dodati custom metodu u repo)
                sDto.setWorkLogs(workLogRepository.findByStudentIdAndInternshipId(s.getId(), i.getId())
                        .stream().map(workLogMapper::toResponse).collect(Collectors.toList()));

                // Provjeravamo da li već postoji ocjena/komentar
                gradeRepository.findByStudentIdAndInternshipId(s.getId(), i.getId())
                        .ifPresent(grade -> {
                            sDto.setExistingComment(grade.getCompanyComment());
                            sDto.setGraded(true);
                        });

                return sDto;
            }).collect(Collectors.toList());

            groupDto.setStudents(studentDetails);
            return groupDto;
        }).collect(Collectors.toList());
    }
}