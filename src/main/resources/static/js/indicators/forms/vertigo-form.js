const vertigoForm = document.getElementById("vertigo-form");

vertigoForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        duration: checked(vertigoForm.querySelector('input[name="duration"]:checked')),
        nausea: checked(vertigoForm.querySelector('input[name="nausea"]:checked')),
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/indicators/add/vertigo", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": vertigoForm.querySelector('input[name="_csrf"]').value,
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                vertigoForm.reset();
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
    document.getElementById("success-modal-text").innerText = 'Показатель "Головокружение" был успешно отправлен';
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
