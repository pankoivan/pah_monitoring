const pressureForm = document.getElementById("pressure-form");

pressureForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        upper: pressureForm.querySelector('input[name="upper"]').value,
        lower: pressureForm.querySelector('input[name="lower"]').value,
        afterExercise: checked(pressureForm.querySelector('input[name="afterExercise"]:checked')),
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/indicators/add/pressure", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": pressureForm.querySelector('input[name="_csrf"]').value,
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                pressureForm.reset();
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
    document.getElementById("success-modal-text").innerText = 'Показатель "Давление" был успешно отправлен';
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
