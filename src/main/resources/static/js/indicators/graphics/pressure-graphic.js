const patientId = document.querySelector("div[data-patient]").dataset.patient;

document.addEventListener("DOMContentLoaded", () => {
    fetchInit();
});

function fetchInit() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/pressure`, {
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
            labels: pressureDate(pressuresBeforeExercise(pressures)),
            datasets: [
                {
                    label: "Верхнее давление до нагрузки (мм рт. ст.)",
                    data: pressureUpper(pressuresBeforeExercise(pressures)),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "Нижнее давление до нагрузки (мм рт. ст.)",
                    data: pressureLower(pressuresBeforeExercise(pressures)),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function afterExercisePressureTable(pressures) {
    console.log(pressures);
    console.log(pressures[0]);
    new Chart(document.getElementById("after"), {
        type: "line",
        data: {
            labels: pressureDate(pressuresAfterExercise(pressures)),
            datasets: [
                {
                    label: "Верхнее давление после нагрузки (мм рт. ст.)",
                    data: pressureUpper(pressuresAfterExercise(pressures)),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "Нижнее давление после нагрузки (мм рт. ст.)",
                    data: pressureLower(pressuresAfterExercise(pressures)),
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

function pressureUpper(pressures) {
    return pressures.map((pressure) => pressure.upper);
}

function pressureLower(pressures) {
    return pressures.map((pressure) => pressure.lower);
}

function pressureDate(pressures) {
    return pressures.map((pressure) => pressure.formattedDate);
}
