const hospitalRegistrationForm = document.getElementById("hospital-registration-form");

const hospitals = document.getElementById("hospitals");

let timerId;

hospitalRegistrationForm.querySelector('input[name="hospitalName"]').addEventListener("input", () => {
    hospitalRegistrationForm.querySelector('input[name="hospitalOid"]').value = "";

    clearTimeout(timerId);

    timerId = setTimeout(() => {
        fetchSearch(hospitalRegistrationForm.querySelector('input[name="hospitalName"]').value);
    }, 620);
});

function fetchSearch(data) {
    fetch("http://localhost:8080/rest/client/registry/search", {
        method: "POST",
        headers: {
            "Content-Type": "application/text",
            Accept: "application/json",
        },
        body: data,
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    clearHospitals();
                    fillHospitals(responseJson);
                });
            } else {
                console.error("Ошибка сервера", error);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function clearHospitals() {
    while (hospitals.firstChild) {
        hospitals.removeChild(hospitals.firstChild);
    }
}

function fillHospitals(responseJson) {
    responseJson.forEach((registryHospital) => {
        appendHospital(registryHospital);
    });
}

function appendHospital(registryHospital) {
    const hospital = document.createElement("a");
    hospital.className = "list-group-item list-group-item-action fs-6";
    hospital.innerText = registryHospital.name;
    hospital.href = "#";
    hospital.addEventListener("click", (event) => {
        event.preventDefault();
        hospitalRegistrationForm.querySelector('input[name="hospitalName"]').value = registryHospital.name;
        hospitalRegistrationForm.querySelector('input[name="hospitalOid"]').value = registryHospital.oid;
        clearHospitals(hospitals);
    });
    hospitals.appendChild(hospital);
}

hospitalRegistrationForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        name: hospitalRegistrationForm.querySelector('input[name="name"]').value,
        lastname: hospitalRegistrationForm.querySelector('input[name="lastname"]').value,
        patronymic: hospitalRegistrationForm.querySelector('input[name="patronymic"]').value == "" ? null : hospitalRegistrationForm.querySelector('input[name="patronymic"]').value,
        post: hospitalRegistrationForm.querySelector('input[name="post"]').value,
        passport: hospitalRegistrationForm.querySelector('input[name="passport"]').value,
        phoneNumber: hospitalRegistrationForm.querySelector('input[name="phoneNumber"]').value,
        email: hospitalRegistrationForm.querySelector('input[name="email"]').value,
        comment: hospitalRegistrationForm.querySelector('textarea[name="comment"]').value == "" ? null : hospitalRegistrationForm.querySelector('textarea[name="comment"]').value,
        hospitalAddingDto: {
            name: hospitalRegistrationForm.querySelector('input[name="hospitalName"]').value,
            oid: hospitalRegistrationForm.querySelector('input[name="hospitalOid"]').value,
        },
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/hospital-registration/requests/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                hospitalRegistrationForm.reset();
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

    const hospitalName = document.createElement("span");
    hospitalName.className = "fw-bold";
    hospitalName.innerText = `${responseJson.hospitalName}`;

    const email = document.createElement("span");
    email.className = "fw-bold";
    email.innerText = `${responseJson.email}`;

    successModalText.appendChild(document.createTextNode("Заявка на регистрацию медицинского учреждения "));
    successModalText.appendChild(hospitalName);
    successModalText.appendChild(document.createTextNode(" успешно отправлена."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Если ваша личность будет подтверждена, главный администратор приложения сгенерирует код для регистрации, который придёт на почту "));
    successModalText.appendChild(email);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Ожидайте."));
}
