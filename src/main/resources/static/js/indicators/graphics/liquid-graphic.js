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
            labels: liquidDate(liquids),
            datasets: [
                {
                    label: "Количество жидкости в день (л)",
                    data: liquidLiquid(liquids),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function liquidLiquid(liquids) {
    return liquids.map((liquid) => liquid.liquid);
}

function liquidDate(liquids) {
    return liquids.map((liquid) => liquid.formattedDate);
}
