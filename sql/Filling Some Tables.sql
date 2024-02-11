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

INSERT INTO achievement
(name, description, filename)
VALUES
('Историк', 'Вручается за отправку анамнеза', '1.png')
('Вдох-выдох', 'Вручается за первую отправку показателя "Спирометрия"', '2.png'),
('Прогулка', 'Вручается за первую отправку показателя "Т6МХ"', '3.png'),
('Без сознания', 'Вручается за первую отправку показателя "Обморок"', '4.png'),
('Трансформация', 'Вручается за первую отправку показателя "Физические изменения"', '5.png'),
('Спортсмен', 'Вручается за первую отправку показателя "Общее самочувствие"', '6.png'),
('Водный баланс', 'Вручается за первую отправку показателя "Питьевой режим"', '7.png'),
('Диета', 'Вручается за первую отправку показателя "Вес"', '8.png'),
('Отчаянный', 'Вручается за первую отправку показателя "Катетеризация правых отделов сердца"', '9.png'),
('Начинающий', 'Вручается за 5 отправок любых показателей', '10.png'),
('Проверенный', 'Вручается за 10 отправок любых показателей', '11.png'),
('Старательный', 'Вручается за 20 отправок любых показателей', '12.png'),
('Упорный', 'Вручается за 50 отправок любых показателей', '13.png'),
('Целеустремлённый', 'Вручается за 100 отправок любых показателей', '14.png'),
('Непоколебимый', 'Вручается за 250 отправок любых показателей', '15.png'),
('Чемпион', 'Вручается за получение всех остальных наград', '16.png');
