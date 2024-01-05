/* Контакты главного администратора (1) */

CREATE TABLE IF NOT EXISTS main_admin_contact
(
	id SERIAL PRIMARY KEY,
	contact VARCHAR (256) UNIQUE NOT NULL,
	description VARCHAR (128) UNIQUE NOT NULL
);

/* Медицинские учреждения (2) */

CREATE TABLE IF NOT EXISTS hospital
(
	id SERIAL PRIMARY KEY,
	oid VARCHAR (256) UNIQUE NOT NULL,
	name VARCHAR (512) UNIQUE NOT NULL,
	date TIMESTAMP NOT NULL,
	current_state VARCHAR (24) NOT NULL
);

/* Коды безопасности для регистрации пользователей (3) */

CREATE TABLE IF NOT EXISTS registration_security_code
(
	id SERIAL PRIMARY KEY,
	hospital_id INT REFERENCES hospital (id) NOT NULL,
	role VARCHAR (24) NOT NULL,
	code UUID UNIQUE NOT NULL,
	expiration_date TIMESTAMP NOT NULL
);

/* Коды безопасности для доступа к странице регистрации медицинского учреждения (4) */

CREATE TABLE IF NOT EXISTS page_access_security_code
(
	id SERIAL PRIMARY KEY,
	code UUID UNIQUE NOT NULL,
	expiration_date TIMESTAMP NOT NULL
);

/* Почты и пароли всех пользователей (5) */

CREATE TABLE IF NOT EXISTS user_security_information
(
	id SERIAL PRIMARY KEY,
	email VARCHAR (256) UNIQUE NOT NULL,
	password VARCHAR (64) NOT NULL
);

/* Общая информация обо всех пользователях (6) */

CREATE TABLE IF NOT EXISTS user_information
(
	id SERIAL PRIMARY KEY,
	name VARCHAR (32) NOT NULL,
	lastname VARCHAR (64) NOT NULL,
	patronymic VARCHAR (32) NOT NULL,
	gender VARCHAR (8),
	birthdate DATE,
	phone_number VARCHAR (24) NOT NULL
);

/* Информация о сотрудниках медицинского учреждения (7) */

CREATE TABLE IF NOT EXISTS hospital_employee_information
(
	id SERIAL PRIMARY KEY,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL,
	hospital_id INT REFERENCES hospital (id) NOT NULL,
	post VARCHAR (128) NOT NULL,
	experience INT
);

/* Главный администратор (8) */

CREATE TABLE IF NOT EXISTS main_administrator
(
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL
);

/* Администраторы (9) */

CREATE TABLE IF NOT EXISTS administrator
(
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL
);

/* Врачи (10) */

CREATE TABLE IF NOT EXISTS doctor
(
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL,
	university VARCHAR (256)
);

/* Больничные сотрудников медицинских учреждений (11) */

CREATE TABLE IF NOT EXISTS sick_leave
(
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	comment TEXT,
	
	CONSTRAINT sick_leave__hospital_employee_and_author_are_not_equal CHECK (hospital_employee_information_id != author_id)
);

/* Отпуски сотрудников медицинских учреждений (12) */

CREATE TABLE IF NOT EXISTS vacation
(
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	comment TEXT,
	
	CONSTRAINT vacation__hospital_employee_and_author_are_not_equal CHECK (hospital_employee_information_id != author_id)
);

/* Увольнения сотрудников медицинских учреждений (13) */

CREATE TABLE IF NOT EXISTS dismissal
(
	id SERIAL PRIMARY KEY,
	hospital_employee_information_id INT REFERENCES hospital_employee_information (id) UNIQUE NOT NULL,
	author_id INT REFERENCES administrator (id) NOT NULL,
	date DATE NOT NULL,
	comment TEXT,
	
	CONSTRAINT dismissal__hospital_employee_and_author_are_not_equal CHECK (hospital_employee_information_id != author_id)
);

/* Пациенты (14) */

CREATE TABLE IF NOT EXISTS patient
(
	id SERIAL PRIMARY KEY,
	user_security_information_id INT REFERENCES user_security_information (id) UNIQUE NOT NULL,
	user_information_id INT REFERENCES user_information (id) UNIQUE NOT NULL,
	doctor_id INT REFERENCES doctor (id),
	hospital_id INT REFERENCES hospital (id) NOT NULL
);

/* Неактивные пациенты (15) */

CREATE TABLE IF NOT EXISTS inactive_patient
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) UNIQUE NOT NULL,
	author_id INT REFERENCES doctor (id) NOT NULL,
	date DATE NOT NULL,
	comment TEXT
);

/* Наблюдения (16) */

CREATE TABLE IF NOT EXISTS examination
(
	id SERIAL PRIMARY KEY,
	patient_id INT REFERENCES patient (id) NOT NULL,
	doctor_id INT REFERENCES doctor (id) NOT NULL,
	date TIMESTAMP NOT NULL
);

/* Группа показателей: "Спирометрия + DLCO" (17) */

CREATE TABLE IF NOT EXISTS spirometry_and_DLCO
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	VLC REAL NOT NULL,
	AVLC REAL NOT NULL,
	RLV REAL NOT NULL,
	VFE1 REAL NOT NULL,
	DLCO REAL NOT NULL
);

/* Группа показателей: "Пульсоксиметрия" (18) */

CREATE TABLE IF NOT EXISTS pulse_oximetry
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) NOT NULL,
	oxygen_percentage REAL NOT NULL,
	pulse_rate INT NOT NULL,
	during_exercise BOOL NOT NULL
);

/* Группа показателей: "Давление" (19) */

CREATE TABLE IF NOT EXISTS pressure
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) NOT NULL,
	upper INT NOT NULL,
	lower INT NOT NULL,
	during_exercise BOOL NOT NULL
);

/* Группа показателей: "Т6МХ" (20) */

CREATE TABLE IF NOT EXISTS walk_test
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	oxygen_support BOOL NOT NULL,
	auxiliary_devices BOOL NOT NULL,
	distance REAL NOT NULL,
	number_of_stops INT NOT NULL,
	pulse_oximetry_id_before INT REFERENCES pulse_oximetry (id) UNIQUE NOT NULL,
	pulse_oximetry_id_after INT REFERENCES pulse_oximetry (id) UNIQUE NOT NULL,
	pressure_id_before INT REFERENCES pressure (id) UNIQUE NOT NULL,
	pressure_id_after INT REFERENCES pressure (id) UNIQUE NOT NULL,
	breathlessness VARCHAR (4) NOT NULL
);

/* Группа показателей: "Кашель" (21) */

CREATE TABLE IF NOT EXISTS cough
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	type VARCHAR (24) NOT NULL,
	power VARCHAR (24) NOT NULL,
	timbre VARCHAR (32) NOT NULL,
	hemoptysis BOOL NOT NULL
);

/* Группа показателей: "Боль в груди" (22) */

CREATE TABLE IF NOT EXISTS chest_pain
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	type VARCHAR (16) NOT NULL,
	duration VARCHAR (16) NOT NULL,
	nitroglycerin VARCHAR (16) NOT NULL
);

/* Группа показателей: "Обморок" (23) */

CREATE TABLE IF NOT EXISTS fainting
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	duration VARCHAR (16) NOT NULL,
	during_exercise BOOL NOT NULL
);

/* Группа показателей: "Физические изменения" (24) */

CREATE TABLE IF NOT EXISTS physical_changes
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	acrocyanosis BOOL NOT NULL,
	fingers_phalanges BOOL NOT NULL,
	nails BOOL NOT NULL,
	chest BOOL NOT NULL,
	neck_veins BOOL NOT NULL,
	lower_extremities BOOL NOT NULL
);

/* Группа показателей: "Асцит" (25) */

CREATE TABLE IF NOT EXISTS ascites
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	liquid_amount VARCHAR (16) NOT NULL,
	content_infection VARCHAR (16) NOT NULL,
	response_to_drug_therapy VARCHAR (24) NOT NULL
);

/* Группа показателей: "Общее самочувствие" (26) */

CREATE TABLE IF NOT EXISTS overall_health
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	fatigue BOOL NOT NULL,
	rest_feeling BOOL NOT NULL,
	drowsiness BOOL NOT NULL,
	concentration BOOL NOT NULL,
	weakness VARCHAR (24) NOT NULL,
	appetite BOOL NOT NULL,
	cold_extremities VARCHAR (24) NOT NULL
);

/* Группа показателей: "Головокружение" (27) */

CREATE TABLE IF NOT EXISTS vertigo
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	duration VARCHAR (16) NOT NULL,
	nausea BOOL NOT NULL
);

/* Группа показателей: "Жидкость и вес" (28) */

CREATE TABLE IF NOT EXISTS liquid_and_weight
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	liquid REAL NOT NULL,
	weight REAL NOT NULL
);

/* Группа показателей: "Функциональный класс" (29) */

CREATE TABLE IF NOT EXISTS functional_class
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	functional_class VARCHAR (1) NOT NULL
);

/* Группа показателей: "Развёрнутый анализ крови" (30) */

CREATE TABLE IF NOT EXISTS detailed_blood_test
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	filename VARCHAR (256) UNIQUE NOT NULL
);

/* Группа показателей: "Электрокардиограия" (31) */

CREATE TABLE IF NOT EXISTS electrocardiography
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	filename VARCHAR (256) UNIQUE NOT NULL
);

/* Группа показателей: "Рентгенография органов грудной клетки" (32) */

CREATE TABLE IF NOT EXISTS radiography
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	filename VARCHAR (256) UNIQUE NOT NULL
);

/* Группа показателей: "Эхокардиография" (33) */

CREATE TABLE IF NOT EXISTS echocardiography
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	filename VARCHAR (256) UNIQUE NOT NULL
);

/* Группа показателей: "Компьютерная томография органов грудной клетки" (34) */

CREATE TABLE IF NOT EXISTS computed_tomography
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	filename VARCHAR (256) UNIQUE NOT NULL
);

/* Группа показателей: "Катетеризация правых отделов сердца" (35) */

CREATE TABLE IF NOT EXISTS right_heart_catheterization
(
	id SERIAL PRIMARY KEY,
	examination_id INT REFERENCES examination (id) UNIQUE NOT NULL,
	filename VARCHAR (256) UNIQUE NOT NULL
);

/* Расписания отправки показателей (36) */

CREATE TABLE IF NOT EXISTS examination_schedule
(
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES patient (id) NOT NULL,
    indicators_group VARCHAR (64) NOT NULL,
    times VARCHAR (8) NOT NULL,
	period VARCHAR (24) NOT NULL
);

/* Лекарства, назначенные пациентам (37) */

CREATE TABLE IF NOT EXISTS medicine
(
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES patient (id) NOT NULL,
    medicine_api_id VARCHAR (1024) NOT NULL,
    description TEXT NOT NULL,
	
	CONSTRAINT patient_medicine__many_to_many_unique UNIQUE (patient_id, medicine_api_id)
);

/* Награды для пациентов (38) */

CREATE TABLE IF NOT EXISTS achievement
(
    id SERIAL PRIMARY KEY,
    filename VARCHAR (256) UNIQUE NOT NULL,
    name VARCHAR (64) UNIQUE NOT NULL,
    description TEXT UNIQUE NOT NULL
);

/* Связи пациентов и наград (39) */

CREATE TABLE IF NOT EXISTS patient_achievement
(
    id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES patient (id) NOT NULL,
    achievement_id INT REFERENCES achievement (id) NOT NULL,
	
	CONSTRAINT patient_achievement__many_to_many_unique UNIQUE (patient_id, achievement_id)
);

/* Сообщения всех пользователей (40) */

CREATE TABLE IF NOT EXISTS user_message
(
	id SERIAL PRIMARY KEY,
	recipient_id INT REFERENCES user_information (id) NOT NULL,
	author_id INT REFERENCES user_information (id) NOT NULL,
	message_text TEXT NOT NULL,
	date TIMESTAMP NOT NULL,
	
	CONSTRAINT user_message__many_to_many_unique UNIQUE (recipient_id, author_id),
	CONSTRAINT user_message__recipient_and_author_are_not_equal CHECK (recipient_id != author_id)
);
