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

import com.example.internships.entity.Cv;
import com.example.internships.repository.CvRepository;
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

    @Transactional
    public CvResponseDTO createCv(Long studentId, CreateCvRequest request) {

        Student student = studentRepository.findByUserId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found for user id " + studentId));

        Cv cv = cvMapper.toEntity(request);
        cv.setStudent(student);

        // =========================
        // EDUCATIONS
        // =========================

        Set<Education> educations = new HashSet<>();

        // postojeći
        if (request.getEducationIds() != null) {
            List<Education> existing = educationRepository.findAllById(request.getEducationIds());

            // 🔥 sigurnosna provjera
            existing.forEach(e -> {
                if (!e.getStudent().getId().equals(studentId)) {
                    throw new RuntimeException("Education ne pripada studentu");
                }
            });

            educations.addAll(existing);
        }

        // novi
        if (request.getNewEducations() != null) {
            for (CreateEducationRequest eReq : request.getNewEducations()) {
                Education e = new Education();
                e.setStudent(student);
                e.setInstitution(eReq.getInstitution());
                e.setDegree(eReq.getDegree());
                e.setFieldOfStudy(eReq.getFieldOfStudy());
                e.setStartYear(eReq.getStartYear());
                e.setEndYear(eReq.getEndYear());

                educations.add(educationRepository.save(e));
            }
        }

        cv.setEducations(educations);

        // =========================
        // EXPERIENCES
        // =========================
        Set<Experience> experiences = new HashSet<>();
        if (request.getExperienceIds() != null) {
            List<Experience> existing = experienceRepository.findAllById(request.getExperienceIds());
            existing.forEach(exp -> {
                if (!exp.getStudent().getId().equals(studentId)) throw new RuntimeException("Experience ne pripada studentu");
            });
            experiences.addAll(existing);
        }
        if (request.getNewExperiences() != null) {
            for (CreateExperienceRequest exReq : request.getNewExperiences()) {
                Experience exp = new Experience();
                exp.setStudent(student);
                exp.setCompanyName(exReq.getCompanyName());
                exp.setPosition(exReq.getPosition());
                exp.setDescription(exReq.getDescription());
                exp.setStartDate(exReq.getStartDate());
                exp.setEndDate(exReq.getEndDate());
                experiences.add(experienceRepository.save(exp));
            }
        }
        cv.setExperiences(experiences);

        // =========================
        // SKILLS
        // =========================
        Set<Skill> skills = new HashSet<>();
        if (request.getSkillIds() != null) {
            List<Skill> existing = skillRepository.findAllById(request.getSkillIds());
            existing.forEach(s -> {
                if (!s.getStudent().getId().equals(studentId)) throw new RuntimeException("Skill ne pripada studentu");
            });
            skills.addAll(existing);
        }
        if (request.getNewSkills() != null) {
            for (CreateSkillRequest sReq : request.getNewSkills()) {
                Skill s = new Skill();
                s.setStudent(student);
                s.setSkillName(sReq.getSkillName());
                s.setSkillLevel(sReq.getSkillLevel());
                skills.add(skillRepository.save(s));
            }
        }
        cv.setSkills(skills);

        // =========================
        // LANGUAGES
        // =========================
        Set<Language> languages = new HashSet<>();
        if (request.getLanguageIds() != null) {
            List<Language> existing = languageRepository.findAllById(request.getLanguageIds());
            existing.forEach(l -> {
                if (!l.getStudent().getId().equals(studentId)) throw new RuntimeException("Language ne pripada studentu");
            });
            languages.addAll(existing);
        }
        if (request.getNewLanguages() != null) {
            for (CreateLanguageRequest lReq : request.getNewLanguages()) {
                Language l = new Language();
                l.setStudent(student);
                l.setLanguageName(lReq.getLanguageName());
                l.setLevel(lReq.getLevel());
                languages.add(languageRepository.save(l));
            }
        }
        cv.setLanguages(languages);

        // =========================
        // INTERESTS
        // =========================
        Set<Interest> interests = new HashSet<>();
        if (request.getInterestIds() != null) {
            List<Interest> existing = interestRepository.findAllById(request.getInterestIds());
            existing.forEach(i -> {
                if (!i.getStudent().getId().equals(studentId)) throw new RuntimeException("Interest ne pripada studentu");
            });
            interests.addAll(existing);
        }
        if (request.getNewInterests() != null) {
            for (CreateInterestRequest iReq : request.getNewInterests()) {
                Interest i = new Interest();
                i.setStudent(student);
                i.setInterestName(iReq.getInterestName());
                interests.add(interestRepository.save(i));
            }
        }
        cv.setInterests(interests);

        cvRepository.save(cv);

        return cvMapper.toDto(cv);
    }

    @Transactional
    public CvResponseDTO getCvByStudentId(Long studentId) {
        Cv cv = cvRepository.findFirstByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("CV not found"));

        return cvMapper.toDto(cv);
    }

    private void fillCvRelations(Cv cv, Student student, CreateCvRequest request) {

        Long studentId = student.getId();

        // EDUCATION
        Set<Education> educations = new HashSet<>();

        if (request.getEducationIds() != null) {
            List<Education> existing = educationRepository.findAllById(request.getEducationIds());
            existing.forEach(e -> {
                if (!e.getStudent().getId().equals(studentId)) {
                    throw new RuntimeException("Education ne pripada studentu");
                }
            });
            educations.addAll(existing);
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
                educations.add(educationRepository.save(e));
            }
        }

        cv.setEducations(educations);

        // =========================
        // EXPERIENCE
        // =========================
        Set<Experience> experiences = new HashSet<>();
        if (request.getExperienceIds() != null) {
            List<Experience> existing = experienceRepository.findAllById(request.getExperienceIds());
            existing.forEach(exp -> {
                if (!exp.getStudent().getId().equals(studentId)) {
                    throw new RuntimeException("Experience ne pripada studentu");
                }
            });
            experiences.addAll(existing);
        }
        if (request.getNewExperiences() != null) {
            for (CreateExperienceRequest exReq : request.getNewExperiences()) {
                Experience exp = new Experience();
                exp.setStudent(student);
                exp.setCompanyName(exReq.getCompanyName());
                exp.setPosition(exReq.getPosition());
                exp.setDescription(exReq.getDescription());
                exp.setStartDate(exReq.getStartDate());
                exp.setEndDate(exReq.getEndDate());
                experiences.add(experienceRepository.save(exp));
            }
        }
        cv.setExperiences(experiences);

        // =========================
        // SKILLS
        // =========================
        Set<Skill> skills = new HashSet<>();
        if (request.getSkillIds() != null) {
            List<Skill> existing = skillRepository.findAllById(request.getSkillIds());
            existing.forEach(s -> {
                if (!s.getStudent().getId().equals(studentId)) {
                    throw new RuntimeException("Skill ne pripada studentu");
                }
            });
            skills.addAll(existing);
        }
        if (request.getNewSkills() != null) {
            for (CreateSkillRequest sReq : request.getNewSkills()) {
                Skill s = new Skill();
                s.setStudent(student);
                s.setSkillName(sReq.getSkillName());
                s.setSkillLevel(sReq.getSkillLevel());
                skills.add(skillRepository.save(s));
            }
        }
        cv.setSkills(skills);

        // =========================
        // LANGUAGES
        // =========================
        Set<Language> languages = new HashSet<>();
        if (request.getLanguageIds() != null) {
            List<Language> existing = languageRepository.findAllById(request.getLanguageIds());
            existing.forEach(l -> {
                if (!l.getStudent().getId().equals(studentId)) {
                    throw new RuntimeException("Language ne pripada studentu");
                }
            });
            languages.addAll(existing);
        }
        if (request.getNewLanguages() != null) {
            for (CreateLanguageRequest lReq : request.getNewLanguages()) {
                Language l = new Language();
                l.setStudent(student);
                l.setLanguageName(lReq.getLanguageName());
                l.setLevel(lReq.getLevel());
                languages.add(languageRepository.save(l));
            }
        }
        cv.setLanguages(languages);

        // =========================
        // INTERESTS
        // =========================
        Set<Interest> interests = new HashSet<>();
        if (request.getInterestIds() != null) {
            List<Interest> existing = interestRepository.findAllById(request.getInterestIds());
            existing.forEach(i -> {
                if (!i.getStudent().getId().equals(studentId)) {
                    throw new RuntimeException("Interest ne pripada studentu");
                }
            });
            interests.addAll(existing);
        }
        if (request.getNewInterests() != null) {
            for (CreateInterestRequest iReq : request.getNewInterests()) {
                Interest i = new Interest();
                i.setStudent(student);
                i.setInterestName(iReq.getInterestName());
                interests.add(interestRepository.save(i));
            }
        }
        cv.setInterests(interests);
    }

    @Transactional
    public CvResponseDTO updateCv(Long studentId, CreateCvRequest request) {

        Cv cv = cvRepository.findFirstByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("CV not found"));

        Student student = cv.getStudent();

        // basic fields
        cv.setPhotoUrl(request.getPhotoUrl());
        cv.setSummary(request.getSummary());

        // 🔥 reset relacija
        cv.getEducations().clear();
        cv.getExperiences().clear();
        cv.getSkills().clear();
        cv.getLanguages().clear();
        cv.getInterests().clear();

        // ponovo popuni
        fillCvRelations(cv, student, request);

        cvRepository.save(cv);

        return cvMapper.toDto(cv);
    }

    @Transactional
    public void deleteCv(Long studentId) {

        Cv cv = cvRepository.findFirstByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("CV not found"));

        cv.getEducations().clear();
        cv.getExperiences().clear();
        cv.getSkills().clear();
        cv.getLanguages().clear();
        cv.getInterests().clear();

        cvRepository.delete(cv);
    }

    public byte[] generateCvPdf(Long studentId) {

        Cv cv = cvRepository.findFirstByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("CV not found"));

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // =========================
            // Dodaj osnovne informacije
            // =========================
            document.add(new Paragraph("CV for: " + cv.getStudent().getFirstName() + " " + cv.getStudent().getLastName()));
            document.add(new Paragraph("Email: " + cv.getStudent().getUser().getEmail()));
            document.add(new Paragraph("Summary: " + cv.getSummary()));
            document.add(new Paragraph("\n"));

            // =========================
            // Education
            // =========================
            document.add(new Paragraph("=== Education ==="));
            cv.getEducations().forEach(e -> document.add(new Paragraph(e.getDegree() + " in " + e.getFieldOfStudy() +
                    " from " + e.getInstitution() +
                    " (" + e.getStartYear() + " - " + e.getEndYear() + ")")));

            // =========================
            // Experiences
            // =========================
            document.add(new Paragraph("\n=== Experiences ==="));
            cv.getExperiences().forEach(exp -> document.add(new Paragraph(exp.getPosition() + " at " + exp.getCompanyName() +
                    " (" + exp.getStartDate() + " - " + exp.getEndDate() + ")\n" +
                    exp.getDescription())));

            // =========================
            // Skills
            // =========================
            document.add(new Paragraph("\n=== Skills ==="));
            cv.getSkills().forEach(s -> document.add(new Paragraph(s.getSkillName() + " - " + s.getSkillLevel())));

            // =========================
            // Languages
            // =========================
            document.add(new Paragraph("\n=== Languages ==="));
            cv.getLanguages().forEach(l -> document.add(new Paragraph(l.getLanguageName() + " - " + l.getLevel())));

            // =========================
            // Interests
            // =========================
            document.add(new Paragraph("\n=== Interests ==="));
            cv.getInterests().forEach(i -> document.add(new Paragraph(i.getInterestName())));

            document.close();
            return baos.toByteArray();

        } catch (Exception ex) {
            throw new RuntimeException("Failed to generate PDF", ex);
        }
    }
}
