const coughForm = document.getElementById("cough-form");

coughForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        type: checked(coughForm.querySelector('input[name="type"]:checked')),
        power: checked(coughForm.querySelector('input[name="power"]:checked')),
        timbre: checked(coughForm.querySelector('input[name="timbre"]:checked')),
        hemoptysis: checked(coughForm.querySelector('input[name="hemoptysis"]:checked')),
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/indicators/add/cough", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": coughForm.querySelector('input[name="_csrf"]').value,
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                coughForm.reset();
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
    document.getElementById("success-modal-text").innerText = 'Показатель "Кашель" был успешно отправлен';
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
