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
-- TECHNOLOGIES
--------------------------------------------------
INSERT INTO technologies (name) VALUES
('Java'),
('Spring Boot'),
('Angular'),
('React'),
('Python'),
('SQL');

--------------------------------------------------
-- INTERNSHIPS
--------------------------------------------------
INSERT INTO internships (company_id, title, description, location, start_date, end_date, requirements)
VALUES
(1, 'Java Developer Intern', 'Develop web applications using Java', 'Sarajevo', '2026-04-01', '2026-07-01', 'Java, Spring Boot'),
(1, 'Frontend Intern', 'Work on Angular projects', 'Sarajevo', '2026-05-01', '2026-08-01', 'Angular, HTML, CSS'),
(2, 'Python Data Analyst Intern', 'Analyze datasets using Python', 'Tuzla', '2026-04-15', '2026-07-15', 'Python, SQL');

--------------------------------------------------
-- INTERNSHIP_TECHNOLOGIES
--------------------------------------------------
INSERT INTO internship_technologies (internship_id, technology_id) VALUES
(1, 1), -- Java Developer Intern → Java
(1, 2), -- Java Developer Intern → Spring Boot
(2, 3), -- Frontend Intern → Angular
(3, 5), -- Python Data Analyst → Python
(3, 6); -- Python Data Analyst → SQL

--------------------------------------------------
-- CV
--------------------------------------------------
INSERT INTO cv (student_id, photo_url, summary)
VALUES
(1, 'https://example.com/photo1.jpg', 'Passionate about backend development.'),
(2, 'https://example.com/photo2.jpg', 'Interested in data analysis and visualization.');

--------------------------------------------------
-- CV EDUCATION
--------------------------------------------------
INSERT INTO cv_education (cv_id, institution, degree, field_of_study, start_year, end_year)
VALUES
(1, 'University of Sarajevo', 'BSc', 'Computer Science', 2020, 2023),
(2, 'University of Tuzla', 'BSc', 'Information Systems', 2021, 2024);

--------------------------------------------------
-- CV EXPERIENCE
--------------------------------------------------
INSERT INTO cv_experience (cv_id, company_name, position, description, start_date, end_date)
VALUES
(1, 'Intern IT Solutions', 'Junior Developer', 'Worked on internal tools', '2022-06-01', '2022-09-01'),
(2, 'Data Labs', 'Data Intern', 'Assisted in data cleaning and reporting', '2023-06-01', '2023-08-01');

--------------------------------------------------
-- CV SKILLS
--------------------------------------------------
INSERT INTO cv_skills (cv_id, skill_name, skill_level)
VALUES
(1, 'Java', 'ADVANCED'),
(1, 'Spring Boot', 'INTERMEDIATE'),
(2, 'Python', 'ADVANCED'),
(2, 'SQL', 'INTERMEDIATE');

--------------------------------------------------
-- CV LANGUAGES
--------------------------------------------------
INSERT INTO cv_languages (cv_id, language_name, level)
VALUES
(1, 'English', 'ADVANCED'),
(2, 'English', 'ADVANCED'),
(2, 'German', 'BEGINNER');

--------------------------------------------------
-- CV INTERESTS
--------------------------------------------------
INSERT INTO cv_interests (cv_id, interest_name)
VALUES
(1, 'Web Development'),
(1, 'Machine Learning'),
(2, 'Data Analysis'),
(2, 'Statistics');

--------------------------------------------------
-- APPLICATIONS
--------------------------------------------------
INSERT INTO applications (student_id, internship_id, status)
VALUES
(1, 1, 'APPLIED'),
(2, 3, 'APPLIED');

--------------------------------------------------
-- WORK_LOGS
--------------------------------------------------
INSERT INTO work_logs (student_id, internship_id, week_number, description)
VALUES
(1, 1, 1, 'Setup development environment, created initial backend modules'),
(2, 3, 1, 'Cleaned dataset, wrote initial SQL queries');

--------------------------------------------------
-- GRADES
--------------------------------------------------
INSERT INTO grades (student_id, internship_id, company_comment, faculty_grade)
VALUES
(1, 1, 'Good progress, learning quickly', 9),
(2, 3, 'Shows initiative and analytical skills', 8);

--------------------------------------------------
-- RECOMMENDATIONS
--------------------------------------------------
INSERT INTO recommendations (student_id, internship_id, score, explanation)
VALUES
(1, 1, 0.95, 'Strong match for Java backend projects'),
(2, 3, 0.90, 'Excellent Python and SQL skills, fits data analyst role');