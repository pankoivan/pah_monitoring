/* Главный администратор */

INSERT INTO user_security_information
(email, password)
VALUES
('example@yandex.ru', '$2a$10$251QtAG7JnugQAuKYuSgP.YzChzMtX4QG8Vg6Y2gH.U99GKsax4si');

INSERT INTO user_information
(name, lastname, patronymic, gender, birthdate, phone_number)
VALUES
('Иван', 'Иванов', 'Иванович', 'MALE', '21-10-1999', '8 944 123 85 64');

INSERT INTO main_administrator
(user_security_information_id, user_information_id)
VALUES
(1, 1);

INSERT INTO main_page_contact
(contact, description)
VALUES
('https://ru.stackoverflow.com/', 'StackOverflow'),
('https://www.skype.com/ru/', 'Skype'),
('example@yandex.ru', 'Почта');