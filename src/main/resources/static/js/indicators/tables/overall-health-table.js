const patientId = document.querySelector("div[data-patient]").dataset.patient;

const period = new URLSearchParams(window.location.search).get("period");

new gridjs.Grid({
    columns: ["Дата", "Ссылка на анкету"],
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
        url: `http://localhost:8080/rest/patients/${patientId}/examinations/tables/overall-health?period=${period}`,
        then: (overallHealths) => overallHealths.map((overallHealth) => [overallHealth.formattedDate, gridjs.html(`<a href="/patients/${patientId}/examinations/plain/overall-health/${overallHealth.id}" class="text-secondary">Анкета</a>`)]),
    },
}).render(document.getElementById("table"));
