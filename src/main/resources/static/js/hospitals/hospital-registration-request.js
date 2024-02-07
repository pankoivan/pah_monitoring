const referrer = document.referrer == "" ? "/hospitals" : document.referrer;

const requestId = Number(window.location.pathname.split("/").pop());

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
    response.json().then((responseJson) => {
        fillSuccessModalTextForCodeGeneration(responseJson);
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function showModalErrorForCodeGeneration(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function fillSuccessModalTextForCodeGeneration(responseJson) {
    let successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    let toHospitals = document.createElement("a");
    toHospitals.className = "href-success";
    toHospitals.innerText = "списку медицинских учреждений";
    toHospitals.href = "/hospitals";

    let email = document.createElement("span");
    email.className = "fw-bold";
    email.textContent = ` ${responseJson.email}`;

    let hospital = document.createElement("span");
    hospital.className = "fw-bold";
    hospital.textContent = ` ${responseJson.hospital.name} `;

    successModalText.appendChild(document.createTextNode("Код регистрации был успешно сгенерирован и отправлен на почту"));
    successModalText.appendChild(email);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Медицинское учреждение"));
    successModalText.appendChild(hospital);
    successModalText.appendChild(document.createTextNode("переходит в состояние ожидания регистрации."));
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
            console.error("Ошибка запроса", error);
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

    let toHospitals = document.createElement("a");
    toHospitals.className = "href-success";
    toHospitals.innerText = " списку ";
    toHospitals.href = referrer;

    successModalText.appendChild(document.createTextNode("Заявка на регистрацию медицинского учреждения была успешно отклонена."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Вернуться к"));
    successModalText.appendChild(toHospitals);
    successModalText.appendChild(document.createTextNode("медицинских учреждений"));
    successModalText.appendChild(document.createTextNode("."));
}

document.getElementById("success-modal").addEventListener("hidden.bs.modal", () => {
    window.location.href = referrer;
});
