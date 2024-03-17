const userRegistrationForm = document.getElementById("user-registration-form");

const code = new URLSearchParams(window.location.search).get("code");

const submitUrl = userRegistrationForm.getAttribute("action");

userRegistrationForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const email = userRegistrationForm.querySelector('input[name="email"]').value;
    const password = userRegistrationForm.querySelector('input[name="password"]').value;
    const name = userRegistrationForm.querySelector('input[name="name"]').value;
    const lastname = userRegistrationForm.querySelector('input[name="lastname"]').value;
    const patronymic = userRegistrationForm.querySelector('input[name="patronymic"]').value == "" ? null : userRegistrationForm.querySelector('input[name="patronymic"]').value;
    const phoneNumber = userRegistrationForm.querySelector('input[name="phoneNumber"]').value;
    const gender = userRegistrationForm.querySelector('input[name="gender"]:checked') ? userRegistrationForm.querySelector('input[name="gender"]:checked').value : null;
    const birthdate = userRegistrationForm.querySelector('input[name="birthdate"]').value;

    let post;
    if (submitUrl.includes("administrator") || submitUrl.includes("doctor")) {
        post = userRegistrationForm.querySelector('input[name="post"]').value;
    }

    let data;
    if (submitUrl.includes("administrator") || submitUrl.includes("doctor")) {
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
                    patronymic: patronymic,
                    phoneNumber: phoneNumber,
                    gender: gender,
                    birthdate: birthdate,
                },
            },
            code: code,
        };
    } else if (submitUrl.includes("patient")) {
        data = {
            userSecurityInformationAddingDto: {
                email: email,
                password: password,
            },
            userInformationAddingDto: {
                name: name,
                lastname: lastname,
                patronymic: patronymic,
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
    fetch(`http://localhost:8080${submitUrl}`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
            "X-CSRF-TOKEN": userRegistrationForm.querySelector('input[name="_csrf"]').value,
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
    const successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    const fullName = document.createElement("span");
    fullName.className = "fw-bold";
    fullName.textContent = `${responseJson.fullName}`;

    const login = document.createElement("a");
    login.className = "href-success";
    login.textContent = "войти";
    login.href = "/login";

    successModalText.appendChild(document.createTextNode("Вы успешно зарегистрировались в приложении, "));
    successModalText.appendChild(fullName);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Теперь вы можете "));
    successModalText.appendChild(login);
    successModalText.appendChild(document.createTextNode(" в свой аккаунт."));

    if (submitUrl.includes("patient")) {
        successModalText.appendChild(document.createElement("br"));
        successModalText.appendChild(document.createElement("br"));
        successModalText.appendChild(document.createTextNode("Рекомендуем вам сразу же после входа в аккаунт заполнить анамнез: он позволит вашему будущему лечащему врачу сразу же получить важную для дальнейшего лечения информацию."));
    }
}

document.getElementById("success-modal").addEventListener("hidden.bs.modal", () => {
    window.location.href = "/login";
});
