const spirometryForm = document.getElementById("spirometry-form");

spirometryForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        vlc: spirometryForm.querySelector('input[name="vlc"]').value,
        avlc: spirometryForm.querySelector('input[name="avlc"]').value,
        rlv: spirometryForm.querySelector('input[name="rlv"]').value,
        vfe1: spirometryForm.querySelector('input[name="vfe1"]').value,
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/indicators/add/spirometry", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                spirometryForm.reset();
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
    document.getElementById("success-modal-text").innerText = 'Показатель "Спирометрия" был успешно отправлен';
    new bootstrap.Modal(document.getElementById("success-modal")).show();
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}
