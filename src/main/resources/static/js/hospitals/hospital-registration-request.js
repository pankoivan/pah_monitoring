const referrer = document.referrer == "" ? "/hospitals" : document.referrer;

const requestId = Number(window.location.pathname.split("/").pop());

const codeGenerationForm = document.getElementById("code-generation-form");

if (codeGenerationForm) {
    codeGenerationForm.addEventListener("submit", (event) => {
        event.preventDefault();

        const data = {
            hospitalRegistrationRequestId: requestId,
            expirationDate: codeGenerationForm.querySelector('select[name="expirationDate"]').value,
        };

        fetchAdd(data);
    });
}

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
    const successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    const role = document.createElement("span");
    role.className = "fw-bold";
    role.innerText = `${responseJson.roleAlias}`;

    const email = document.createElement("span");
    email.className = "fw-bold";
    email.innerText = `${responseJson.email}`;

    const hospital = document.createElement("span");
    hospital.className = "fw-bold";
    hospital.innerText = `${responseJson.hospitalName}`;

    const expirationDate = document.createElement("span");
    expirationDate.className = "fw-bold";
    expirationDate.textContent = `${responseJson.formattedExpirationDate}`;

    const toHospitals = document.createElement("a");
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

if (document.getElementById("decline-request")) {
    document.getElementById("decline-request").addEventListener("click", () => {
        fetchDelete();
    });
}

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
    const successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    const toHospitals = document.createElement("a");
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

if (document.getElementById("success-modal")) {
    document.getElementById("success-modal").addEventListener("hidden.bs.modal", () => {
        window.location.href = referrer;
    });
}
