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
                    pressureGraphicBeforeExercise(pressures);
                    pressureGraphicAfterExercise(pressures);
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function pressureGraphicBeforeExercise(pressures) {
    new Chart(document.getElementById("before"), {
        type: "line",
        data: {
            labels: date(beforeExercise(pressures)),
            datasets: [
                {
                    label: "Верхнее давление (мм рт. ст.)",
                    data: upper(beforeExercise(pressures)),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "Нижнее давление (мм рт. ст.)",
                    data: lower(beforeExercise(pressures)),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function pressureGraphicAfterExercise(pressures) {
    new Chart(document.getElementById("after"), {
        type: "line",
        data: {
            labels: date(afterExercise(pressures)),
            datasets: [
                {
                    label: "Верхнее давление (мм рт. ст.)",
                    data: upper(afterExercise(pressures)),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(75, 192, 192, 0.2)",
                    borderColor: "rgba(75, 192, 192, 1)",
                },
                {
                    label: "Нижнее давление (мм рт. ст.)",
                    data: lower(afterExercise(pressures)),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(153, 102, 255, 0.2)",
                    borderColor: "rgba(153, 102, 255, 1)",
                },
            ],
        },
    });
}

function beforeExercise(pressures) {
    return pressures.filter((pressure) => pressure.afterExercise == false);
}

function afterExercise(pressures) {
    return pressures.filter((pressure) => pressure.afterExercise == true);
}

function upper(pressures) {
    return pressures.map((pressure) => pressure.upper);
}

function lower(pressures) {
    return pressures.map((pressure) => pressure.lower);
}

function date(pressures) {
    return pressures.map((pressure) => pressure.formattedDate);
}
