const codeGenerationForm = document.getElementById("code-generation-form");

codeGenerationForm.addEventListener("submit", function (event) {
    event.preventDefault();

    let data = {
        role: codeGenerationForm.querySelector('select[name="role"]').value,
        email: codeGenerationForm.querySelector('input[name="email"]').value,
        expirationDate: codeGenerationForm.querySelector('select[name="expirationDate"]').value,
    };

    fetchSave(data);
});

function fetchSave(data) {
    fetch("http://localhost:8080/rest/security-codes/generate/by-admin", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                codeGenerationForm.reset();
                showModalSuccess(response);
            } else {
                showModalError(response);
            }
        })
        .catch((error) => {
            console.error("Произошла ошибка, для которой не предусмотрено никаких действий", error);
        });
}

function showModalSuccess(response) {
    response.json().then((responseJson) => {
        fillSuccessModalText(responseJson);
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function fillSuccessModalText(responseJson) {
    let successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    let email = document.createElement("span");
    email.className = "fw-bold";
    email.textContent = ` ${responseJson.email}`;

    successModalText.appendChild(document.createTextNode("Код регистрации был успешно сгенерирован и отправлен на почту"));
    successModalText.appendChild(email);
    successModalText.appendChild(document.createTextNode("."));
}

function showModalError(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}
