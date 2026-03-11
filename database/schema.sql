CREATE DATABASE IF NOT EXISTS internship_system;
USE internship_system;

-- USERS

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

CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    index_number VARCHAR(50),
    faculty VARCHAR(255),
    year_of_study INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_student_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- COMPANIES

CREATE TABLE companies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    website VARCHAR(255),
    active BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_company_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- CV

CREATE TABLE cv (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    photo_url VARCHAR(255),
    summary TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_cv_student
        FOREIGN KEY (student_id)
        REFERENCES students(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- CV EDUCATION

CREATE TABLE cv_education (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cv_id BIGINT NOT NULL,
    institution VARCHAR(255),
    degree VARCHAR(255),
    field_of_study VARCHAR(255),
    start_year INT,
    end_year INT,

    CONSTRAINT fk_education_cv
        FOREIGN KEY (cv_id)
        REFERENCES cv(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- CV EXPERIENCE

CREATE TABLE cv_experience (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cv_id BIGINT NOT NULL,
    company_name VARCHAR(255),
    position VARCHAR(255),
    description TEXT,
    start_date DATE,
    end_date DATE,

    CONSTRAINT fk_experience_cv
        FOREIGN KEY (cv_id)
        REFERENCES cv(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- CV SKILLS

CREATE TABLE cv_skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cv_id BIGINT NOT NULL,
    skill_name VARCHAR(255),
    skill_level VARCHAR(50),

    CONSTRAINT fk_skills_cv
        FOREIGN KEY (cv_id)
        REFERENCES cv(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- CV LANGUAGES

CREATE TABLE cv_languages (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cv_id BIGINT NOT NULL,
    language_name VARCHAR(100),
    level VARCHAR(50),

    CONSTRAINT fk_languages_cv
        FOREIGN KEY (cv_id)
        REFERENCES cv(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- CV INTERESTS

CREATE TABLE cv_interests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cv_id BIGINT NOT NULL,
    interest_name VARCHAR(255),

    CONSTRAINT fk_interests_cv
        FOREIGN KEY (cv_id)
        REFERENCES cv(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- TECHNOLOGIES

CREATE TABLE technologies (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) UNIQUE
);

--------------------------------------------------

-- COMPANIES → INTERNSHIPS

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

    CONSTRAINT fk_internship_company
        FOREIGN KEY (company_id)
        REFERENCES companies(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- INTERNSHIP TECHNOLOGIES (many-to-many)

CREATE TABLE internship_technologies (
    internship_id BIGINT NOT NULL,
    technology_id BIGINT NOT NULL,

    PRIMARY KEY (internship_id, technology_id),

    CONSTRAINT fk_it_internship
        FOREIGN KEY (internship_id)
        REFERENCES internships(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_it_technology
        FOREIGN KEY (technology_id)
        REFERENCES technologies(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- APPLICATIONS

CREATE TABLE applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    status VARCHAR(50),
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_application_student
        FOREIGN KEY (student_id)
        REFERENCES students(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_application_internship
        FOREIGN KEY (internship_id)
        REFERENCES internships(id)
        ON DELETE CASCADE,

    CONSTRAINT unique_application
        UNIQUE (student_id, internship_id)
);

--------------------------------------------------

-- WORK LOGS

CREATE TABLE work_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    week_number INT,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_worklog_student
        FOREIGN KEY (student_id)
        REFERENCES students(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_worklog_internship
        FOREIGN KEY (internship_id)
        REFERENCES internships(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- GRADES

CREATE TABLE grades (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    company_comment TEXT,
    faculty_grade INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_grade_student
        FOREIGN KEY (student_id)
        REFERENCES students(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_grade_internship
        FOREIGN KEY (internship_id)
        REFERENCES internships(id)
        ON DELETE CASCADE
);

--------------------------------------------------

-- AI RECOMMENDATIONS

CREATE TABLE recommendations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    internship_id BIGINT NOT NULL,
    score DECIMAL(3,2),
    explanation TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_recommendation_student
        FOREIGN KEY (student_id)
        REFERENCES students(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_recommendation_internship
        FOREIGN KEY (internship_id)
        REFERENCES internships(id)
        ON DELETE CASCADE
);