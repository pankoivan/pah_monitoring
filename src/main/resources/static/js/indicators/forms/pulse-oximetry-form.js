const pulseOximetryForm = document.getElementById("pulse-oximetry-form");

pulseOximetryForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        oxygenPercentage: pulseOximetryForm.querySelector('input[name="oxygenPercentage"]').value,
        pulseRate: pulseOximetryForm.querySelector('input[name="pulseRate"]').value,
        afterExercise: checked(pulseOximetryForm.querySelector('input[name="afterExercise"]:checked')),
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/indicators/add/pulse-oximetry", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": pulseOximetryForm.querySelector('input[name="_csrf"]').value,
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                pulseOximetryForm.reset();
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
    document.getElementById("success-modal-text").innerText = 'Показатель "Пульсоксиметрия" был успешно отправлен';
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
