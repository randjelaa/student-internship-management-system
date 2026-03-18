USE internship_system;

--------------------------------------------------
-- USERS
--------------------------------------------------
INSERT INTO users (email, password, role, active) VALUES
('student1@example.com', 'password1', 'ROLE_STUDENT', TRUE),
('student2@example.com', 'password2', 'ROLE_STUDENT', TRUE),
('faculty1@example.com', 'password3', 'ROLE_FACULTY', TRUE),
('company1@example.com', 'password4', 'ROLE_COMPANY', TRUE),
('company2@example.com', 'password5', 'ROLE_COMPANY', TRUE);

--------------------------------------------------
-- STUDENTS
--------------------------------------------------
INSERT INTO students (user_id, first_name, last_name, index_number, faculty, year_of_study)
VALUES
(1, 'Ana', 'Markovic', '2020/001', 'Computer Science', 3),
(2, 'Ivan', 'Petrovic', '2021/002', 'Information Systems', 2);

--------------------------------------------------
-- COMPANIES
--------------------------------------------------
INSERT INTO companies (user_id, name, description, website, active)
VALUES
(4, 'Tech Solutions', 'IT consultancy', 'https://techsolutions.com', TRUE),
(5, 'Innovatech', 'Software development', 'https://innovatech.com', TRUE);

--------------------------------------------------
-- CV
--------------------------------------------------
INSERT INTO cv (student_id, photo_url, summary)
VALUES
(1, 'https://example.com/photo1.jpg', 'Backend oriented CV'),
(2, 'https://example.com/photo2.jpg', 'Data oriented CV');

--------------------------------------------------
-- EDUCATION
--------------------------------------------------
INSERT INTO education (student_id, institution, degree, field_of_study, start_year, end_year)
VALUES
(1, 'University of Sarajevo', 'BSc', 'Computer Science', 2020, 2023),
(2, 'University of Tuzla', 'BSc', 'Information Systems', 2021, 2024);

--------------------------------------------------
-- EXPERIENCE
--------------------------------------------------
INSERT INTO experience (student_id, company_name, position, description, start_date, end_date)
VALUES
(1, 'Intern IT Solutions', 'Junior Developer', 'Worked on backend', '2022-06-01', '2022-09-01'),
(2, 'Data Labs', 'Data Intern', 'Worked on analytics', '2023-06-01', '2023-08-01');

--------------------------------------------------
-- SKILLS
--------------------------------------------------
INSERT INTO skills (student_id, skill_name, skill_level)
VALUES
(1, 'Java', 'ADVANCED'),
(1, 'Spring Boot', 'INTERMEDIATE'),
(2, 'Python', 'ADVANCED'),
(2, 'SQL', 'INTERMEDIATE');

--------------------------------------------------
-- LANGUAGES
--------------------------------------------------
INSERT INTO languages (student_id, language_name, level)
VALUES
(1, 'English', 'ADVANCED'),
(2, 'English', 'ADVANCED'),
(2, 'German', 'BEGINNER');

--------------------------------------------------
-- INTERESTS
--------------------------------------------------
INSERT INTO interests (student_id, interest_name)
VALUES
(1, 'Web Development'),
(2, 'Data Analysis');

--------------------------------------------------
-- MAP CV ↔ ELEMENTS
--------------------------------------------------
INSERT INTO cv_education_map VALUES (1,1),(2,2);
INSERT INTO cv_experience_map VALUES (1,1),(2,2);
INSERT INTO cv_skills_map VALUES (1,1),(1,2),(2,3),(2,4);
INSERT INTO cv_languages_map VALUES (1,1),(2,2),(2,3);
INSERT INTO cv_interests_map VALUES (1,1),(2,2);

--------------------------------------------------
-- TECHNOLOGIES
--------------------------------------------------
INSERT INTO technologies (name) VALUES
('Java'), ('Spring Boot'), ('Angular'), ('Python'), ('SQL');

--------------------------------------------------
-- INTERNSHIPS
--------------------------------------------------
INSERT INTO internships (company_id, title, description, location, start_date, end_date, requirements)
VALUES
(1, 'Java Intern', 'Backend dev', 'Sarajevo', '2026-04-01', '2026-07-01', 'Java'),
(2, 'Python Intern', 'Data analysis', 'Tuzla', '2026-04-15', '2026-07-15', 'Python');

--------------------------------------------------
-- APPLICATIONS
--------------------------------------------------
INSERT INTO applications (student_id, internship_id, status)
VALUES
(1, 1, 'APPLIED'),
(2, 2, 'APPLIED');