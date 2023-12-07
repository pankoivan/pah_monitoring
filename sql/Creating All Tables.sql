/* Контакты на главной странице */

CREATE TABLE main_page_contact (
	id SERIAL PRIMARY KEY,
	contact VARCHAR (256) UNIQUE NOT NULL,
	description VARCHAR (128) UNIQUE NOT NULL
);

/* Коды для регистрации */

CREATE TABLE registration_security_code (
	id SERIAL PRIMARY KEY,
	role VARCHAR (32) NOT NULL,
	hospital_id VARCHAR (512) NOT NULL,
	code UUID UNIQUE NOT NULL,
	expiration_date DATE NOT NULL
);

/* Коды для регистрации */

CREATE TABLE user_security_information (
	id SERIAL PRIMARY KEY,
	email VARCHAR (80) UNIQUE NOT NULL,
	password VARCHAR (256) NOT NULL
);

/* Коды для регистрации */

CREATE TABLE user_information (
	id SERIAL PRIMARY KEY,
	name VARCHAR (32) NOT NULL,
	lastname VARCHAR (64) NOT NULL,
	patronymic VARCHAR (32) NOT NULL,
	gender VARCHAR (8),
	birthdate DATE,
	phone_number VARCHAR (24)
);

/* Коды для регистрации */

CREATE TABLE hospital_employee_information (
	id SERIAL PRIMARY KEY,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL,
	experience INT
);

/* Коды для регистрации */

CREATE TABLE main_administrator (
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL
);

/* Коды для регистрации */

CREATE TABLE administrator (
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL,
	hospital_id VARCHAR (512) NOT NULL
);

/* Коды для регистрации */

CREATE TABLE doctor (
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL,
	hospital_id VARCHAR (512) NOT NULL,
	university VARCHAR (256)
);

/* Коды для регистрации */

CREATE TABLE patient (
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL,
	doctor_id INT REFERENCES doctor (id),
	hospital_id VARCHAR (512) NOT NULL
);

/* Коды для регистрации */

CREATE TABLE patient_examination (
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	doctor_id INT REFERENCES doctor (id) NOT NULL,
	examination_date DATE NOT NULL,
	breathlessness VARCHAR (1024),
	fatigue VARCHAR (1024),
	weakness VARCHAR (1024),
	vertigo VARCHAR (1024),
	edema VARCHAR (1024),
	hepatomegaly VARCHAR (1024),
	ascites VARCHAR (1024),
	pulsation VARCHAR (1024),
	chest_pain VARCHAR (1024),
	hemoptysis VARCHAR (1024),
	pulse_oximetry VARCHAR (1024),
	angina VARCHAR (1024),
	cough VARCHAR (1024),
	changes_in_fingers VARCHAR (1024),
	appetite VARCHAR (1024),
	blood_test_link VARCHAR (1024),
	electrocardiography_link VARCHAR (1024),
	radiography_link VARCHAR (1024),
	echocardiography_link VARCHAR (1024),
	chest_tomography_link VARCHAR (1024)
);

/* Коды для регистрации */

CREATE TABLE hospital_employee_sick_leave (
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	comment TEXT
);

/* Коды для регистрации */

CREATE TABLE hospital_employee_vacation (
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	comment TEXT
);

/* Коды для регистрации */

CREATE TABLE hospital_employee_dismissal (
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	comment TEXT
);

/* Коды для регистрации */

CREATE TABLE patient_recovery (
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) UNIQUE NOT NULL,
	author_id INT REFERENCES doctor (id) NOT NULL,
	comment TEXT
);

/* Коды для регистрации */

CREATE TABLE user_message (
	id SERIAL PRIMARY KEY,
	recipient_id INT REFERENCES user_information (id) NOT NULL,
	author_id INT REFERENCES user_information (id) NOT NULL,
	message_text TEXT NOT NULL,
	date TIMESTAMP NOT NULL
);
