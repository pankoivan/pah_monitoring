const chestPainForm = document.getElementById("chest-pain-form");

chestPainForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        type: checked(chestPainForm.querySelector('input[name="type"]:checked')),
        duration: checked(chestPainForm.querySelector('input[name="duration"]:checked')),
        nitroglycerin: checked(chestPainForm.querySelector('input[name="nitroglycerin"]:checked')),
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/indicators/add/chest-pain", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                chestPainForm.reset();
                showSuccessModal(response);
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showSuccessModal() {
    document.getElementById("success-modal-text").innerText = 'Показатель "Боль в груди" был успешно отправлен';
    new bootstrap.Modal(document.getElementById("success-modal")).show();
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function checked(checked) {
    return checked ? checked.value : checked;
}
