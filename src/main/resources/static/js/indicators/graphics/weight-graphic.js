const patientId = document.querySelector("div[data-patient]").dataset.patient;

const period = new URLSearchParams(window.location.search).get("period");

document.addEventListener("DOMContentLoaded", () => {
    fetchInit();
});

function fetchInit() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/weight?period=${period}`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((weights) => {
                    weightGraphic(weights);
                    bodyMassIndexGraphic(weights);
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function weightGraphic(weights) {
    new Chart(document.getElementById("weight"), {
        type: "line",
        data: {
            labels: date(weights),
            datasets: [
                {
                    label: "Вес (кг)",
                    data: weight(weights),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(22, 235, 24, 0.2)",
                    borderColor: "rgba(22, 235, 24, 1)",
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

function bodyMassIndexGraphic(weights) {
    new Chart(document.getElementById("bodyMassIndex"), {
        type: "line",
        data: {
            labels: date(weights),
            datasets: [
                {
                    label: "Индекс массы тела (кг/м²)",
                    data: bodyMassIndex(weights),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(237, 1, 185, 0.2)",
                    borderColor: "rgba(237, 1, 185, 1)",
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

function weight(weights) {
    return weights.map((weight) => weight.weight);
}

function bodyMassIndex(weights) {
    return weights.map((weight) => weight.bodyMassIndex);
}

function date(weights) {
    return weights.map((weight) => weight.formattedDate);
}
