const liquidForm = document.getElementById("liquid-form");

liquidForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        liquid: liquidForm.querySelector('input[name="liquid"]').value,
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/indicators/add/liquid", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": liquidForm.querySelector('input[name="_csrf"]').value,
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                liquidForm.reset();
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
    document.getElementById("success-modal-text").innerText = 'Показатель "Питьевой режим" был успешно отправлен';
    new bootstrap.Modal(document.getElementById("success-modal")).show();
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}
