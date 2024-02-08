const referrer = document.referrer == "" ? "/hospitals" : document.referrer;

const requestId = Number(window.location.pathname.split("/").pop());

const codeGenerationForm = document.getElementById("code-generation-form");

codeGenerationForm.addEventListener("submit", (event) => {
    event.preventDefault();

    let data = {
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
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                closeCodeGenerationModal();
                showSuccessModalForCodeGeneration(response);
            } else {
                showErrorModalForCodeGeneration(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showSuccessModalForCodeGeneration(response) {
    response.json().then((responseJson) => {
        fillSuccessModalTextForCodeGeneration(responseJson);
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function showErrorModalForCodeGeneration(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function fillSuccessModalTextForCodeGeneration(responseJson) {
    let successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    let role = document.createElement("span");
    role.className = "fw-bold";
    role.textContent = `${responseJson.roleAlias}`;

    let email = document.createElement("span");
    email.className = "fw-bold";
    email.textContent = `${responseJson.email}`;

    let hospital = document.createElement("span");
    hospital.className = "fw-bold";
    hospital.textContent = `${responseJson.hospitalName}`;

    let expirationDate = document.createElement("span");
    expirationDate.className = "fw-bold";
    expirationDate.textContent = `${responseJson.formattedExpirationDate}`;

    let toHospitals = document.createElement("a");
    toHospitals.className = "href-success";
    toHospitals.innerText = "списку медицинских учреждений";
    toHospitals.href = referrer;

    successModalText.appendChild(document.createTextNode("Код регистрации на роль "));
    successModalText.appendChild(role);
    successModalText.appendChild(document.createTextNode(" был успешно сгенерирован и отправлен на почту "));
    successModalText.appendChild(email);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Медицинское учреждение "));
    successModalText.appendChild(hospital);
    successModalText.appendChild(document.createTextNode(" переходит в состояние ожидания регистрации."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Срок действия кода истечёт "));
    successModalText.appendChild(expirationDate);
    successModalText.appendChild(document.createTextNode(". Администратору необходимо успеть зарегистрироваться до этого времени, иначе код нужно будет выдать повторно."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Вернуться к "));
    successModalText.appendChild(toHospitals);
    successModalText.appendChild(document.createTextNode("."));
}

document.getElementById("decline-request").addEventListener("click", () => {
    fetchDelete();
});

function fetchDelete() {
    fetch("http://localhost:8080/rest/hospital-registration/requests/delete/" + requestId, {
        method: "POST",
    })
        .then((response) => {
            if (response.ok) {
                showSuccessModalForRequestRejecting();
            } else {
                showErrorModalForRequestRejecting(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showSuccessModalForRequestRejecting() {
    fillSuccessModalTextForRequestRejecting();
    new bootstrap.Modal(document.getElementById("success-modal")).show();
}

function showErrorModalForRequestRejecting(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function fillSuccessModalTextForRequestRejecting() {
    let successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    let toHospitals = document.createElement("a");
    toHospitals.className = "href-success";
    toHospitals.innerText = "списку медицинских учреждений";
    toHospitals.href = referrer;

    successModalText.appendChild(document.createTextNode("Заявка на регистрацию медицинского учреждения была успешно отклонена."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Вернуться к "));
    successModalText.appendChild(toHospitals);
    successModalText.appendChild(document.createTextNode("."));
}

function closeCodeGenerationModal() {
    document.getElementById("code-generation-modal-close").click();
}

document.getElementById("success-modal").addEventListener("hidden.bs.modal", () => {
    window.location.href = referrer;
});
