const userRegistrationForm = document.getElementById("user-registration-form");

const role = userRegistrationForm.querySelector('input[name="role"]').value;

const submitUrlPart = role == "Администратор" ? "admin" : role == "Врач" ? "doctor" : "patient";

const code = new URLSearchParams(window.location.search).get("code");

userRegistrationForm.addEventListener("submit", (event) => {
    event.preventDefault();

    let email = userRegistrationForm.querySelector('input[name="email"]').value;
    let password = userRegistrationForm.querySelector('input[name="password"]').value;
    let name = userRegistrationForm.querySelector('input[name="name"]').value;
    let lastname = userRegistrationForm.querySelector('input[name="lastname"]').value;
    let patronymic = userRegistrationForm.querySelector('input[name="patronymic"]').value;
    let phoneNumber = userRegistrationForm.querySelector('input[name="phoneNumber"]').value;
    let gender = userRegistrationForm.querySelector('input[name="gender"]:checked') ? userRegistrationForm.querySelector('input[name="gender"]:checked').value : null;
    let birthdate = userRegistrationForm.querySelector('input[name="birthdate"]').value;

    if (role == "Администратор" || role == "Врач") {
        post = userRegistrationForm.querySelector('input[name="post"]').value;
    }

    let data;

    if (role == "Администратор" || role == "Врач") {
        data = {
            userSecurityInformationAddingDto: {
                email: email,
                password: password,
            },
            employeeInformationAddingDto: {
                post: post,
                userInformationAddingDto: {
                    name: name,
                    lastname: lastname,
                    patronymic: patronymic == "" ? null : patronymic,
                    phoneNumber: phoneNumber,
                    gender: gender,
                    birthdate: birthdate,
                },
            },
            code: code,
        };
    } else if (role == "Пациент") {
        data = {
            userSecurityInformationAddingDto: {
                email: email,
                password: password,
            },
            userInformationAddingDto: {
                name: name,
                lastname: lastname,
                patronymic: patronymic == "" ? null : patronymic,
                phoneNumber: phoneNumber,
                gender: gender,
                birthdate: birthdate,
            },
            code: code,
        };
    }

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/registration/" + submitUrlPart, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                userRegistrationForm.reset();
                showSuccessModal(response);
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showSuccessModal(response) {
    response.json().then((responseJson) => {
        fillSuccessModalText(responseJson);
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function showErrorModal(response) {
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
    fullname.innerText = `${responseJson.fullName}`;

    let login = document.createElement("a");
    login.className = "href-success";
    login.textContent = "войти";
    login.href = "/login";

    successModalText.appendChild(document.createTextNode("Вы успешно зарегистрировались в приложении, "));
    successModalText.appendChild(fullname);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Теперь вы можете "));
    successModalText.appendChild(login);
    successModalText.appendChild(document.createTextNode(" в свой аккаунт."));
}

document.getElementById("success-modal").addEventListener("hidden.bs.modal", () => {
    window.location.href = "/login";
});
