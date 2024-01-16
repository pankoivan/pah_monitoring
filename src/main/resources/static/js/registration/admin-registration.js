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

    let fullname = document.createElement("span");
    fullname.className = "fw-bold";
    fullname.innerText = ` ${responseJson.lastname} ${responseJson.name} ${responseJson.patronymic} `;

    let link = document.createElement("a");
    link.href = "/login";
    link.textContent = " войти ";

    let login = document.createElement("span");
    login.appendChild(document.createTextNode("Теперь вы можете"));
    login.appendChild(link);
    login.appendChild(document.createTextNode("в свой аккаунт."));

    successModalText.appendChild(document.createTextNode("Вы успешно зарегистрировались в приложении,"));
    successModalText.appendChild(fullname);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(login);
}
