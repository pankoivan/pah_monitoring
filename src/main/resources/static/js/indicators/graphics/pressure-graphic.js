const patientId = document.querySelector("div[data-patient]").dataset.patient;

let pressures;

function fetchGet() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/pressure`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    pressures = responseJson;
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

document.addEventListener("DOMContentLoaded", () => {
    fetchGet();
    console.log(pressures);
});

new Chart(document.getElementById("upper"), {
    type: "line",
    data: {
        labels: ["Дата 1", "Дата 2", "Дата 3", "Дата 4", "Дата 5", "Дата 6", "Дата 7"],
        datasets: [
            {
                label: "Верхнее давление до нагрузки (мм рт. ст.)",
                data: [12, 19, 3, 5, 2, 3, 1],
                borderWidth: 1,
                tension: 0.1,
            },
            {
                label: "Верхнее давление после нагрузки (мм рт. ст.)",
                data: [15, 11, 4, 6, 12, 10, 2],
                borderWidth: 1,
                tension: 0.1,
            },
        ],
    },
});

new Chart(document.getElementById("lower"), {
    type: "line",
    data: {
        labels: ["Дата 1", "Дата 2", "Дата 3", "Дата 4", "Дата 5", "Дата 6", "Дата 7"],
        datasets: [
            {
                label: "Нижнее давление до нагрузки (мм рт. ст.)",
                data: [10, 10, 1, 9, 8, 4, 9],
                borderWidth: 1,
                tension: 0.1,
            },
            {
                label: "Нижнее давление до нагрузки (мм рт. ст.)",
                data: [12, 19, 10, 11, 12, 7, 11],
                borderWidth: 1,
                tension: 0.1,
            },
        ],
    },
});
