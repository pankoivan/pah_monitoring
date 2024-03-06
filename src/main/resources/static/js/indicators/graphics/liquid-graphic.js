const patientId = document.querySelector("div[data-patient]").dataset.patient;

const period = new URLSearchParams(window.location.search).get("period");

document.addEventListener("DOMContentLoaded", () => {
    fetchInit();
});

function fetchInit() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/liquid?period=${period}`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((liquids) => {
                    liquidGraphic(liquids);
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function liquidGraphic(liquids) {
    new Chart(document.getElementById("liquid"), {
        type: "line",
        data: {
            labels: date(liquids),
            datasets: [
                {
                    label: "Количество жидкости в день (л)",
                    data: liquid(liquids),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function liquid(liquids) {
    return liquids.map((liquid) => liquid.liquid);
}

function date(liquids) {
    return liquids.map((liquid) => liquid.formattedDate);
}
