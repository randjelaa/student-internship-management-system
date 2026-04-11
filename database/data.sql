USE internship_system;

--------------------------------------------------
-- USERS
--------------------------------------------------
INSERT INTO users (email, password, role) VALUES

-- 🎓 FACULTY (2)
('harvard@faculty.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_FACULTY'),
('mit@faculty.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_FACULTY'),

-- 👨‍🎓 STUDENTS (20)
('john.smith@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('emma.johnson@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('liam.williams@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('olivia.brown@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('noah.jones@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('ava.garcia@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('william.miller@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('sophia.davis@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('james.rodriguez@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('isabella.martinez@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('benjamin.hernandez@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('mia.lopez@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('lucas.gonzalez@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('amelia.wilson@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('henry.anderson@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('evelyn.thomas@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('alexander.taylor@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('harper.moore@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('daniel.jackson@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),
('abigail.martin@student.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_STUDENT'),

-- 🏢 COMPANIES (10)
('techcorp@company.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_COMPANY'),
('innovatex@company.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_COMPANY'),
('softworks@company.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_COMPANY'),
('datastream@company.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_COMPANY'),
('cloudbase@company.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_COMPANY'),
('nextgen@company.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_COMPANY'),
('codehub@company.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_COMPANY'),
('alphatech@company.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_COMPANY'),
('byteforge@company.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_COMPANY'),
('cybersoft@company.com', '$2a$10$YnFb9hlBWb10jp0zmgBW9O0GA54Pf4SqK5279f0Fa8lacFY8tmKQq', 'ROLE_COMPANY');

--------------------------------------------------
-- STUDENTS
--------------------------------------------------
INSERT INTO students (user_id, first_name, last_name, index_number, faculty, year_of_study) VALUES

-- 🎓 HARVARD (user_id 3–12)
(3, 'John', 'Smith', 'HARV001', 'Harvard', 3),
(4, 'Emma', 'Johnson', 'HARV002', 'Harvard', 2),
(5, 'Liam', 'Williams', 'HARV003', 'Harvard', 1),
(6, 'Olivia', 'Brown', 'HARV004', 'Harvard', 4),
(7, 'Noah', 'Jones', 'HARV005', 'Harvard', 3),
(8, 'Ava', 'Garcia', 'HARV006', 'Harvard', 2),
(9, 'William', 'Miller', 'HARV007', 'Harvard', 1),
(10, 'Sophia', 'Davis', 'HARV008', 'Harvard', 4),
(11, 'James', 'Rodriguez', 'HARV009', 'Harvard', 3),
(12, 'Isabella', 'Martinez', 'HARV010', 'Harvard', 2),

-- 🎓 MIT (user_id 13–22)
(13, 'Benjamin', 'Hernandez', 'MIT001', 'MIT', 3),
(14, 'Mia', 'Lopez', 'MIT002', 'MIT', 2),
(15, 'Lucas', 'Gonzalez', 'MIT003', 'MIT', 1),
(16, 'Amelia', 'Wilson', 'MIT004', 'MIT', 4),
(17, 'Henry', 'Anderson', 'MIT005', 'MIT', 3),
(18, 'Evelyn', 'Thomas', 'MIT006', 'MIT', 2),
(19, 'Alexander', 'Taylor', 'MIT007', 'MIT', 1),
(20, 'Harper', 'Moore', 'MIT008', 'MIT', 4),
(21, 'Daniel', 'Jackson', 'MIT009', 'MIT', 3),
(22, 'Abigail', 'Martin', 'MIT010', 'MIT', 2);

--------------------------------------------------
-- COMPANIES
--------------------------------------------------
INSERT INTO companies (user_id, name, description, website) VALUES

(23, 'TechCorp Solutions', 'Full-stack software development company specializing in enterprise applications and scalable systems.', 'https://techcorp.com'),
(24, 'InnovateX', 'Innovative startup focused on AI-driven products and machine learning solutions.', 'https://innovatex.com'),
(25, 'SoftWorks Inc.', 'Custom software development and consulting services for global clients.', 'https://softworks.com'),
(26, 'DataStream Analytics', 'Data engineering and analytics company helping businesses make data-driven decisions.', 'https://datastream.com'),
(27, 'CloudBase Systems', 'Cloud infrastructure and DevOps solutions provider.', 'https://cloudbase.com'),
(28, 'NextGen Technologies', 'Next-generation web and mobile application development company.', 'https://nextgen.com'),
(29, 'CodeHub', 'Agile development team delivering modern web applications and APIs.', 'https://codehub.com'),
(30, 'AlphaTech', 'Technology company focused on cybersecurity and backend systems.', 'https://alphatech.com'),
(31, 'ByteForge', 'Engineering high-performance software systems and microservices.', 'https://byteforge.com'),
(32, 'CyberSoft Solutions', 'Enterprise IT solutions with focus on security and scalability.', 'https://cybersoft.com');

--------------------------------------------------
-- INTERNSHIPS
--------------------------------------------------
INSERT INTO internships (company_id, title, description, location, start_date, end_date, requirements) VALUES

-- 🏢 1 TechCorp Solutions
(1, 'Frontend Developer Intern', 'Work on modern web applications using Angular and REST APIs.', 'Remote', '2026-06-01', '2026-08-31', 'HTML, CSS, JavaScript, Angular basics'),
(1, 'Backend Developer Intern', 'Develop scalable backend services using Java and Spring Boot.', 'New York', '2026-06-01', '2026-08-31', 'Java, Spring Boot, SQL'),
(1, 'QA Intern', 'Assist in testing web applications and writing test cases.', 'Remote', '2026-06-01', '2026-08-31', 'Attention to detail, basic testing knowledge'),

-- 🏢 2 InnovateX
(2, 'AI Intern', 'Work on machine learning models and data pipelines.', 'San Francisco', '2026-06-01', '2026-09-01', 'Python, ML basics'),
(2, 'Data Analyst Intern', 'Analyze datasets and build reports.', 'Remote', '2026-06-01', '2026-09-01', 'SQL, Excel, Python'),
(2, 'Backend Intern', 'Support API development for AI systems.', 'Remote', '2026-06-01', '2026-09-01', 'Node.js or Java'),

-- 🏢 3 SoftWorks
(3, 'Full Stack Intern', 'Work across frontend and backend systems.', 'Chicago', '2026-06-01', '2026-08-31', 'JS, Java, databases'),
(3, 'Frontend Intern', 'Build UI components and improve UX.', 'Remote', '2026-06-01', '2026-08-31', 'React or Angular'),
(3, 'QA Intern', 'Test enterprise applications.', 'Remote', '2026-06-01', '2026-08-31', 'Manual testing'),

-- 🏢 4 DataStream
(4, 'Data Engineer Intern', 'Build ETL pipelines and manage data.', 'Boston', '2026-06-01', '2026-08-31', 'Python, SQL'),
(4, 'BI Intern', 'Create dashboards and reports.', 'Remote', '2026-06-01', '2026-08-31', 'Power BI, SQL'),
(4, 'Backend Intern', 'Support data APIs.', 'Remote', '2026-06-01', '2026-08-31', 'Java, APIs'),

-- 🏢 5 CloudBase
(5, 'DevOps Intern', 'Work with CI/CD and cloud deployments.', 'Seattle', '2026-06-01', '2026-08-31', 'Docker, AWS basics'),
(5, 'Backend Intern', 'Develop cloud services.', 'Remote', '2026-06-01', '2026-08-31', 'Java, microservices'),
(5, 'Cloud Intern', 'Assist in cloud infrastructure setup.', 'Remote', '2026-06-01', '2026-08-31', 'AWS, Linux'),

-- 🏢 6 NextGen
(6, 'Frontend Intern', 'Develop modern SPA apps.', 'Los Angeles', '2026-06-01', '2026-08-31', 'Angular/React'),
(6, 'Mobile Intern', 'Work on mobile applications.', 'Remote', '2026-06-01', '2026-08-31', 'Flutter or React Native'),
(6, 'Backend Intern', 'Build REST APIs.', 'Remote', '2026-06-01', '2026-08-31', 'Node.js'),

-- 🏢 7 CodeHub
(7, 'Full Stack Intern', 'Develop web apps end-to-end.', 'Remote', '2026-06-01', '2026-08-31', 'JS, Java'),
(7, 'QA Intern', 'Write and execute test cases.', 'Remote', '2026-06-01', '2026-08-31', 'Testing basics'),
(7, 'Frontend Intern', 'UI development tasks.', 'Remote', '2026-06-01', '2026-08-31', 'HTML, CSS'),

-- 🏢 8 AlphaTech
(8, 'Cybersecurity Intern', 'Assist in security testing.', 'Washington', '2026-06-01', '2026-08-31', 'Security basics'),
(8, 'Backend Intern', 'Develop secure backend systems.', 'Remote', '2026-06-01', '2026-08-31', 'Java, Spring'),
(8, 'DevOps Intern', 'Support infrastructure security.', 'Remote', '2026-06-01', '2026-08-31', 'Linux, Docker'),

-- 🏢 9 ByteForge
(9, 'Backend Intern', 'Work on microservices.', 'Austin', '2026-06-01', '2026-08-31', 'Java, microservices'),
(9, 'Full Stack Intern', 'Build scalable apps.', 'Remote', '2026-06-01', '2026-08-31', 'JS + backend'),
(9, 'QA Intern', 'Test system performance.', 'Remote', '2026-06-01', '2026-08-31', 'Testing'),

-- 🏢 10 CyberSoft
(10, 'Security Intern', 'Work on application security.', 'New York', '2026-06-01', '2026-08-31', 'Security basics'),
(10, 'Backend Intern', 'Enterprise backend systems.', 'Remote', '2026-06-01', '2026-08-31', 'Java'),
(10, 'Data Intern', 'Analyze system logs and data.', 'Remote', '2026-06-01', '2026-08-31', 'SQL, Python');

--------------------------------------------------
-- TECHNOLOGIES
--------------------------------------------------
INSERT INTO technologies (name) VALUES
('Java'),
('Spring Boot'),
('Angular'),
('React'),
('Node.js'),
('Python'),
('Django'),
('MySQL'),
('PostgreSQL'),
('Docker'),
('Git'),
('TypeScript');

--------------------------------------------------
-- MAP INTERNSHIP ↔ TECHNOLOGIES
--------------------------------------------------
INSERT INTO internship_technologies (internship_id, technology_id) VALUES
(1, 1), (1, 2), (1, 8),
(2, 1), (2, 2), (2, 10),
(3, 6), (3, 7), (3, 8),
(4, 6), (4, 8), (4, 9),
(5, 3), (5, 12), (5, 11),
(6, 3), (6, 4), (6, 12),
(7, 4), (7, 11),
(8, 3), (8, 4), (8, 10),
(9, 1), (9, 3), (9, 10),
(10, 2), (10, 4), (10, 11),
(11, 1), (11, 3), (11, 12),
(12, 6), (12, 3), (12, 10),
(13, 10), (13, 11),
(14, 10), (14, 8), (14, 11),
(15, 2), (15, 10), (15, 11),
(16, 1), (16, 10), (16, 11),
(17, 6), (17, 8),
(18, 6), (18, 9),
(19, 6), (19, 7), (19, 8),
(20, 6), (20, 10), (20, 11),
(21, 1), (21, 4), (21, 10),
(22, 2), (22, 3), (22, 8),
(23, 4), (23, 6), (23, 11),
(24, 3), (24, 12), (24, 10),
(25, 3), (25, 4), (25, 12),
(26, 1), (26, 2), (26, 11),
(27, 6), (27, 10), (27, 11),
(28, 4), (28, 10),
(29, 1), (29, 2), (29, 8),
(30, 3), (30, 4), (30, 11);


--------------------------------------------------
-- CV
--------------------------------------------------
INSERT INTO cv (student_id, photo_url, summary) VALUES

(1, 'https://randomuser.me/api/portraits/men/1.jpg', 'Backend-focused computer science student interested in scalable systems and APIs.'),
(2, 'https://randomuser.me/api/portraits/women/2.jpg', 'Frontend developer passionate about Angular and modern UI design.'),
(3, 'https://randomuser.me/api/portraits/men/3.jpg', 'Full-stack developer with interest in cloud and distributed systems.'),
(4, 'https://randomuser.me/api/portraits/women/4.jpg', 'Software engineering student focused on data science and analytics.'),
(5, 'https://randomuser.me/api/portraits/men/5.jpg', 'Java backend developer interested in Spring Boot and microservices.'),
(6, 'https://randomuser.me/api/portraits/women/6.jpg', 'Mobile developer focused on Flutter and cross-platform apps.'),
(7, 'https://randomuser.me/api/portraits/men/7.jpg', 'DevOps enthusiast working with AWS and Docker.'),
(8, 'https://randomuser.me/api/portraits/women/8.jpg', 'QA engineer focused on automation testing and software quality.'),
(9, 'https://randomuser.me/api/portraits/men/9.jpg', 'Cybersecurity student interested in penetration testing.'),
(10, 'https://randomuser.me/api/portraits/women/10.jpg', 'Data analyst working with Python, SQL and visualization tools.'),
(11, 'https://randomuser.me/api/portraits/men/11.jpg', 'AI enthusiast focused on machine learning models.'),
(12, 'https://randomuser.me/api/portraits/women/12.jpg', 'Frontend engineer passionate about UX and design systems.'),
(13, 'https://randomuser.me/api/portraits/men/13.jpg', 'Backend developer working with Java and Spring ecosystem.'),
(14, 'https://randomuser.me/api/portraits/women/14.jpg', 'Data science student interested in big data and analytics.'),
(15, 'https://randomuser.me/api/portraits/men/15.jpg', 'Full-stack developer focused on web applications and APIs.');

--------------------------------------------------
-- EDUCATION
--------------------------------------------------
INSERT INTO education (student_id, institution, degree, field_of_study, start_year, end_year) VALUES

(1, 'Harvard University', 'BSc', 'Computer Science', 2022, NULL),
(2, 'Harvard University', 'BSc', 'Software Engineering', 2021, NULL),
(3, 'Harvard University', 'BSc', 'Information Systems', 2023, NULL),
(4, 'Harvard University', 'BSc', 'Data Science', 2020, NULL),
(5, 'Harvard University', 'BSc', 'Computer Engineering', 2022, NULL),
(6, 'Harvard University', 'BSc', 'Mobile Computing', 2021, NULL),
(7, 'MIT', 'BSc', 'Computer Science', 2022, NULL),
(8, 'MIT', 'BSc', 'Software Engineering', 2020, NULL),
(9, 'MIT', 'BSc', 'Cybersecurity', 2022, NULL),
(10, 'MIT', 'BSc', 'Data Analytics', 2021, NULL),
(11, 'MIT', 'BSc', 'Artificial Intelligence', 2022, NULL),
(12, 'MIT', 'BSc', 'UX Engineering', 2021, NULL),
(13, 'MIT', 'BSc', 'Backend Systems', 2020, NULL),
(14, 'MIT', 'BSc', 'Data Science', 2022, NULL),
(15, 'MIT', 'BSc', 'Full Stack Development', 2021, NULL);

--------------------------------------------------
-- EXPERIENCE
--------------------------------------------------
INSERT INTO experience (student_id, company_name, position, description, start_date, end_date) VALUES

(1, 'TechCorp', 'Backend Intern', 'Worked on REST APIs and microservices.', '2025-06-01', '2025-08-31'),

(2, 'InnovateX', 'Frontend Intern', 'Built Angular components and UI systems.', '2025-06-01', '2025-08-31'),
(2, 'CodeHub', 'Junior Developer', 'Worked on small frontend fixes.', '2024-06-01', '2024-08-31'),

(3, 'SoftWorks', 'Full Stack Intern', 'Worked on full stack web applications.', '2025-06-01', '2025-08-31'),

(4, 'DataStream', 'Data Intern', 'Analyzed datasets and built reports.', '2025-06-01', '2025-08-31'),
(4, 'Harvard Lab', 'Research Assistant', 'Assisted in data research projects.', '2024-06-01', '2024-09-01'),

(5, 'TechCorp', 'Backend Intern', 'Spring Boot APIs development.', '2025-06-01', '2025-08-31'),

(6, 'NextGen', 'Mobile Intern', 'Flutter app development.', '2025-06-01', '2025-08-31'),

(7, 'CloudBase', 'DevOps Intern', 'Worked with Docker and AWS.', '2025-06-01', '2025-08-31'),

(8, 'CodeHub', 'QA Intern', 'Manual and automated testing.', '2025-06-01', '2025-08-31'),

(9, 'CyberSoft', 'Security Intern', 'Penetration testing and audits.', '2025-06-01', '2025-08-31'),

(10, 'DataStream', 'Data Analyst Intern', 'SQL reporting and dashboards.', '2025-06-01', '2025-08-31'),

(11, 'AI Labs', 'ML Intern', 'Built machine learning models.', '2025-06-01', '2025-08-31'),

(12, 'UI Studio', 'Frontend Intern', 'UX/UI improvements.', '2025-06-01', '2025-08-31'),

(13, 'ByteForge', 'Backend Intern', 'Microservices development.', '2025-06-01', '2025-08-31'),

(14, 'DataStream', 'Data Intern', 'Big data processing pipelines.', '2025-06-01', '2025-08-31'),

(15, 'TechCorp', 'Full Stack Intern', 'Web application development.', '2025-06-01', '2025-08-31');

--------------------------------------------------
-- SKILLS
--------------------------------------------------
INSERT INTO skills (student_id, skill_name, skill_level) VALUES

(1, 'Java', 'Advanced'),
(1, 'Spring Boot', 'Intermediate'),

(2, 'Angular', 'Advanced'),
(2, 'TypeScript', 'Advanced'),
(2, 'CSS', 'Intermediate'),

(3, 'Java', 'Advanced'),
(3, 'Docker', 'Intermediate'),

(4, 'Python', 'Advanced'),
(4, 'Pandas', 'Advanced'),
(4, 'SQL', 'Advanced'),

(5, 'Java', 'Advanced'),
(5, 'Microservices', 'Intermediate'),

(6, 'Flutter', 'Advanced'),

(7, 'AWS', 'Intermediate'),
(7, 'Docker', 'Intermediate'),

(8, 'Testing', 'Advanced'),
(8, 'Selenium', 'Intermediate'),

(9, 'Cybersecurity', 'Advanced'),

(10, 'SQL', 'Advanced'),
(10, 'Power BI', 'Intermediate'),

(11, 'Machine Learning', 'Advanced'),
(11, 'Python', 'Advanced'),

(12, 'Angular', 'Advanced'),
(12, 'UX Design', 'Intermediate'),

(13, 'Java', 'Advanced'),
(13, 'Spring Boot', 'Advanced'),

(14, 'Big Data', 'Advanced'),
(14, 'Python', 'Advanced'),

(15, 'Full Stack', 'Advanced'),
(15, 'React', 'Intermediate');

--------------------------------------------------
-- LANGUAGES
--------------------------------------------------
INSERT INTO languages (student_id, language_name, level) VALUES

(1, 'English', 'C1'),
(2, 'English', 'C1'),
(2, 'Spanish', 'B1'),

(3, 'English', 'C1'),
(4, 'English', 'C1'),
(4, 'French', 'A2'),

(5, 'English', 'C1'),
(6, 'English', 'B2'),
(7, 'English', 'C1'),

(8, 'English', 'C1'),
(9, 'English', 'C1'),
(9, 'German', 'B1'),

(10, 'English', 'C1'),
(11, 'English', 'C1'),

(12, 'English', 'C1'),
(12, 'Spanish', 'B2'),

(13, 'English', 'C1'),
(14, 'English', 'C1'),
(14, 'German', 'A2'),

(15, 'English', 'C1');

--------------------------------------------------
-- INTERESTS
--------------------------------------------------
INSERT INTO interests (student_id, interest_name) VALUES

(1, 'Backend Development'),
(1, 'Distributed Systems'),

(2, 'UI/UX Design'),
(2, 'Frontend Engineering'),

(3, 'Cloud Computing'),

(4, 'Data Science'),
(4, 'Machine Learning'),

(5, 'Microservices'),

(6, 'Mobile Apps'),

(7, 'DevOps'),

(8, 'Quality Assurance'),

(9, 'Cybersecurity'),
(9, 'Ethical Hacking'),

(10, 'Data Analytics'),

(11, 'Artificial Intelligence'),

(12, 'UX Design'),

(13, 'Backend Systems'),

(14, 'Big Data'),

(15, 'Full Stack Development'),
(15, 'Web Development');

--------------------------------------------------
-- MAP CV ↔ ELEMENTS
--------------------------------------------------
INSERT INTO cv_education_map (cv_id, education_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 11),
(12, 12),
(13, 13),
(14, 14),
(15, 15);

INSERT INTO cv_experience_map (cv_id, experience_id) VALUES

(1, 1),

(2, 2),
(2, 3),

(3, 4),

(4, 5),
(4, 6),

(5, 7),

(6, 8),

(7, 9),

(8, 10),

(9, 11),

(10, 12),

(11, 13),

(12, 14),

(13, 15),

(14, 6),

(15, 1);


INSERT INTO cv_skills_map (cv_id, skill_id) VALUES

(1, 1),
(1, 2),

(2, 3),
(2, 4),
(2, 5),

(3, 6),
(3, 7),

(4, 8),
(4, 9),
(4, 10),

(5, 11),
(5, 12),

(6, 13),

(7, 14),
(7, 15),

(8, 16),
(8, 17),

(9, 18),

(10, 19),
(10, 20),

(11, 21),
(11, 22),

(12, 23),
(12, 24),

(13, 25),
(13, 26),

(14, 27),
(14, 28),

(15, 29),
(15, 30);


INSERT INTO cv_languages_map (cv_id, language_id) VALUES

(1, 1),

(2, 2),
(2, 3),

(3, 4),

(4, 5),
(4, 6),

(5, 7),

(6, 8),

(7, 9),

(8, 10),

(9, 11),
(9, 12),

(10, 13),

(11, 14),

(12, 15),
(12, 16),

(13, 17),

(14, 18),
(14, 19),

(15, 20);


INSERT INTO cv_interests_map (cv_id, interest_id) VALUES

(1, 1),
(1, 2),

(2, 3),
(2, 4),

(3, 5),

(4, 6),
(4, 7),

(5, 8),

(6, 9),

(7, 10),

(8, 11),

(9, 12),
(9, 13),

(10, 14),

(11, 15),

(12, 16),

(13, 17),

(14, 18),

(15, 19),
(15, 20);

--------------------------------------------------
-- APPLICATIONS
--------------------------------------------------
INSERT INTO applications (student_id, internship_id, status) VALUES

-- STUDENT 1
(1, 1, 'ACCEPTED'),
(1, 2, 'PENDING'),

-- STUDENT 2
(2, 3, 'ACCEPTED'),
(2, 4, 'REJECTED'),

-- STUDENT 3
(3, 5, 'PENDING'),

-- STUDENT 4
(4, 6, 'ACCEPTED'),
(4, 7, 'PENDING'),

-- STUDENT 5
(5, 8, 'ACCEPTED'),

-- STUDENT 6
(6, 9, 'PENDING'),
(6, 10, 'REJECTED'),

-- STUDENT 7
(7, 11, 'ACCEPTED'),

-- STUDENT 8
(8, 12, 'PENDING'),

-- STUDENT 9
(9, 13, 'ACCEPTED'),
(9, 14, 'REJECTED'),

-- STUDENT 10
(10, 15, 'PENDING'),

-- STUDENT 11
(11, 16, 'ACCEPTED'),

-- STUDENT 12
(12, 17, 'PENDING'),
(12, 18, 'REJECTED'),

-- STUDENT 13
(13, 19, 'ACCEPTED'),

-- STUDENT 14
(14, 20, 'PENDING'),

-- STUDENT 15
(15, 21, 'ACCEPTED'),
(15, 22, 'PENDING');


--------------------------------------------------
-- WORK-LOGS
--------------------------------------------------

INSERT INTO work_logs (student_id, internship_id, description, start_date, end_date) VALUES

-- =========================
-- STUDENT 1 (6 logs - TOP)
-- =========================
(1, 1, 'Implemented authentication API endpoints and JWT integration.', '2025-06-01', '2025-06-07'),
(1, 1, 'Worked on user profile service and database optimization.', '2025-06-08', '2025-06-14'),
(1, 1, 'Fixed bugs in payment microservice.', '2025-06-15', '2025-06-21'),
(1, 1, 'Added unit tests for core services.', '2025-06-22', '2025-06-28'),
(1, 1, 'Improved API response time using caching.', '2025-06-29', '2025-07-05'),
(1, 1, 'Code review and refactoring legacy modules.', '2025-07-06', '2025-07-12'),

-- =========================
-- STUDENT 2 (2 logs)
-- =========================
(2, 3, 'Created Angular components for dashboard UI.', '2025-06-01', '2025-06-07'),
(2, 3, 'Fixed styling issues and responsive layout bugs.', '2025-06-08', '2025-06-14'),

-- =========================
-- STUDENT 3 (0 logs)
-- =========================

-- =========================
-- STUDENT 4 (4 logs)
-- =========================
(4, 6, 'Built data preprocessing pipeline in Python.', '2025-06-01', '2025-06-07'),
(4, 6, 'Analyzed datasets and generated reports.', '2025-06-08', '2025-06-14'),
(4, 6, 'Created visualization dashboards.', '2025-06-15', '2025-06-21'),
(4, 6, 'Optimized SQL queries for analytics.', '2025-06-22', '2025-06-28'),

-- =========================
-- STUDENT 5 (2 logs)
-- =========================
(5, 8, 'Worked on Spring Boot REST API endpoints.', '2025-06-01', '2025-06-07'),
(5, 8, 'Implemented validation and error handling.', '2025-06-08', '2025-06-14'),

-- =========================
-- STUDENT 6 (0 logs)
-- =========================

-- =========================
-- STUDENT 7 (5 logs)
-- =========================
(7, 11, 'Configured Docker containers for deployment.', '2025-06-01', '2025-06-07'),
(7, 11, 'Set up AWS EC2 environment.', '2025-06-08', '2025-06-14'),
(7, 11, 'Implemented CI/CD pipeline.', '2025-06-15', '2025-06-21'),
(7, 11, 'Monitoring and logging setup.', '2025-06-22', '2025-06-28'),
(7, 11, 'Infrastructure optimization.', '2025-06-29', '2025-07-05'),

-- =========================
-- STUDENT 8 (4 logs)
-- =========================
(8, 12, 'Wrote test cases for frontend components.', '2025-06-01', '2025-06-07'),
(8, 12, 'Automated regression tests using Selenium.', '2025-06-08', '2025-06-14'),
(8, 12, 'Reported and tracked bugs.', '2025-06-15', '2025-06-21'),
(8, 12, 'Test documentation improvements.', '2025-06-22', '2025-06-28'),

-- =========================
-- STUDENT 9 (6 logs - TOP)
-- =========================
(9, 13, 'Performed penetration testing on web application.', '2025-06-01', '2025-06-07'),
(9, 13, 'Identified SQL injection vulnerabilities.', '2025-06-08', '2025-06-14'),
(9, 13, 'Security audit report preparation.', '2025-06-15', '2025-06-21'),
(9, 13, 'Fixed authentication security issues.', '2025-06-22', '2025-06-28'),
(9, 13, 'Network security monitoring setup.', '2025-06-29', '2025-07-05'),
(9, 13, 'Final security assessment and report.', '2025-07-06', '2025-07-12'),

-- =========================
-- STUDENT 10 (3 logs)
-- =========================
(10, 15, 'Created SQL queries for reporting dashboard.', '2025-06-01', '2025-06-07'),
(10, 15, 'Built Power BI reports.', '2025-06-08', '2025-06-14'),
(10, 15, 'Data cleaning and transformation tasks.', '2025-06-15', '2025-06-21'),

-- =========================
-- STUDENT 11 (0 logs)
-- =========================

-- =========================
-- STUDENT 12 (4 logs)
-- =========================
(12, 17, 'Improved UX flows for onboarding screens.', '2025-06-01', '2025-06-07'),
(12, 17, 'Designed new UI components.', '2025-06-08', '2025-06-14'),
(12, 17, 'A/B testing for UI changes.', '2025-06-15', '2025-06-21'),
(12, 17, 'Accessibility improvements.', '2025-06-22', '2025-06-28'),

-- =========================
-- STUDENT 13 (5 logs)
-- =========================
(13, 19, 'Developed REST APIs for backend services.', '2025-06-01', '2025-06-07'),
(13, 19, 'Database schema optimization.', '2025-06-08', '2025-06-14'),
(13, 19, 'Implemented caching layer.', '2025-06-15', '2025-06-21'),
(13, 19, 'Bug fixes and performance improvements.', '2025-06-22', '2025-06-28'),
(13, 19, 'Code refactoring.', '2025-06-29', '2025-07-05'),

-- =========================
-- STUDENT 14 (2 logs)
-- =========================
(14, 20, 'Built ETL pipeline for data processing.', '2025-06-01', '2025-06-07'),
(14, 20, 'Created data visualizations.', '2025-06-08', '2025-06-14'),

-- =========================
-- STUDENT 15 (6 logs)
-- =========================
(15, 21, 'Developed full-stack feature for internship portal.', '2025-06-01', '2025-06-07'),
(15, 21, 'Implemented frontend React components.', '2025-06-08', '2025-06-14'),
(15, 21, 'Backend API integration.', '2025-06-15', '2025-06-21'),
(15, 21, 'Bug fixing and testing.', '2025-06-22', '2025-06-28'),
(15, 21, 'Performance optimization.', '2025-06-29', '2025-07-05'),
(15, 21, 'Final deployment and review.', '2025-07-06', '2025-07-12');