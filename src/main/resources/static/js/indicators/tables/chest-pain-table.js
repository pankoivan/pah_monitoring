const patientId = document.querySelector("div[data-patient]").dataset.patient;

new gridjs.Grid({
    columns: ["Дата", "Тип", "Продолжительность", "Нитроглицерин"],
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
        url: `http://localhost:8080/rest/patients/${patientId}/examinations/tables/chest-pain`,
        then: (chestPains) => chestPains.map((chestPain) => [chestPain.formattedDate, chestPain.type, chestPain.duration, chestPain.nitroglycerin]),
    },
}).render(document.getElementById("table"));
