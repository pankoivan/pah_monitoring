const hospitalRegistrationForm = document.getElementById("hospital-registration-form");

hospitalRegistrationForm.addEventListener("submit", function (event) {
    event.preventDefault();

    let data = {
        name: hospitalRegistrationForm.querySelector('input[name="name"]').value,
        lastname: hospitalRegistrationForm.querySelector('input[name="lastname"]').value,
        patronymic: hospitalRegistrationForm.querySelector('input[name="patronymic"]').value,
        post: hospitalRegistrationForm.querySelector('input[name="post"]').value,
        passport: hospitalRegistrationForm.querySelector('input[name="passport"]').value,
        phoneNumber: hospitalRegistrationForm.querySelector('input[name="phoneNumber"]').value,
        email: hospitalRegistrationForm.querySelector('input[name="email"]').value,
        comment: hospitalRegistrationForm.querySelector('textarea[name="comment"]').value,
        hospitalSavingDto: {
            name: hospitalRegistrationForm.querySelector('input[name="hospitalName"]').value,
        },
    };

    fetchSave(data);
});

function fetchSave(data) {
    fetch("http://localhost:8080/rest/hospital-registration-requests/save", {
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
    response.json().then((responseJson) => {
        fillSuccessModalText(responseJson);
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function showModalError(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function fillSuccessModalText(responseJson) {
    let successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    let hospitalName = document.createElement("span");
    hospitalName.className = "fw-bold";
    hospitalName.innerText = ` "${responseJson.hospital.name}" `;

    let email = document.createElement("span");
    email.className = "fw-bold";
    email.innerText = ` "${responseJson.email}"`;

    successModalText.appendChild(document.createTextNode("Заявка на регистрацию медицинского учреждения"));
    successModalText.appendChild(hospitalName);
    successModalText.appendChild(document.createTextNode("успешно отправлена."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Если ваша личность будет подтверждена, главный администратор приложения сгенерирует код, который придёт на почту"));
    successModalText.appendChild(email);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Ожидайте."));
}
