let currentModal;

let errorModal = document.getElementById("error-modal");
let successModal = document.getElementById("success-modal");

let employeeInfoEditingModal = document.getElementById("employee-info-editing-modal");
let userInfoEditingModal = document.getElementById("user-info-editing-modal");
let loginInfoEditingModal = document.getElementById("login-info-editing-modal");

let vacationModal = document.getElementById("vacation-modal");
let sickLeaveModal = document.getElementById("sick-leave-modal");
let dismissalModal = document.getElementById("dismissal-modal");
let patientInactivityModal = document.getElementById("patient-inactivity-modal");

let employeeInfoEditingForm = document.getElementById("employee-info-editing-form");
let userInfoEditingForm = document.getElementById("user-info-editing-form");
let loginInfoEditingForm = document.getElementById("login-info-editing-form");

let vacationForm = document.getElementById("vacation-form");
let sickLeaveForm = document.getElementById("sick-leave-form");
let dismissalForm = document.getElementById("dismissal-form");
let patientInactivityForm = document.getElementById("patient-inactivity-form");

document.addEventListener("DOMContentLoaded", () => {
    modalsInit();
});

function modalsInit() {
    errorModal = new bootstrap.Modal(errorModal);
    successModal = new bootstrap.Modal(successModal);

    if (employeeInfoEditingModal) {
        employeeInfoEditingModal = new bootstrap.Modal(employeeInfoEditingModal);
        employeeInfoEditingForm.addEventListener("submit", (event) => {
            event.preventDefault();
            let data = {
                post: employeeInfoEditingForm.querySelector('input[name="post"]').value,
            };
            currentModal = "employee-info";
            fetchInfoEdit(data, "employee-info");
        });
    }
    if (userInfoEditingModal) {
        userInfoEditingModal = new bootstrap.Modal(userInfoEditingModal);
        userInfoEditingForm.addEventListener("submit", (event) => {
            event.preventDefault();
            let data = {
                name: userInfoEditingForm.querySelector('input[name="name"]').value,
                lastname: userInfoEditingForm.querySelector('input[name="lastname"]').value,
                patronymic: userInfoEditingForm.querySelector('input[name="patronymic"]').value,
                phoneNumber: userInfoEditingForm.querySelector('input[name="phoneNumber"]').value,
                gender: userInfoEditingForm.querySelector('input[name="gender"]:checked') ? userInfoEditingForm.querySelector('input[name="gender"]:checked').value : null,
                birthdate: userInfoEditingForm.querySelector('input[name="birthdate"]').value,
            };
            currentModal = "user-info";
            fetchInfoEdit(data, "user-info");
        });
    }
    if (loginInfoEditingModal) {
        loginInfoEditingModal = new bootstrap.Modal(loginInfoEditingModal);
        loginInfoEditingForm.addEventListener("submit", (event) => {
            event.preventDefault();
            let data = {
                email: loginInfoEditingForm.querySelector('input[name="email"]').value,
                password: loginInfoEditingForm.querySelector('input[name="password"]').value,
            };
            currentModal = "login-info";
            fetchInfoEdit(data, "login-info");
        });
    }

    if (vacationModal) {
        vacationModal = new bootstrap.Modal(vacationModal);
    }
    if (sickLeaveModal) {
        sickLeaveModal = new bootstrap.Modal(sickLeaveModal);
    }
    if (dismissalModal) {
        dismissalModal = new bootstrap.Modal(dismissalModal);
    }
    if (patientInactivityModal) {
        patientInactivityModal = new bootstrap.Modal(patientInactivityModal);
    }
}

function fetchInfoEdit(data, whichInfo) {
    fetch("http://localhost:8080/rest/user-profile/edit/" + whichInfo, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                if (whichInfo == "employee-info") {
                    showSuccessModal("Рабочая информация была успешно изменена");
                } else if (whichInfo == "user-info") {
                    showSuccessModal("Общая информация была успешно изменена");
                } else if (whichInfo == "login-info") {
                    showSuccessModal("Логин-информация была успешно изменена");
                }
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showSuccessModal(message) {
    document.getElementById("success-modal-text").innerText = message;
    successModal.show();
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        errorModal.show();
    });
}

document.getElementById("error-modal-close-1").addEventListener("click", () => {
    errorModalCloseEvent();
});

document.getElementById("error-modal-close-2").addEventListener("click", () => {
    errorModalCloseEvent();
});

function errorModalCloseEvent() {
    if (currentModal == "employee-info") {
        employeeInfoEditingModal.show();
    }
    if (currentModal == "user-info") {
        userInfoEditingModal.show();
    }
    if (currentModal == "login-info") {
        loginInfoEditingModal.show();
    }
    if (currentModal == "vacation") {
        vacationModal.show();
    }
    if (currentModal == "sick-leave") {
        sickLeaveModal.show();
    }
    if (currentModal == "dismissal") {
        dismissalModal.show();
    }
    if (currentModal == "patient-inactivity-modal") {
        patientInactivityModal.show();
    }
}

document.getElementById("change-password").addEventListener("change", (check) => {
    if (check.target.checked) {
        document.getElementById("password-block").classList.remove("visually-hidden");
    } else {
        document.getElementById("password-block").classList.add("visually-hidden");
    }
});
