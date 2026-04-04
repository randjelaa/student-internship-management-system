package com.example.internships.service;

import com.example.internships.dto.cv.*;
import com.example.internships.entity.*;
import com.example.internships.mapper.CvMapper;
import com.example.internships.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import com.itextpdf.layout.element.Image;
import com.itextpdf.io.image.ImageDataFactory;

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

    @Transactional
    public CvResponseDTO createCv(Long userId, CreateCvRequest request) {
        Student student = getStudentByUserId(userId);

        Cv cv = cvMapper.toEntity(request);
        cv.setStudent(student);

        fillCvRelations(cv, student, request);

        cvRepository.save(cv);
        return cvMapper.toDto(cv);
    }

    public CvResponseDTO getCvByUserId(Long userId) {
        Cv cv = getCvEntityByUserId(userId);
        return cvMapper.toDto(cv);
    }

    public CvResponseDTO getCvByStudentId(Long studentId) {
        Long userId = studentRepository.findById(studentId).orElseThrow().getUser().getId();
        return getCvByUserId(userId);
    }

    @Transactional
    public CvResponseDTO updateCv(Long userId, CreateCvRequest request) {
        Cv cv = getCvEntityByUserId(userId);
        Student student = cv.getStudent();

        cv.setPhotoUrl(request.getPhotoUrl());
        cv.setSummary(request.getSummary());

        cv.getEducations().clear();
        cv.getExperiences().clear();
        cv.getSkills().clear();
        cv.getLanguages().clear();
        cv.getInterests().clear();

        fillCvRelations(cv, student, request);

        cvRepository.save(cv);
        return cvMapper.toDto(cv);
    }

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

    public byte[] generateCvPdfByUserId(Long userId) {
        Cv cv = getCvEntityByUserId(userId);
        return generateCvPdfByStudentId(cv.getStudent().getId());
    }

    public byte[] generateCvPdfByStudentId(Long studentId) {
        Cv cv = cvRepository.findFirstByStudentId(studentId).orElseThrow();

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            if (cv.getPhotoUrl() != null && !cv.getPhotoUrl().isEmpty()) {
                try {
                    String filename = null;

                    if (cv.getPhotoUrl() != null && cv.getPhotoUrl().contains("/")) {
                        filename = cv.getPhotoUrl().substring(cv.getPhotoUrl().lastIndexOf("/") + 1);
                    }

                    if (filename != null) {
                        try {
                            byte[] imageBytes = Files.readAllBytes(Paths.get("uploads/" + filename));
                            Image img = new Image(ImageDataFactory.create(imageBytes));
                            img.setWidth(100);
                            img.setHeight(100);
                            document.add(img);
                        } catch (Exception e) {
                            System.out.println("Image load failed: " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Image load failed: " + e.getMessage());
                }
            }

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

        if (request.getEducations() != null) {
            for (EducationDTO dto : request.getEducations()) {
                Education edu;

                if (dto.getId() != null) {
                    edu = educationRepository.findById(dto.getId())
                            .orElseThrow(() -> new RuntimeException("Education not found: " + dto.getId()));

                    if (!edu.getStudent().getId().equals(studentId)) {
                        throw new RuntimeException("Niste vlasnik ovog zapisa");
                    }
                } else {
                    edu = new Education();
                    edu.setStudent(student);
                }

                edu.setInstitution(dto.getInstitution());
                edu.setDegree(dto.getDegree());
                edu.setFieldOfStudy(dto.getFieldOfStudy());
                edu.setStartYear(dto.getStartYear());
                edu.setEndYear(dto.getEndYear());

                result.add(educationRepository.save(edu));
            }
        }

        return result;
    }

    private Set<Experience> handleExperiences(CreateCvRequest request, Student student, Long studentId) {
        Set<Experience> result = new HashSet<>();

        if (request.getExperiences() != null) {
            for (ExperienceDTO dto : request.getExperiences()) {
                Experience experience;

                if (dto.getId() != null) {
                    experience = experienceRepository.findById(dto.getId())
                            .orElseThrow(() -> new RuntimeException("Experience not found: " + dto.getId()));

                    if (!experience.getStudent().getId().equals(studentId)) {
                        throw new RuntimeException("Niste vlasnik ovog zapisa");
                    }
                } else {
                    experience = new Experience();
                    experience.setStudent(student);
                }

                experience.setCompanyName(dto.getCompanyName());
                experience.setPosition(dto.getPosition());
                experience.setDescription(dto.getDescription());
                experience.setStartDate(dto.getStartDate());
                experience.setEndDate(dto.getEndDate());

                result.add(experienceRepository.save(experience));
            }
        }
        return result;
    }

    private Set<Skill> handleSkills(CreateCvRequest request, Student student, Long studentId) {
        Set<Skill> result = new HashSet<>();

        if (request.getSkills() != null) {
            for (SkillDTO dto : request.getSkills()) {
                Skill skill;

                if (dto.getId() != null) {
                    skill = skillRepository.findById(dto.getId())
                            .orElseThrow(() -> new RuntimeException("Skill not found: " + dto.getId()));

                    if (!skill.getStudent().getId().equals(studentId)) {
                        throw new RuntimeException("Niste vlasnik ovog zapisa");
                    }
                } else {
                    skill = new Skill();
                    skill.setStudent(student);
                }

                skill.setSkillName(dto.getSkillName());
                skill.setSkillLevel(dto.getSkillLevel());

                result.add(skillRepository.save(skill));
            }
        }

        return result;
    }

    private Set<Language> handleLanguages(CreateCvRequest request, Student student, Long studentId) {
        Set<Language> result = new HashSet<>();

        if (request.getLanguages() != null) {
            for (LanguageDTO dto : request.getLanguages()) {
                Language lang;

                if (dto.getId() != null) {
                    lang = languageRepository.findById(dto.getId())
                            .orElseThrow(() -> new RuntimeException("Language not found: " + dto.getId()));

                    if (!lang.getStudent().getId().equals(studentId)) {
                        throw new RuntimeException("Niste vlasnik ovog zapisa");
                    }
                } else {
                    lang = new Language();
                    lang.setStudent(student);
                }

                lang.setLanguageName(dto.getLanguageName());
                lang.setLevel(dto.getLevel());

                result.add(languageRepository.save(lang));
            }
        }

        return result;
    }

    private Set<Interest> handleInterests(CreateCvRequest request, Student student, Long studentId) {
        Set<Interest> result = new HashSet<>();

        if (request.getInterests() != null) {
            for (InterestDTO dto : request.getInterests()) {
                Interest interest;

                if (dto.getId() != null) {
                    interest = interestRepository.findById(dto.getId())
                            .orElseThrow(() -> new RuntimeException("Interest not found: " + dto.getId()));

                    if (!interest.getStudent().getId().equals(studentId)) {
                        throw new RuntimeException("Niste vlasnik ovog zapisa");
                    }
                } else {
                    interest = new Interest();
                    interest.setStudent(student);
                }

                interest.setInterestName(dto.getInterestName());

                result.add(interestRepository.save(interest));
            }
        }

        return result;
    }
}