const adminRegistrationForm = document.getElementById("admin-registration-form");

adminRegistrationForm.addEventListener("submit", function (event) {
    event.preventDefault();

    let data = {
        userSecurityInformationSavingDto: {
            email: adminRegistrationForm.querySelector('input[name="email"]').value,
            password: adminRegistrationForm.querySelector('input[name="password"]').value,
        },
        employeeInformationSavingDto: {
            post: adminRegistrationForm.querySelector('input[name="post"]').value,
            userInformationSavingDto: {
                name: adminRegistrationForm.querySelector('input[name="name"]').value,
                lastname: adminRegistrationForm.querySelector('input[name="lastname"]').value,
                patronymic: adminRegistrationForm.querySelector('input[name="patronymic"]').value,
                gender: adminRegistrationForm.querySelector('input[name="gender"]:checked').value,
                birthdate: adminRegistrationForm.querySelector('input[name="birthdate"]').value,
                phoneNumber: adminRegistrationForm.querySelector('input[name="phoneNumber"]').value,
            },
        },
        code: new URLSearchParams(window.location.search).urlParams.get("code"),
    };

    fetchSave(data);
});

function fetchSave(data) {
    fetch("http://localhost:8080/rest/registration/administrator", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    if (responseJson.isTrue) {
                        hospitalRegistrationForm.reset();
                        showModalSuccess(response);
                    } else {
                        showModalError(responseJson);
                    }
                });
            } else {
                console.error("На сервере произошла ошибка, для которой здесь не предусмотрено никаких действий");
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
