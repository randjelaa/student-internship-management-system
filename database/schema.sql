CREATE DATABASE IF NOT EXISTS internship_system;
USE internship_system;

--------------------------------------------------
-- USERS
--------------------------------------------------

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

--------------------------------------------------
-- STUDENTS
--------------------------------------------------

CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    index_number VARCHAR(50),
    faculty VARCHAR(255),
    year_of_study INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

--------------------------------------------------
-- COMPANIES
--------------------------------------------------

CREATE TABLE companies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    website VARCHAR(255),
    active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

--------------------------------------------------
-- CV
--------------------------------------------------

CREATE TABLE cv (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    photo_url VARCHAR(255),
    summary TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

--------------------------------------------------
-- EDUCATION (NOW OWNED BY STUDENT)
--------------------------------------------------

CREATE TABLE education (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    institution VARCHAR(255),
    degree VARCHAR(255),
    field_of_study VARCHAR(255),
    start_year INT,
    end_year INT,

    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

--------------------------------------------------
-- EXPERIENCE
--------------------------------------------------

CREATE TABLE experience (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    company_name VARCHAR(255),
    position VARCHAR(255),
    description TEXT,
    start_date DATE,
    end_date DATE,

    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

--------------------------------------------------
-- SKILLS
--------------------------------------------------

CREATE TABLE skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    skill_name VARCHAR(255),
    skill_level VARCHAR(50),

    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

--------------------------------------------------
-- LANGUAGES
--------------------------------------------------

CREATE TABLE languages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    language_name VARCHAR(100),
    level VARCHAR(50),

    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

--------------------------------------------------
-- INTERESTS
--------------------------------------------------

CREATE TABLE interests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    interest_name VARCHAR(255),

    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

--------------------------------------------------
-- M:N MAP TABLES (CV ↔ ELEMENTS)
--------------------------------------------------

CREATE TABLE cv_education_map (
    cv_id BIGINT NOT NULL,
    education_id BIGINT NOT NULL,
    PRIMARY KEY (cv_id, education_id),
    FOREIGN KEY (cv_id) REFERENCES cv(id) ON DELETE CASCADE,
    FOREIGN KEY (education_id) REFERENCES education(id) ON DELETE CASCADE
);

CREATE TABLE cv_experience_map (
    cv_id BIGINT NOT NULL,
    experience_id BIGINT NOT NULL,
    PRIMARY KEY (cv_id, experience_id),
    FOREIGN KEY (cv_id) REFERENCES cv(id) ON DELETE CASCADE,
    FOREIGN KEY (experience_id) REFERENCES experience(id) ON DELETE CASCADE
);

CREATE TABLE cv_skills_map (
    cv_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    PRIMARY KEY (cv_id, skill_id),
    FOREIGN KEY (cv_id) REFERENCES cv(id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

CREATE TABLE cv_languages_map (
    cv_id BIGINT NOT NULL,
    language_id BIGINT NOT NULL,
    PRIMARY KEY (cv_id, language_id),
    FOREIGN KEY (cv_id) REFERENCES cv(id) ON DELETE CASCADE,
    FOREIGN KEY (language_id) REFERENCES languages(id) ON DELETE CASCADE
);

CREATE TABLE cv_interests_map (
    cv_id BIGINT NOT NULL,
    interest_id BIGINT NOT NULL,
    PRIMARY KEY (cv_id, interest_id),
    FOREIGN KEY (cv_id) REFERENCES cv(id) ON DELETE CASCADE,
    FOREIGN KEY (interest_id) REFERENCES interests(id) ON DELETE CASCADE
);

--------------------------------------------------
-- TECHNOLOGIES
--------------------------------------------------

CREATE TABLE technologies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE
);

--------------------------------------------------
-- INTERNSHIPS
--------------------------------------------------

CREATE TABLE internships (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_id BIGINT NOT NULL,
    title VARCHAR(255),
    description TEXT,
    location VARCHAR(255),
    start_date DATE,
    end_date DATE,
    requirements TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (company_id) REFERENCES companies(id) ON DELETE CASCADE
);

--------------------------------------------------
-- INTERNSHIP TECHNOLOGIES
--------------------------------------------------

CREATE TABLE internship_technologies (
    internship_id BIGINT NOT NULL,
    technology_id BIGINT NOT NULL,
    PRIMARY KEY (internship_id, technology_id),
    FOREIGN KEY (internship_id) REFERENCES internships(id) ON DELETE CASCADE,
    FOREIGN KEY (technology_id) REFERENCES technologies(id) ON DELETE CASCADE
);

--------------------------------------------------
-- APPLICATIONS
--------------------------------------------------

CREATE TABLE applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    status VARCHAR(50),
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    UNIQUE (student_id, internship_id),

    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (internship_id) REFERENCES internships(id) ON DELETE CASCADE
);

--------------------------------------------------
-- WORK LOGS
--------------------------------------------------

CREATE TABLE work_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (internship_id) REFERENCES internships(id) ON DELETE CASCADE
);

--------------------------------------------------
-- GRADES
--------------------------------------------------

CREATE TABLE grades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    company_comment TEXT,
    faculty_grade INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (internship_id) REFERENCES internships(id) ON DELETE CASCADE
);

--------------------------------------------------
-- RECOMMENDATIONS
--------------------------------------------------

CREATE TABLE recommendations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    score DECIMAL(3,2),
    explanation TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (internship_id) REFERENCES internships(id) ON DELETE CASCADE
);