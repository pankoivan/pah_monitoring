const requestId = Number(window.location.pathname.split("/").pop());

/* Для генерации кода */

const codeGenerationForm = document.getElementById("code-generation-form");

codeGenerationForm.addEventListener("submit", function (event) {
    event.preventDefault();

    let data = {
        hospitalRegistrationRequestId: requestId,
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
                closeCodeGenerationModal();
                showModalSuccessForCodeGeneration(response);
            } else {
                showModalErrorForCodeGeneration(response);
            }
        })
        .catch((error) => {
            console.error("Произошла ошибка, для которой не предусмотрено никаких действий", error);
        });
}

function closeCodeGenerationModal() {
    document.getElementById("code-generation-modal-close").click();
}

function showModalSuccessForCodeGeneration(response) {
    response.json().then(() => {
        fillSuccessModalTextForCodeGeneration();
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function showModalErrorForCodeGeneration(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function fillSuccessModalTextForCodeGeneration() {
    let successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "Код был успешно сгенерирован. Теперь данное медицинское учреждение переходит в состояние ожидания регистрации администратора";

    successModalText.appendChild(document.createTextNode("Код был успешно сгенерирован. Теперь данное медицинское учреждение переходит в состояние ожидания регистрации администратора."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("После успешной регистрации администратора медицинское учреждение будет успешно зарегистрировано в приложении."));
}

/* Для удаления заявки */

document.getElementById("decline-request").addEventListener("click", function (event) {
    event.preventDefault();
    fetchDelete();
});

function fetchDelete() {
    console.log("AAAAAAAAAAAAAAAAAAAAA");
    fetch("http://localhost:8080/rest/hospital-registration/requests/delete/" + requestId, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                showModalSuccessForRequestRejecting();
            } else {
                showModalErrorForRequestRejecting(response);
            }
        })
        .catch((error) => {
            console.error("Произошла ошибка, для которой не предусмотрено никаких действий", error);
        });
}

function showModalSuccessForRequestRejecting() {
    fillSuccessModalTextForRequestRejecting();
    new bootstrap.Modal(document.getElementById("success-modal")).show();
}

function showModalErrorForRequestRejecting(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function fillSuccessModalTextForRequestRejecting() {
    let successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    let toRequests = document.createElement("a");
    toRequests.innerText = "К странице заявок.";

    successModalText.appendChild(document.createTextNode("Заявка на регистрацию этого медицинского учреждения была успешно отклонена."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(toRequests);
}
