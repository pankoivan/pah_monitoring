const hospitalRegistrationForm = document.getElementById("hospital-registration-form");

let timerId;

hospitalRegistrationForm.querySelector('input[name="hospitalName"]').addEventListener("input", function () {
    clearTimeout(timerId);

    timerId = setTimeout(function () {
        let data = hospitalRegistrationForm.querySelector('input[name="hospitalName"]').value;
        fetchSearch(data);
    }, 800);
});

function fetchSearch(data) {
    fetch("http://localhost:8080/rest/client/registry/search", {
        method: "POST",
        headers: {
            "Content-Type": "application/text",
        },
        body: data,
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    let hospitals = document.getElementById("hospitals");
                    clearListbox(hospitals);
                    responseJson.forEach((registryHospital) => {
                        appendListBoxItem(registryHospital, hospitals);
                    });
                });
            } else {
                console.error("На сервере произошла ошибка, для которой здесь не предусмотрено никаких действий", error);
            }
        })
        .catch((error) => {
            console.error("Произошла ошибка, для которой не предусмотрено никаких действий", error);
        });
}

function clearListbox(hospitals) {
    while (hospitals.firstChild) {
        hospitals.removeChild(hospitals.firstChild);
    }
}

function appendListBoxItem(registryHospital, hospitals) {
    let hospital = document.createElement("a");
    hospital.className = "list-group-item list-group-item-action fs-6";
    hospital.href = "#";
    hospital.innerText = registryHospital.name;
    hospital.addEventListener("click", function (event) {
        event.preventDefault();
        hospitalRegistrationForm.querySelector('input[name="hospitalName"]').value = this.textContent;
        clearListbox(hospitals);
    });
    hospitals.appendChild(hospital);
}

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
        comment: hospitalRegistrationForm.querySelector('textarea[name="comment"]').value == "" ? null : hospitalRegistrationForm.querySelector('textarea[name="comment"]').value,
        hospitalAddingDto: {
            name: hospitalRegistrationForm.querySelector('input[name="hospitalName"]').value,
        },
    };

    fetchSave(data);
});

function fetchSave(data) {
    fetch("http://localhost:8080/rest/hospital-registration/requests/add", {
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
    hospitalName.innerText = ` ${responseJson.hospital.name} `;

    let email = document.createElement("span");
    email.className = "fw-bold";
    email.innerText = ` ${responseJson.email}`;

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
