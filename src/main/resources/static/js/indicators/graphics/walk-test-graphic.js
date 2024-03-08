const patientId = document.querySelector("div[data-patient]").dataset.patient;

const period = new URLSearchParams(window.location.search).get("period");

document.addEventListener("DOMContentLoaded", () => {
    fetchInit();
});

function fetchInit() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/walk-test?period=${period}`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((walkTests) => {
                    distanceGraphic(walkTests);
                    breathlessnessGraphic(walkTests);
                    pressureBeforeExerciseGraphic(walkTests);
                    pulseRateBeforeExerciseGraphic(walkTests);
                    oxygenPercentageBeforeExerciseGraphic(walkTests);
                    pressureAfterExerciseGraphic(walkTests);
                    pulseRateAfterExerciseGraphic(walkTests);
                    oxygenPercentageAfterExerciseGraphic(walkTests);
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function distanceGraphic(walkTests) {
    new Chart(document.getElementById("distance"), {
        type: "line",
        data: {
            labels: date(walkTests),
            datasets: [
                {
                    label: "Дистанция (м)",
                    data: distance(walkTests),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(255, 159, 64, 0.2)",
                    borderColor: "rgba(255, 159, 64, 1)",
                },
            ],
        },
        options: {
            scales: {
                x: {
                    ticks: {
                        maxRotation: 80,
                        minRotation: 80,
                    },
                },
            },
        },
    });
}

function breathlessnessGraphic(walkTests) {
    new Chart(document.getElementById("breathlessness"), {
        type: "line",
        data: {
            labels: date(walkTests),
            datasets: [
                {
                    label: "Одышка по шкале Борга",
                    data: breathlessness(walkTests),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(182, 110, 37, 0.2)",
                    borderColor: "rgba(182, 110, 37, 1)",
                },
            ],
        },
        options: {
            scales: {
                x: {
                    ticks: {
                        maxRotation: 80,
                        minRotation: 80,
                    },
                },
            },
        },
    });
}

function pressureBeforeExerciseGraphic(walkTests) {
    new Chart(document.getElementById("pressureBefore"), {
        type: "line",
        data: {
            labels: date(walkTests),
            datasets: [
                {
                    label: "Верхнее давление (мм рт. ст.)",
                    data: upper(pressuresBeforeExercise(walkTests)),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(54, 162, 235, 0.2)",
                    borderColor: "rgba(54, 162, 235, 1)",
                },
                {
                    label: "Нижнее давление (мм рт. ст.)",
                    data: lower(pressuresBeforeExercise(walkTests)),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(255, 99, 132, 0.2)",
                    borderColor: "rgba(255, 99, 132, 1)",
                },
            ],
        },
        options: {
            scales: {
                x: {
                    ticks: {
                        maxRotation: 80,
                        minRotation: 80,
                    },
                },
            },
        },
    });
}

function pulseRateBeforeExerciseGraphic(walkTests) {
    new Chart(document.getElementById("pulseRateBefore"), {
        type: "line",
        data: {
            labels: date(walkTests),
            datasets: [
                {
                    label: "Пульс (уд/мин)",
                    data: pulseRate(pulseOximetriesBeforeExercise(walkTests)),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(75, 192, 192, 0.2)",
                    borderColor: "rgba(75, 192, 192, 1)",
                },
            ],
        },
        options: {
            scales: {
                x: {
                    ticks: {
                        maxRotation: 80,
                        minRotation: 80,
                    },
                },
            },
        },
    });
}

function oxygenPercentageBeforeExerciseGraphic(walkTests) {
    new Chart(document.getElementById("oxygenPercentageBefore"), {
        type: "line",
        data: {
            labels: date(walkTests),
            datasets: [
                {
                    label: "Содержание кислорода в крови (%)",
                    data: oxygenPercentage(pulseOximetriesBeforeExercise(walkTests)),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(153, 102, 255, 0.2)",
                    borderColor: "rgba(153, 102, 255, 1)",
                },
            ],
        },
        options: {
            scales: {
                x: {
                    ticks: {
                        maxRotation: 80,
                        minRotation: 80,
                    },
                },
            },
        },
    });
}

function pressureAfterExerciseGraphic(walkTests) {
    new Chart(document.getElementById("pressureAfter"), {
        type: "line",
        data: {
            labels: date(walkTests),
            datasets: [
                {
                    label: "Верхнее давление (мм рт. ст.)",
                    data: upper(pressuresAfterExercise(walkTests)),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(54, 162, 235, 0.2)",
                    borderColor: "rgba(54, 162, 235, 1)",
                },
                {
                    label: "Нижнее давление (мм рт. ст.)",
                    data: lower(pressuresAfterExercise(walkTests)),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(255, 99, 132, 0.2)",
                    borderColor: "rgba(255, 99, 132, 1)",
                },
            ],
        },
        options: {
            scales: {
                x: {
                    ticks: {
                        maxRotation: 80,
                        minRotation: 80,
                    },
                },
            },
        },
    });
}

function pulseRateAfterExerciseGraphic(walkTests) {
    new Chart(document.getElementById("pulseRateAfter"), {
        type: "line",
        data: {
            labels: date(walkTests),
            datasets: [
                {
                    label: "Пульс (уд/мин)",
                    data: pulseRate(pulseOximetriesAfterExercise(walkTests)),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(75, 192, 192, 0.2)",
                    borderColor: "rgba(75, 192, 192, 1)",
                },
            ],
        },
        options: {
            scales: {
                x: {
                    ticks: {
                        maxRotation: 80,
                        minRotation: 80,
                    },
                },
            },
        },
    });
}

function oxygenPercentageAfterExerciseGraphic(walkTests) {
    new Chart(document.getElementById("oxygenPercentageAfter"), {
        type: "line",
        data: {
            labels: date(walkTests),
            datasets: [
                {
                    label: "Содержание кислорода в крови (%)",
                    data: oxygenPercentage(pulseOximetriesAfterExercise(walkTests)),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(153, 102, 255, 0.2)",
                    borderColor: "rgba(153, 102, 255, 1)",
                },
            ],
        },
        options: {
            scales: {
                x: {
                    ticks: {
                        maxRotation: 80,
                        minRotation: 80,
                    },
                },
            },
        },
    });
}

function distance(walkTests) {
    return walkTests.map((walkTest) => walkTest.distance);
}

function breathlessness(walkTests) {
    return walkTests.map((walkTest) => walkTest.breathlessness);
}

function pressuresBeforeExercise(walkTests) {
    return walkTests.map((walkTest) => walkTest.pressureBefore);
}

function pulseOximetriesBeforeExercise(walkTests) {
    return walkTests.map((walkTest) => walkTest.pulseOximetryBefore);
}

function pressuresAfterExercise(walkTests) {
    return walkTests.map((walkTest) => walkTest.pressureAfter);
}

function pulseOximetriesAfterExercise(walkTests) {
    return walkTests.map((walkTest) => walkTest.pulseOximetryAfter);
}

function upper(pressures) {
    return pressures.map((pressure) => pressure.upper);
}

function lower(pressures) {
    return pressures.map((pressure) => pressure.lower);
}

function oxygenPercentage(pulseOximetries) {
    return pulseOximetries.map((pulseOximetry) => pulseOximetry.oxygenPercentage);
}

function pulseRate(pulseOximetries) {
    return pulseOximetries.map((pulseOximetry) => pulseOximetry.pulseRate);
}

function date(walkTests) {
    return walkTests.map((walkTest) => walkTest.formattedDate);
}
