const patientId = document.querySelector("div[data-patient]").dataset.patient;

new gridjs.Grid({
    columns: [
        "Дата",
        "Дистанция (м)",
        "Количество остановок",
        "Одышка по шкале Борга",
        "Верхнее давление (мм рт.ст.)",
        "Нижнее давлениее (мм рт.ст.)",
        "Пульс (уд/мин)",
        "Содержание кислорода в крови (%)",
        "Кислородная поддержка",
        "Вспомогательные устройства",
    ],
    resizable: true,
    search: true,
    sort: true,
    pagination: {
        limit: 10,
    },
    language: {
        search: {
            placeholder: "Найти...",
        },
        sort: {
            sortAsc: "Сортировать в порядке возрастания",
            sortDesc: "Сортировать в порядке убывания",
        },
        pagination: {
            previous: "‹",
            next: "›",
            navigate: (page, pages) => `Страница ${page} из ${pages}`,
            page: (page) => `Страница ${page}`,
            showing: "Показано c",
            to: "по",
            of: "из",
            results: "записей",
        },
        loading: "Загрузка...",
        noRecordsFound: "Записи не найдены",
        error: "Произошла ошибка при получении данных с сервера",
    },
    server: {
        url: `http://localhost:8080/rest/patients/${patientId}/examinations/tables/walk-test`,
        then: (walkTests) =>
            walkTests.map((walkTest) => [
                walkTest.formattedDate,
                walkTest.distance,
                walkTest.numberOfStops,
                walkTest.breathlessness,
                `${walkTest.pressureBefore.upper} → ${walkTest.pressureAfter.upper}`,
                `${walkTest.pressureBefore.lower} → ${walkTest.pressureAfter.lower}`,
                `${walkTest.pulseOximetryBefore.pulseRate} → ${walkTest.pulseOximetryAfter.pulseRate}`,
                `${walkTest.pulseOximetryBefore.oxygenPercentage} → ${walkTest.pulseOximetryAfter.oxygenPercentage}`,
                walkTest.oxygenSupport,
                walkTest.auxiliaryDevices,
            ]),
    },
}).render(document.getElementById("table"));
