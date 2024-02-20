const patientId = document.querySelector("div[data-patient]").dataset.patient;

new gridjs.Grid({
    columns: ["Дата", "Верхнее", "Нижнее", "После нагрузки"],
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
        pagination: {
            previous: "‹",
            next: "›",
            navigate: (page, pages) => `Страница ${page} из ${pages}`,
            page: (page) => `Страница ${page}`,
            to: "do",
            of: "из",
            results: "записей",
        },
        loading: "Загрузка...",
        noRecordsFound: "Записей не найдено",
    },
    server: {
        url: `http://localhost:8080/rest/patients/${patientId}/examinations/tables/pressure`,
        then: (pressures) => pressures.map((pressure) => [pressure.formattedDate, pressure.upper, pressure.lower, pressure.afterExercise]),
    },
}).render(document.getElementById("table"));
