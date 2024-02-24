const patientId = document.querySelector("div[data-patient]").dataset.patient;

document.addEventListener("DOMContentLoaded", () => {
    fetchInit();
});

function fetchInit() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/liquid`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((liquids) => {
                    liquidTable(liquids);
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function liquidTable(liquids) {
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
