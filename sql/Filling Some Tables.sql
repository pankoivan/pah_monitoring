INSERT INTO user_security_information
(email, password)
VALUES
('mainAdmin@mailmail', '$2a$10$251QtAG7JnugQAuKYuSgP.YzChzMtX4QG8Vg6Y2gH.U99GKsax4si');

INSERT INTO user_information
(lastname, name, patronymic, phone_number, gender, birthdate)
VALUES
('Ленин', 'Владимир', 'Ильич', '+7 (941) 139-85-64', 'MALE', '19-11-1984');

INSERT INTO main_administrator
(user_security_information_id, user_information_id)
VALUES
(1, 1);
