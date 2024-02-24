const patientId = document.querySelector("div[data-patient]").dataset.patient;

document.addEventListener("DOMContentLoaded", () => {
    fetchInit();
});

function fetchInit() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/weight`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((weights) => {
                    weightTable(weights);
                    bodyMassIndexTable(weights);
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function weightTable(weights) {
    new Chart(document.getElementById("weight"), {
        type: "line",
        data: {
            labels: weightDate(weights),
            datasets: [
                {
                    label: "Вес (кг)",
                    data: weightWeight(weights),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function bodyMassIndexTable(weights) {
    new Chart(document.getElementById("bodyMassIndex"), {
        type: "line",
        data: {
            labels: weightDate(weights),
            datasets: [
                {
                    label: "Индекс массы тела (кг/м²)",
                    data: weightBodyMassIndex(weights),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function weightWeight(weights) {
    return weights.map((weight) => weight.weight);
}

function weightBodyMassIndex(weights) {
    return weights.map((weight) => weight.bodyMassIndex);
}

function weightDate(weights) {
    return weights.map((weight) => weight.formattedDate);
}
