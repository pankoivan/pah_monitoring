/* Контакты главного администратора */

CREATE TABLE main_admin_contact (
	id SERIAL PRIMARY KEY,
	contact VARCHAR (256) UNIQUE NOT NULL,
	description VARCHAR (128) UNIQUE NOT NULL
);

/* Зарегистрированные медицинские учреждения */

CREATE TABLE registered_hospital (
	id SERIAL PRIMARY KEY,
	oid VARCHAR (512) UNIQUE NOT NULL,
	name VARCHAR (1024) UNIQUE NOT NULL,
	date TIMESTAMP NOT NULL
);

/* Коды безопасности для регистрации администраторов, врачей и пациентов */

CREATE TABLE registration_security_code (
	id SERIAL PRIMARY KEY,
	registered_hospital_id INT REFERENCES registered_hospital (id) NOT NULL,
	role VARCHAR (32) NOT NULL,
	code UUID UNIQUE NOT NULL,
	expiration_date DATE NOT NULL
);

/* Коды безопасности для доступа к странице (форме) регистрации медицинского учреждения */

CREATE TABLE page_access_security_code (
	id SERIAL PRIMARY KEY,
	code UUID UNIQUE NOT NULL,
	expiration_date DATE NOT NULL
);

/* Почты и пароли всех пользователей */

CREATE TABLE user_security_information (
	id SERIAL PRIMARY KEY,
	email VARCHAR (256) UNIQUE NOT NULL,
	password VARCHAR (256) NOT NULL
);

/* Общая информация обо всех пользователях */

CREATE TABLE user_information (
	id SERIAL PRIMARY KEY,
	name VARCHAR (32) NOT NULL,
	lastname VARCHAR (64) NOT NULL,
	patronymic VARCHAR (32) NOT NULL,
	gender VARCHAR (8),
	birthdate DATE,
	phone_number VARCHAR (24)
);

/* Информация о сотрудниках медицинского учреждения */

CREATE TABLE hospital_employee_information (
	id SERIAL PRIMARY KEY,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL,
	registered_hospital_id INT REFERENCES registered_hospital (id) NOT NULL,
	post VARCHAR (128) NOT NULL,
	experience INT
);

/* Главный администратор */

CREATE TABLE main_administrator (
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL
);

/* Администраторы */

CREATE TABLE administrator (
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL
);

/* Врачи */

CREATE TABLE doctor (
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL,
	university VARCHAR (256)
);

/* Пациенты */

CREATE TABLE patient (
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL,
	doctor_id INT REFERENCES doctor (id),
	registered_hospital_id INT REFERENCES registered_hospital (id) NOT NULL,
);

/* Наблюдения */

CREATE TABLE examination (
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	doctor_id INT REFERENCES doctor (id) NOT NULL,
	date TIMESTAMP NOT NULL
);

/* Показатели */

CREATE TABLE indicator (
    id SERIAL PRIMARY KEY,
    name VARCHAR (128) UNIQUE NOT NULL,
    group VARCHAR (32) NOT NULL
);

/* Конкретные показатели в конкретном наблюдении */

CREATE TABLE examination_indicator (
    id SERIAL PRIMARY KEY,
    examination_id INT REFERENCES examination (id) NOT NULL,
    indicator_id INT REFERENCES indicator (id) NOT NULL,
    value VARCHAR (256) NOT NULL
);

/* Расписания сбора показателей */

CREATE TABLE indicator_schedule (
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES patient (id) NOT NULL,
    indicator_id INT REFERENCES indicator (id) NOT NULL,
    schedule VARCHAR (64) NOT NULL
);

/* Лекарства, назначенные пациентам */

CREATE TABLE medicine (
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES patient (id) NOT NULL,
    medicine_api_id VARCHAR (1024) NOT NULL,
    description TEXT NOT NULL
);

/* Награды пациентов */

CREATE TABLE achievement (
    id SERIAL PRIMARY KEY,
    filename VARCHAR (256) UNIQUE NOT NULL,
    name VARCHAR (64) UNIQUE NOT NULL,
    description TEXT UNIQUE NOT NULL
);

/* Связи пациентов и наград */

CREATE TABLE patient_achievement (
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES patient (id) NOT NULL,
    achievement_id INT REFERENCES achievement (id) NOT NULL
);

/* Больничные сотрудников медицинских учреждений */

CREATE TABLE sick_leave (
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	comment TEXT
);

/* Отпуски сотрудников медицинских учреждений */

CREATE TABLE vacation (
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	comment TEXT
);

/* Увольнения сотрудников медицинских учреждений */

CREATE TABLE dismissal (
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	date TIME NOT NULL,
	comment TEXT
);

/* Выписки пациентов */

CREATE TABLE recovery (
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) UNIQUE NOT NULL,
	author_id INT REFERENCES doctor (id) NOT NULL,
	date TIME NOT NULL,
	comment TEXT
);

/* Сообщения всех пользователей */

CREATE TABLE user_message (
	id SERIAL PRIMARY KEY,
	recipient_id INT REFERENCES user_information (id) NOT NULL,
	author_id INT REFERENCES user_information (id) NOT NULL,
	message_text TEXT NOT NULL,
	date TIMESTAMP NOT NULL
);
