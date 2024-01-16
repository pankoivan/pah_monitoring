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
    fetch("http://localhost:8080/rest/hospital-registration-requests/add", {
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

    let fullname = document.createElement("span");
    fullname.className = "fw-bold";
    fullname.innerText = ` "${responseJson.lastname}" "${responseJson.name}" "${responseJson.patronymic}" `;

    let link = document.createElement("a");
    link.href = "/login";
    link.textContent = " войти ";

    let login = document.createElement("span");
    login.appendChild(document.createTextNode("Теперь вы можете"));
    login.appendChild(link);
    login.appendChild(document.createTextNode("в свой аккаунт."));

    successModalText.appendChild(document.createTextNode("Вы успешно зарегистрировались в приложении,"));
    successModalText.appendChild(fullname);
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode(login));
}
