const codeGenerationForm = document.getElementById("code-generation-form");

codeGenerationForm.addEventListener("submit", function (event) {
    event.preventDefault();

    let data = {
        hospitalRegistrationRequestId: "adf",
        expirationDate: codeGenerationForm.querySelector('select[name="expirationDate"]').value,
    };

    fetchSave(data);
});

function fetchSave(data) {
    fetch("http://localhost:8080/rest/security-codes/generate/by-main-admin", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                hospitalRegistrationForm.reset();
                showModalSuccess(response);
            } else {
                showModalError(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка при сохранении заявки", error);
        });
}

function showModalSuccess(response) {
    response.json().then(() => {
        fillSuccessModalText();
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function showModalError(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function fillSuccessModalText() {
    let successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    let toRequests = document.createElement("a");
    toRequests.innerText = `К странице заявок.`;

    successModalText.appendChild(document.createTextNode("Заявка на регистрацию этого медицинского учреждения была успешно отклонена."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(toRequests);
}
