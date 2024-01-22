CREATE TABLE IF NOT EXISTS main_admin_contact
(
	id SERIAL PRIMARY KEY,
	contact VARCHAR (80) UNIQUE NOT NULL,
	description VARCHAR (48) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS hospital
(
	id SERIAL PRIMARY KEY,
	oid VARCHAR (256) UNIQUE NOT NULL,
	name VARCHAR (512) UNIQUE NOT NULL,
	current_state VARCHAR (24) NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS hospital_registration_request
(
	id SERIAL PRIMARY KEY,
	hospital_id INT REFERENCES hospital (id) UNIQUE NOT NULL,
	name VARCHAR (32) NOT NULL,
	lastname VARCHAR (64) NOT NULL,
	patronymic VARCHAR (32) NOT NULL,
	post VARCHAR (128) NOT NULL,
	passport VARCHAR (12) UNIQUE NOT NULL,
	phone_number VARCHAR (24) UNIQUE NOT NULL,
	email VARCHAR (256) UNIQUE NOT NULL,
	comment TEXT NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS registration_security_code
(
	id SERIAL PRIMARY KEY,
	hospital_id INT REFERENCES hospital (id) NOT NULL,
	role VARCHAR (24) NOT NULL,
	email VARCHAR (256) UNIQUE NOT NULL,
	code UUID UNIQUE NOT NULL,
	expiration_date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS user_security_information
(
	id SERIAL PRIMARY KEY,
	email VARCHAR (256) UNIQUE NOT NULL,
	password VARCHAR (64) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_information
(
	id SERIAL PRIMARY KEY,
	name VARCHAR (32) NOT NULL,
	lastname VARCHAR (64) NOT NULL,
	patronymic VARCHAR (32) NOT NULL,
	phone_number VARCHAR (24) NOT NULL,
	gender VARCHAR (8),
	birthdate DATE
);

CREATE TABLE IF NOT EXISTS hospital_employee_information
(
	id SERIAL PRIMARY KEY,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL,
	post VARCHAR (128) NOT NULL
);

CREATE TABLE IF NOT EXISTS main_administrator
(
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS administrator
(
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL,
	hospital_id INT REFERENCES hospital (id) NOT NULL
);

CREATE TABLE IF NOT EXISTS doctor
(
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL,
	hospital_id INT REFERENCES hospital (id) NOT NULL
);

CREATE TABLE IF NOT EXISTS sick_leave
(
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	comment TEXT NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS vacation
(
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	comment TEXT NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS dismissal
(
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	comment TEXT NOT NULL,
	date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS patient
(
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL,
	hospital_id INT REFERENCES hospital (id) NOT NULL,
	doctor_id INT REFERENCES doctor (id)
);

CREATE TABLE IF NOT EXISTS patient_inactivity
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) UNIQUE NOT NULL,
	author_id INT REFERENCES doctor (id) NOT NULL,
	comment TEXT NOT NULL,
	date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS spirometry
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	vcl REAL NOT NULL,
	avlc REAL NOT NULL,
	rlv REAL NOT NULL,
	vfe1 REAL NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS pulse_oximetry
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	oxygen_percentage REAL NOT NULL,
	pulse_rate INT NOT NULL,
	during_exercise BOOL NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS pressure
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	upper INT NOT NULL,
	lower INT NOT NULL,
	during_exercise BOOL NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS walk_test
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	oxygen_support BOOL NOT NULL,
	auxiliary_devices BOOL NOT NULL,
	distance REAL NOT NULL,
	number_of_stops INT NOT NULL,
	breathlessness VARCHAR (12) NOT NULL,
	pulse_oximetry_id_before INT REFERENCES pulse_oximetry (id) UNIQUE NOT NULL,
	pulse_oximetry_id_after INT REFERENCES pulse_oximetry (id) UNIQUE NOT NULL,
	pressure_id_before INT REFERENCES pressure (id) UNIQUE NOT NULL,
	pressure_id_after INT REFERENCES pressure (id) UNIQUE NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS cough
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	type VARCHAR (4) NOT NULL,
	power VARCHAR (16) NOT NULL,
	timbre VARCHAR (12) NOT NULL,
	hemoptysis BOOL NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS chest_pain
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	type VARCHAR (12) NOT NULL,
	duration VARCHAR (16) NOT NULL,
	nitroglycerin VARCHAR (16) NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS fainting
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	duration VARCHAR (16) NOT NULL,
	during_exercise BOOL NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS physical_changes
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	acrocyanosis BOOL NOT NULL,
	fingers_phalanges BOOL NOT NULL,
	nails BOOL NOT NULL,
	chest BOOL NOT NULL,
	neck_veins BOOL NOT NULL,
	lower_extremities BOOL NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS ascites
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	liquid_amount VARCHAR (12) NOT NULL,
	content_infection VARCHAR (12) NOT NULL,
	response_to_drug_therapy VARCHAR (24) NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS overall_health
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	fatigue BOOL NOT NULL,
	rest_feeling BOOL NOT NULL,
	drowsiness BOOL NOT NULL,
	concentration BOOL NOT NULL,
	weakness VARCHAR (12) NOT NULL,
	appetite BOOL NOT NULL,
	cold_extremities VARCHAR (12) NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS vertigo
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	duration VARCHAR (12) NOT NULL,
	nausea BOOL NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS liquid_and_weight
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	liquid REAL NOT NULL,
	weight REAL NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS functional_class
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	functional_class VARCHAR (4) NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS analysis_file
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	filename VARCHAR (256) UNIQUE NOT NULL,
	analysis_type VARCHAR (24) NOT NULL,
	date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS examination_schedule
(
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES patient (id) NOT NULL,
    indicators_group VARCHAR (32) NOT NULL,
    schedule VARCHAR (24) NOT NULL,
	
	CONSTRAINT examination_schedule__patient_and_indicators_group_unique UNIQUE (patient_id, indicators_group)
);

CREATE TABLE IF NOT EXISTS medicine
(
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES patient (id) NOT NULL,
    medicine_api_id VARCHAR (1024) NOT NULL,
    description TEXT NOT NULL,
	
	CONSTRAINT patient_medicine__patient_and_medicine_unique UNIQUE (patient_id, medicine_api_id)
);

CREATE TABLE IF NOT EXISTS achievement
(
    id SERIAL PRIMARY KEY,
    filename VARCHAR (256) UNIQUE NOT NULL,
    name VARCHAR (64) UNIQUE NOT NULL,
    description TEXT UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS patient_achievement
(
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES patient (id) NOT NULL,
    achievement_id INT REFERENCES achievement (id) NOT NULL,
	
	CONSTRAINT patient_achievement__many_to_many_unique UNIQUE (patient_id, achievement_id)
);

CREATE TABLE IF NOT EXISTS user_message
(
	id SERIAL PRIMARY KEY,
	recipient_id INT REFERENCES user_information (id) NOT NULL,
	author_id INT REFERENCES user_information (id) NOT NULL,
	message_text TEXT NOT NULL,
	date TIMESTAMP NOT NULL,
	editing_date TIMESTAMP,

	CONSTRAINT user_message__recipient_and_author_are_not_equal CHECK (recipient_id != author_id)
);
