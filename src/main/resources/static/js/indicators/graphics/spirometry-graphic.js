const patientId = document.querySelector("div[data-patient]").dataset.patient;

document.addEventListener("DOMContentLoaded", () => {
    fetchInit();
});

function fetchInit() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/spirometry`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((spirometries) => {
                    mainSpirometryTable(spirometries);
                    tiffnoIndexSpirometryTable(spirometries);
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function mainSpirometryTable(spirometries) {
    new Chart(document.getElementById("main"), {
        type: "line",
        data: {
            labels: spirometryDate(spirometries),
            datasets: [
                {
                    label: "ЖЕЛ (л)",
                    data: spirometryVlc(spirometries),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "ФЖЕЛ (л)",
                    data: spirometryAvlc(spirometries),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "ООЛ (л)",
                    data: spirometryRlv(spirometries),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "ОФВ1 (л)",
                    data: spirometryVfe1(spirometries),
                    borderWidth: 1,
                    tension: 0.1,
                },
                {
                    label: "ОЕЛ (л)",
                    data: spirometryTlc(spirometries),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function tiffnoIndexSpirometryTable(spirometries) {
    new Chart(document.getElementById("tiffnoIndex"), {
        type: "line",
        data: {
            labels: spirometryDate(spirometries),
            //labels: ["ab", "byb", "pep"],
            datasets: [
                {
                    label: "Индекс Тиффно (%)",
                    data: spirometryTiffnoIndex(spirometries),
                    //data: ["1", "10", "3"],
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function spirometryVlc(spirometries) {
    return spirometries.map((spirometry) => spirometry.vlc);
}

function spirometryAvlc(spirometries) {
    return spirometries.map((spirometry) => spirometry.avlc);
}

function spirometryRlv(spirometries) {
    return spirometries.map((spirometry) => spirometry.rlv);
}

function spirometryVfe1(spirometries) {
    return spirometries.map((spirometry) => spirometry.vfe1);
}

function spirometryTlc(spirometries) {
    return spirometries.map((spirometry) => spirometry.tlc);
}

function spirometryTiffnoIndex(spirometries) {
    console.log(spirometries.map((spirometry) => spirometry.tiffnoIndex));
    return spirometries.map((spirometry) => spirometry.tiffnoIndex);
}

function spirometryDate(spirometries) {
    return spirometries.map((spirometry) => spirometry.formattedDate);
}
