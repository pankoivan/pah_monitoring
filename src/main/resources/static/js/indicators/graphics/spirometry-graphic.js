const patientId = document.querySelector("div[data-patient]").dataset.patient;

const period = new URLSearchParams(window.location.search).get("period");

document.addEventListener("DOMContentLoaded", () => {
    fetchInit();
});

function fetchInit() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/spirometry?period=${period}`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((spirometries) => {
                    volumesGraphic(spirometries);
                    tiffnoIndexGraphic(spirometries);
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function volumesGraphic(spirometries) {
    new Chart(document.getElementById("volumes"), {
        type: "line",
        data: {
            labels: date(spirometries),
            datasets: [
                {
                    label: "ЖЕЛ (л)",
                    data: vlc(spirometries),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "ФЖЕЛ (л)",
                    data: avlc(spirometries),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "ООЛ (л)",
                    data: rlv(spirometries),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "ОФВ1 (л)",
                    data: vfe1(spirometries),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "ОЕЛ (л)",
                    data: tlc(spirometries),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function tiffnoIndexGraphic(spirometries) {
    new Chart(document.getElementById("tiffnoIndex"), {
        type: "line",
        data: {
            labels: date(spirometries),
            datasets: [
                {
                    label: "Индекс Тиффно (%)",
                    data: tiffnoIndex(spirometries),
                    borderWidth: 1,
                    tension: 0.1,
                    backgroundColor: "rgba(153, 102, 255, 0.2)",
                    borderColor: "rgba(153, 102, 255, 1)",
                },
            ],
        },
    });
}

function vlc(spirometries) {
    return spirometries.map((spirometry) => spirometry.vlc);
}

function avlc(spirometries) {
    return spirometries.map((spirometry) => spirometry.avlc);
}

function rlv(spirometries) {
    return spirometries.map((spirometry) => spirometry.rlv);
}

function vfe1(spirometries) {
    return spirometries.map((spirometry) => spirometry.vfe1);
}

function tlc(spirometries) {
    return spirometries.map((spirometry) => spirometry.tlc);
}

function tiffnoIndex(spirometries) {
    return spirometries.map((spirometry) => spirometry.tiffnoIndex);
}

function date(spirometries) {
    return spirometries.map((spirometry) => spirometry.formattedDate);
}
