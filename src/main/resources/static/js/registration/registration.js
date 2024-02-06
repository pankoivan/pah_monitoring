const userRegistrationForm = document.getElementById("user-registration-form");

const role = userRegistrationForm.querySelector('input[name="role"]').value;

const submitUrlPart = role == "Администратор" ? "admin" : role == "Врач" ? "doctor" : "patient";

userRegistrationForm.addEventListener("submit", function (event) {
    event.preventDefault();

    code = new URLSearchParams(window.location.search).get("code");

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

    console.log("Patronymic: " + patronymic);

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
                patronymic: patronymic,
                phoneNumber: phoneNumber,
                gender: gender,
                birthdate: birthdate,
            },
            code: code,
        };
    }

    fetchSave(data);
});

function fetchSave(data) {
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
                showModalSuccess(response);
            } else {
                showModalError(response);
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
    fullname.innerText =
        responseJson.userInformation.patronymic != ""
            ? ` ${responseJson.userInformation.lastname} ${responseJson.userInformation.name} ${responseJson.userInformation.patronymic}`
            : ` ${responseJson.userInformation.lastname} ${responseJson.userInformation.name}`;

    let link = document.createElement("a");
    link.className = "href-success";
    link.textContent = "войти";
    link.href = "/login";

    let login = document.createElement("span");
    login.appendChild(document.createTextNode("Теперь вы можете "));
    login.appendChild(link);
    login.appendChild(document.createTextNode(" в свой аккаунт."));

    successModalText.appendChild(document.createTextNode("Вы успешно зарегистрировались в приложении,"));
    successModalText.appendChild(fullname);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(login);
}

document.getElementById("success-modal-close-1").addEventListener("click", function (event) {
    event.preventDefault();
    window.location.href = "/login";
});

document.getElementById("success-modal-close-2").addEventListener("click", function (event) {
    event.preventDefault();
    window.location.href = "/login";
});
