const patientId = document.querySelector("div[data-patient]").dataset.patient;

const period = new URLSearchParams(window.location.search).get("period");

document.addEventListener("DOMContentLoaded", () => {
    fetchInit();
});

function fetchInit() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/pressure?period=${period}`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((pressures) => {
                    beforeExercisePressureTable(pressures);
                    afterExercisePressureTable(pressures);
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function beforeExercisePressureTable(pressures) {
    new Chart(document.getElementById("before"), {
        type: "line",
        data: {
            labels: spirometryDate(pressuresBeforeExercise(pressures)),
            datasets: [
                {
                    label: "Верхнее давление до нагрузки (мм рт. ст.)",
                    data: spirometryVlc(pressuresBeforeExercise(pressures)),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "Нижнее давление до нагрузки (мм рт. ст.)",
                    data: spirometryRlc(pressuresBeforeExercise(pressures)),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function afterExercisePressureTable(pressures) {
    new Chart(document.getElementById("after"), {
        type: "line",
        data: {
            labels: spirometryDate(pressuresAfterExercise(pressures)),
            datasets: [
                {
                    label: "Верхнее давление после нагрузки (мм рт. ст.)",
                    data: spirometryVlc(pressuresAfterExercise(pressures)),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "Нижнее давление после нагрузки (мм рт. ст.)",
                    data: spirometryRlc(pressuresAfterExercise(pressures)),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function pressuresBeforeExercise(pressures) {
    return pressures.filter((pressure) => pressure.afterExercise == false);
}

function pressuresAfterExercise(pressures) {
    return pressures.filter((pressure) => pressure.afterExercise == true);
}

function spirometryVlc(pressures) {
    return pressures.map((pressure) => pressure.upper);
}

function spirometryRlc(pressures) {
    return pressures.map((pressure) => pressure.lower);
}

function spirometryDate(pressures) {
    return pressures.map((pressure) => pressure.formattedDate);
}
