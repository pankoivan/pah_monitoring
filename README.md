# pah_monitoring
Дипломная работа: Web-приложение для мониторинга состояния пациентов с лёгочной артериальной гипертензией.

_______

**Общая информация**

Разработанное Web-приложение позволяет организовать систематизированный подход к процессу ведения пациентов с лёгочной артериальной гипертензией. Оно сосредотачивает внимание врачей и пациентов на симптомах и показателях здоровья, специфичных для этого заболевания, и обеспечивает их удобное для просмотра и анализа графическое представление.

Информацию, которую приложение получает из опросников (кашель, головокружение, общее самочувствие и другие), оно отображает в табличном виде. Числовые данные (артериальное давление, пульс, вес и другие) приложение визуализирует как в виде таблиц, так и в виде графиков.

На рисунке представлены основные функции, предоставляемые приложением пользователям в соответствии с их ролями.

![UseCase](https://github.com/user-attachments/assets/8feaf8f5-4437-4b47-80ee-e561c7205e19)

_______

**База данных**

Всего база данных содержит 33 таблицы, из которых 17 так или иначе связаны с ходом наблюдения за пациентами:

- 12 хранят информацию о показателях здоровья пациентов (давление, спирометрия, пульсоксиметрия и т.д.) и результатах пройденных ими тестов / опросников (Т6МХ, общее самочувствие, физические изменения и т.д.);
- 1 хранит информацию об анамнезах пациентов;
- 1 хранит информацию о файлах с результатами анализов, загруженных пациентами на сервер;
- 1 хранит расписания сбора показателей, назначенные лечащими врачами своим пациентам;
- 2 предназначены для хранения информации о наградах пациентов за прогресс в отправке показателей: одна таблица непосредственно со всеми доступными в приложении наградами, вторая - *"Many-to-many"*-таблица, связывающая пациентов и награды.

Для удобства просмотра схема базы данных разбита на две части.

Первая часть. Содержит пару таблиц из приведённого описания (*chest_pain* и *liquid*) и остальные таблицы: пользователи, отпуска, больничные, увольнения, коды безопасности регистрации в приложении и т.д.

![Первая часть](https://github.com/user-attachments/assets/927bd989-f75a-4048-9b7a-1a91fddcdb97)

Вторая часть. Содержит все описанные выше таблицы, кроме *chest_pain* и *liquid*. Оборванные на рисунке связи идут к первой части схемы базы данных.

![Вторая часть](https://github.com/user-attachments/assets/51e76912-1be7-4587-a80c-2bf739e9243a)

_______

**Архитектура**

Приложение сочетает в себе архитектуры MVC и REST. Далее подробно описаны области их применения в соответствии с действиями пользователя.

MVC - выдача страниц клиенту:

- при переходе по страницам;
- при редиректе на страницу.

REST - обмен данными с клиентским JavaScript-кодом:

- при отправке на сервер JSON с собранной из input-форм информацией для добавления / изменения сущностей;
- при приёме клиентом ответного JSON с информацией о добавленной / изменённой сущности;
- при запросе клиентом данных в виде JSON для их представления в табличном виде и в виде графиков.

Приведённый рисунок иллюстрирует архитектуру приложения.

![Архитектура](https://github.com/user-attachments/assets/1e26055f-9050-4378-8c7a-488ec0ff09c0)

_______

**Серверная часть**

Серверная часть приложения состоит из четырёх глобальных групп классов (слоёв), описанных ниже. Их также можно увидеть на рисунке архитектуры приложения.

1) Сущности. Представляют собой описание таблиц базы данных в виде классов для осуществления ORM.
2) Репозитории. Описывают операции над базой данных, прежде всего - CRUD-операции.
3) Сервисы. Содержат в себе практически всю основную логику приложения.
4) Контроллеры. Предназначены для обмена данными между клиентом и сервером.

На серверной стороне приложения активно используются DTO-сущности, предусмотренные как для приёма, так и для выдачи данных. Входные DTO содержат только те поля, которые необходимы серверу для добавления / изменения сущностей, а также именно во входных DTO расставлены аннотации для валидации данных библиотекой *Hibernate Validator*. Выходные DTO, в свою очередь, состоят из полей, достаточных для выдачи клиенту всей ожидаемой им информации.

Специально для тех сущностей, которые выдаются пользователям в виде списков (медицинские учреждения, администраторы, врачи и т.д.), имеются специальные классы-фильтры, решающие следующие задачи:

1) Поиск в соответствии с указанной пользователем подстрокой.
2) Фильтрация по указанным пользователем критериями.
3) Сортировка по указанныму пользователем признаку.
4) Пагинация в соответствии с конкретным номером страницы, запрошенным пользователем.

Есть две задачи, которые на сервере выполняются по расписанию:

- удаление просроченных кодов безопасности регистрации из базы данных;
- выдача пациентам наград за прохождение прогрессии в отправке показателей.

Также приложением предусмотрена отправка писем на электронные почты пользователей. Она происходит в следующих случаях:

- выдача кода безопасности регистрации (для администраторов, врачей и пациентов);
- назначение / смена лечащего врача (для пациентов);
- уведомление о получении награды (для пациентов).

_______

**Клиентская часть**

При разработке графического интерфейса использовались стандартные средства HTML / CSS, а также Web-фреймворк *Bootstrap*, определивший практически весь стиль приложения: цвета фона и текста страниц, общий вид разных элементов и отступы между ними, адаптивность вёрстки и т.д.

За все действия пользователя в приложении, направленные на добавление / изменение / удаление сущностей, на клиентской стороне отвечает JavaScript-код, отправляющий на соответствующие эндпоинты сервера JSON со всей необходимой информацией посредством простых fetch-запросов.

Другая, более интересная задача, решаемая клиентскими скриптами, - использование библиотек для графической визуализации данных о состоянии здоровья пациентов. Табличное отображение информации обеспечивает *Grid.js*, а для построения графиков задействована библиотека *Chart.js*.

Так выглядит таблица с данными о показателе "Пульсоксиметрия" пациента.

![Таблица](https://github.com/user-attachments/assets/33111d9c-7fd1-4628-b48f-87375f14545e)

А вот так выглядят эти же данные в виде графиков.

![Графики](https://github.com/user-attachments/assets/5dc1ffae-1fb0-4e86-bccb-8655b9da4037)

_______

**Дополнительные возможности**

В приложении реализован внутренний мессенджер на основе WebSocket. Благодаря нему пользователи (например, пациенты и их лечащие врачи) могут в реальном времени обмениваться между собой сообщениями. Результаты действий одного участника диалога над своими сообщениями (добавление / изменение / удаление) сразу же становятся видны второму соответственно.

Все исключения, возникающие в ходе работы пользователя с приложением, обрабатываются с использованием *ErrorController* и *@ControllerAdvice*. Предусмотрено разделение ошибок на:

- те, которые возникли при простом взятии пользователем страниц;
- те, которые возникли во время добавления / изменения / удаления пользователем сущностей.

В первом случае контроллер, реализующий интерфейс *ErrorController*, выдаёт пользователю страницу, заполненную текстом ошибки. Во втором случае контроллер, помеченный аннотацией *@ControllerAdvice*, возвращает клиенту JSON с информацией о возникшей ошибке.
