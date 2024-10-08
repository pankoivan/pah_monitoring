const patientId = document.querySelector("div[data-patient]").dataset.patient;

const period = new URLSearchParams(window.location.search).get("period");

document.addEventListener("DOMContentLoaded", () => {
    fetchInit();
});

function fetchInit() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/pulse-oximetry?period=${period}`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((pulseOximetries) => {
                    pulseRateBeforeExerciseGraphic(pulseOximetries);
                    oxygenPercentageBeforeExerciseGraphic(pulseOximetries);
                    pulseRateAfterExerciseGraphic(pulseOximetries);
                    oxygenPercentageAfterExerciseGraphic(pulseOximetries);
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function pulseRateBeforeExerciseGraphic(pulseOximetries) {
    new Chart(document.getElementById("pulseRateBefore"), {
        type: "line",
        data: {
            labels: date(beforeExercise(pulseOximetries)),
            datasets: [
                {
                    label: "Пульс (уд/мин)",
                    data: pulseRate(beforeExercise(pulseOximetries)),
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

function oxygenPercentageBeforeExerciseGraphic(pulseOximetries) {
    new Chart(document.getElementById("oxygenPercentageBefore"), {
        type: "line",
        data: {
            labels: date(beforeExercise(pulseOximetries)),
            datasets: [
                {
                    label: "Содержание кислорода в крови (%)",
                    data: oxygenPercentage(beforeExercise(pulseOximetries)),
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

function pulseRateAfterExerciseGraphic(pulseOximetries) {
    new Chart(document.getElementById("pulseRateAfter"), {
        type: "line",
        data: {
            labels: date(afterExercise(pulseOximetries)),
            datasets: [
                {
                    label: "Пульс (уд/мин)",
                    data: pulseRate(afterExercise(pulseOximetries)),
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

function oxygenPercentageAfterExerciseGraphic(pulseOximetries) {
    new Chart(document.getElementById("oxygenPercentageAfter"), {
        type: "line",
        data: {
            labels: date(afterExercise(pulseOximetries)),
            datasets: [
                {
                    label: "Содержание кислорода в крови (%)",
                    data: oxygenPercentage(afterExercise(pulseOximetries)),
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

function beforeExercise(pulseOximetries) {
    return pulseOximetries.filter((pulseOximetry) => pulseOximetry.afterExercise == false);
}

function afterExercise(pulseOximetries) {
    return pulseOximetries.filter((pulseOximetry) => pulseOximetry.afterExercise == true);
}

function oxygenPercentage(pulseOximetries) {
    return pulseOximetries.map((pulseOximetry) => pulseOximetry.oxygenPercentage);
}

function pulseRate(pulseOximetries) {
    return pulseOximetries.map((pulseOximetry) => pulseOximetry.pulseRate);
}

function date(pulseOximetries) {
    return pulseOximetries.map((pulseOximetry) => pulseOximetry.formattedDate);
}
