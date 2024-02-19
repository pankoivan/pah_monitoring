const patientId = document.querySelector("div[data-patient]").dataset.patient;

new gridjs.Grid({
    columns: ["Дата", "Верхнее", "Нижнее", "После нагрузки", "Больше1", "Больше2", "Больше3"],
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
            showing: "Показано",
            to: "до",
            of: "из",
            results: () => "записей",
        },
    },
    server: {
        url: `http://localhost:8080/rest/patients/${patientId}/examinations/tables/pressure`,
        then: (pressures) => pressures.map((pressure) => [pressure.formattedDate, pressure.upper, pressure.lower, pressure.afterExercise, "Byb", "Pyp", "Bob"]),
    },
}).render(document.getElementById("table"));
