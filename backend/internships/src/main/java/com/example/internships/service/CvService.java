package com.example.internships.service;

import com.example.internships.dto.cv.*;
import com.example.internships.entity.*;
import com.example.internships.mapper.CvMapper;
import com.example.internships.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
@RequiredArgsConstructor
public class CvService {

    private final CvRepository cvRepository;
    private final StudentRepository studentRepository;
    private final EducationRepository educationRepository;
    private final ExperienceRepository experienceRepository;
    private final InterestRepository interestRepository;
    private final LanguageRepository languageRepository;
    private final SkillRepository skillRepository;
    private final CvMapper cvMapper;

    // CREATE
    @Transactional
    public CvResponseDTO createCv(Long userId, CreateCvRequest request) {
        Student student = getStudentByUserId(userId);

        Cv cv = cvMapper.toEntity(request);
        cv.setStudent(student);

        fillCvRelations(cv, student, request);

        cvRepository.save(cv);
        return cvMapper.toDto(cv);
    }

    // READ
    @Transactional
    public CvResponseDTO getCvByUserId(Long userId) {
        Cv cv = getCvEntityByUserId(userId);
        return cvMapper.toDto(cv);
    }

    // UPDATE
    @Transactional
    public CvResponseDTO updateCv(Long userId, CreateCvRequest request) {
        Cv cv = getCvEntityByUserId(userId);
        Student student = cv.getStudent();

        cv.setPhotoUrl(request.getPhotoUrl());
        cv.setSummary(request.getSummary());

        // reset relacija
        cv.getEducations().clear();
        cv.getExperiences().clear();
        cv.getSkills().clear();
        cv.getLanguages().clear();
        cv.getInterests().clear();

        fillCvRelations(cv, student, request);

        cvRepository.save(cv);
        return cvMapper.toDto(cv);
    }

    // DELETE
    @Transactional
    public void deleteCv(Long userId) {
        Cv cv = getCvEntityByUserId(userId);

        cv.getEducations().clear();
        cv.getExperiences().clear();
        cv.getSkills().clear();
        cv.getLanguages().clear();
        cv.getInterests().clear();

        cvRepository.delete(cv);
    }

    // PDF
    public byte[] generateCvPdf(Long userId) {
        Cv cv = getCvEntityByUserId(userId);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("CV for: " +
                    cv.getStudent().getFirstName() + " " +
                    cv.getStudent().getLastName()));

            document.add(new Paragraph("Email: " +
                    cv.getStudent().getUser().getEmail()));

            document.add(new Paragraph("Summary: " + cv.getSummary()));
            document.add(new Paragraph("\n"));

            document.add(new Paragraph("=== Education ==="));
            cv.getEducations().forEach(e ->
                    document.add(new Paragraph(
                            e.getDegree() + " in " + e.getFieldOfStudy() +
                                    " from " + e.getInstitution() +
                                    " (" + e.getStartYear() + " - " + e.getEndYear() + ")"
                    ))
            );

            document.add(new Paragraph("\n=== Experiences ==="));
            cv.getExperiences().forEach(exp ->
                    document.add(new Paragraph(
                            exp.getPosition() + " at " + exp.getCompanyName() +
                                    " (" + exp.getStartDate() + " - " + exp.getEndDate() + ")\n" +
                                    exp.getDescription()
                    ))
            );

            document.add(new Paragraph("\n=== Skills ==="));
            cv.getSkills().forEach(s ->
                    document.add(new Paragraph(
                            s.getSkillName() + " - " + s.getSkillLevel()
                    ))
            );

            document.add(new Paragraph("\n=== Languages ==="));
            cv.getLanguages().forEach(l ->
                    document.add(new Paragraph(
                            l.getLanguageName() + " - " + l.getLevel()
                    ))
            );

            document.add(new Paragraph("\n=== Interests ==="));
            cv.getInterests().forEach(i ->
                    document.add(new Paragraph(i.getInterestName()))
            );

            document.close();
            return baos.toByteArray();

        } catch (Exception ex) {
            throw new RuntimeException("Failed to generate PDF", ex);
        }
    }

    // HELPERS
    private Student getStudentByUserId(Long userId) {
        return studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    private Cv getCvEntityByUserId(Long userId) {
        Student student = getStudentByUserId(userId);

        return cvRepository.findFirstByStudentId(student.getId())
                .orElseThrow(() -> new RuntimeException("CV not found"));
    }

    private void fillCvRelations(Cv cv, Student student, CreateCvRequest request) {
        Long studentId = student.getId();

        cv.setEducations(handleEducations(request, student, studentId));
        cv.setExperiences(handleExperiences(request, student, studentId));
        cv.setSkills(handleSkills(request, student, studentId));
        cv.setLanguages(handleLanguages(request, student, studentId));
        cv.setInterests(handleInterests(request, student, studentId));
    }

    private Set<Education> handleEducations(CreateCvRequest request, Student student, Long studentId) {
        Set<Education> result = new HashSet<>();

        if (request.getEducationIds() != null) {
            List<Education> existing = educationRepository.findAllById(request.getEducationIds());
            existing.forEach(e -> {
                if (!e.getStudent().getId().equals(studentId)) {
                    throw new RuntimeException("Education ne pripada studentu");
                }
            });
            result.addAll(existing);
        }

        if (request.getNewEducations() != null) {
            for (CreateEducationRequest eReq : request.getNewEducations()) {
                Education e = new Education();
                e.setStudent(student);
                e.setInstitution(eReq.getInstitution());
                e.setDegree(eReq.getDegree());
                e.setFieldOfStudy(eReq.getFieldOfStudy());
                e.setStartYear(eReq.getStartYear());
                e.setEndYear(eReq.getEndYear());

                result.add(educationRepository.save(e));
            }
        }

        return result;
    }

    private Set<Experience> handleExperiences(CreateCvRequest request, Student student, Long studentId) {
        Set<Experience> result = new HashSet<>();

        if (request.getExperienceIds() != null) {
            List<Experience> existing = experienceRepository.findAllById(request.getExperienceIds());
            existing.forEach(e -> {
                if (!e.getStudent().getId().equals(studentId)) {
                    throw new RuntimeException("Experience ne pripada studentu");
                }
            });
            result.addAll(existing);
        }

        if (request.getNewExperiences() != null) {
            for (CreateExperienceRequest exReq : request.getNewExperiences()) {
                Experience e = new Experience();
                e.setStudent(student);
                e.setCompanyName(exReq.getCompanyName());
                e.setPosition(exReq.getPosition());
                e.setDescription(exReq.getDescription());
                e.setStartDate(exReq.getStartDate());
                e.setEndDate(exReq.getEndDate());

                result.add(experienceRepository.save(e));
            }
        }

        return result;
    }

    private Set<Skill> handleSkills(CreateCvRequest request, Student student, Long studentId) {
        Set<Skill> result = new HashSet<>();

        if (request.getSkillIds() != null) {
            List<Skill> existing = skillRepository.findAllById(request.getSkillIds());
            existing.forEach(s -> {
                if (!s.getStudent().getId().equals(studentId)) {
                    throw new RuntimeException("Skill ne pripada studentu");
                }
            });
            result.addAll(existing);
        }

        if (request.getNewSkills() != null) {
            for (CreateSkillRequest sReq : request.getNewSkills()) {
                Skill s = new Skill();
                s.setStudent(student);
                s.setSkillName(sReq.getSkillName());
                s.setSkillLevel(sReq.getSkillLevel());

                result.add(skillRepository.save(s));
            }
        }

        return result;
    }

    private Set<Language> handleLanguages(CreateCvRequest request, Student student, Long studentId) {
        Set<Language> result = new HashSet<>();

        if (request.getLanguageIds() != null) {
            List<Language> existing = languageRepository.findAllById(request.getLanguageIds());
            existing.forEach(l -> {
                if (!l.getStudent().getId().equals(studentId)) {
                    throw new RuntimeException("Language ne pripada studentu");
                }
            });
            result.addAll(existing);
        }

        if (request.getNewLanguages() != null) {
            for (CreateLanguageRequest lReq : request.getNewLanguages()) {
                Language l = new Language();
                l.setStudent(student);
                l.setLanguageName(lReq.getLanguageName());
                l.setLevel(lReq.getLevel());

                result.add(languageRepository.save(l));
            }
        }

        return result;
    }

    private Set<Interest> handleInterests(CreateCvRequest request, Student student, Long studentId) {
        Set<Interest> result = new HashSet<>();

        if (request.getInterestIds() != null) {
            List<Interest> existing = interestRepository.findAllById(request.getInterestIds());
            existing.forEach(i -> {
                if (!i.getStudent().getId().equals(studentId)) {
                    throw new RuntimeException("Interest ne pripada studentu");
                }
            });
            result.addAll(existing);
        }

        if (request.getNewInterests() != null) {
            for (CreateInterestRequest iReq : request.getNewInterests()) {
                Interest i = new Interest();
                i.setStudent(student);
                i.setInterestName(iReq.getInterestName());

                result.add(interestRepository.save(i));
            }
        }

        return result;
    }
}