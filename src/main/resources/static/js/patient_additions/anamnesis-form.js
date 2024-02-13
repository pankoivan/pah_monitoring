const anamnesisForm = document.getElementById("anamnesis-form");

anamnesisForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        hospitalRegistrationRequestId: requestId,
        expirationDate: codeGenerationForm.querySelector('select[name="expirationDate"]').value,
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/security-codes/generate/by-main-admin", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                document.getElementById("code-generation-modal-close-1").click();
                showSuccessModalForCodeGeneration(response);
            } else {
                showErrorModalForCodeGeneration(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showSuccessModal() {
    fillSuccessModalText();
    new bootstrap.Modal(document.getElementById("success-modal")).show();
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function fillSuccessModalText() {
    const successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    const toAnamnesis = document.createElement("a");
    toAnamnesis.className = "href-success";
    toAnamnesis.innerText = "анамнеза";
    toAnamnesis.href = "";
}
