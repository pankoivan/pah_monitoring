const patientId = document.querySelector("div[data-patient]").dataset.patient;

const period = new URLSearchParams(window.location.search).get("period");

moment.locale("ru");

new gridjs.Grid({
    columns: [
        {
            name: "Дата",
            sort: {
                compare: (firstDate, secondDate) => {
                    const firstMoment = moment(firstDate, "DD MMM YYYY, HH:mm");
                    const secondMoment = moment(secondDate, "DD MMM YYYY, HH:mm");
                    if (firstMoment.isBefore(secondMoment)) {
                        return -1;
                    } else if (firstMoment.isAfter(secondMoment)) {
                        return 1;
                    } else {
                        return 0;
                    }
                },
            },
        },
        ,
        "Количество жидкости в день (л)",
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
        url: `http://localhost:8080/rest/patients/${patientId}/examinations/tables/liquid?period=${period}`,
        then: (liquids) => liquids.map((liquid) => [liquid.formattedDate, liquid.liquid]),
    },
}).render(document.getElementById("table"));
