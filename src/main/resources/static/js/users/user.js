const employeeInfoEditingForm = document.getElementById("employee-info-editing-form");
const userInfoEditingForm = document.getElementById("user-info-editing-form");
const loginInfoEditingForm = document.getElementById("login-info-editing-form");

const vacationForm = document.getElementById("vacation-form");
const sickLeaveForm = document.getElementById("sick-leave-form");
const dismissalForm = document.getElementById("dismissal-form");
const patientInactivityForm = document.getElementById("patient-inactivity-form");

document.addEventListener("DOMContentLoaded", () => {
    employeeInfoEditingModalInit();
    userInfoEditingModalInit();
    loginInfoEditingModalInit();
    vacationModalInit();
    sickLeaveModalInit();
    dismissalModalInit();
    patientInactivityModalInit();
});

function employeeInfoEditingModalInit() {
    let employeeInfoEditingModal = document.getElementById("employee-info-editing-modal");
    if (employeeInfoEditingModal) {
        employeeInfoEditingForm.addEventListener("submit", (event) => {
            event.preventDefault();
            let data = {
                id: employeeInfoEditingForm.querySelector('input[name="id"]').value,
                post: employeeInfoEditingForm.querySelector('input[name="post"]').value,
            };
            fetchInfoEdit(data, "employee-info");
        });
        employeeInfoEditingModal.addEventListener("hidden.bs.modal", () => {
            refreshForm(employeeInfoEditingForm, "employee-info-editing-error");
        });
    }
}

function userInfoEditingModalInit() {
    let userInfoEditingModal = document.getElementById("user-info-editing-modal");
    if (userInfoEditingModal) {
        userInfoEditingForm.addEventListener("submit", (event) => {
            event.preventDefault();
            let data = {
                id: userInfoEditingForm.querySelector('input[name="id"]').value,
                name: userInfoEditingForm.querySelector('input[name="name"]').value,
                lastname: userInfoEditingForm.querySelector('input[name="lastname"]').value,
                patronymic: userInfoEditingForm.querySelector('input[name="patronymic"]').value,
                phoneNumber: userInfoEditingForm.querySelector('input[name="phoneNumber"]').value,
                gender: userInfoEditingForm.querySelector('input[name="gender"]:checked') ? userInfoEditingForm.querySelector('input[name="gender"]:checked').value : null,
                birthdate: userInfoEditingForm.querySelector('input[name="birthdate"]').value,
            };
            fetchInfoEdit(data, "user-info");
        });
        userInfoEditingModal.addEventListener("hidden.bs.modal", () => {
            refreshForm(userInfoEditingForm, "user-info-editing-error");
        });
    }
}

function loginInfoEditingModalInit() {
    let loginInfoEditingModal = document.getElementById("login-info-editing-modal");
    if (loginInfoEditingModal) {
        changePasswordFlag();
        loginInfoEditingForm.addEventListener("submit", (event) => {
            event.preventDefault();
            let data = {
                id: loginInfoEditingForm.querySelector('input[name="id"]').value,
                email: loginInfoEditingForm.querySelector('input[name="email"]').value,
                password: loginInfoEditingForm.querySelector('input[name="password"]').value == "" ? null : loginInfoEditingForm.querySelector('input[name="password"]').value,
            };
            fetchInfoEdit(data, "login-info");
        });
        loginInfoEditingModal.addEventListener("hidden.bs.modal", () => {
            refreshForm(loginInfoEditingForm, "login-info-editing-error");
        });
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
                response.json().then((responseJson) => {
                    if (whichInfo == "employee-info") {
                        document.getElementById("employee-info-editing-modal-close-1").click();
                        refreshEmployeeInfoEditingFormData(responseJson);
                        refreshEmployeeInfo(responseJson);
                        showSuccessModal("Рабочая информация была успешно изменена");
                    } else if (whichInfo == "user-info") {
                        document.getElementById("user-info-editing-modal-close-1").click();
                        refreshUserInfoEditingFormData(responseJson);
                        refreshUserInfo(responseJson);
                        showSuccessModal("Общая информация была успешно изменена");
                    } else if (whichInfo == "login-info") {
                        document.getElementById("login-info-editing-modal-close-1").click();
                        refreshLoginInfoEditingFormData(responseJson);
                        refreshLoginInfo(responseJson);
                        showSuccessModal("Логин-информация была успешно изменена");
                    }
                });
            } else {
                response.json().then((responseJson) => {
                    if (whichInfo == "employee-info") {
                        showErrorBlock("employee-info-editing-error", responseJson.errorDescription);
                    } else if (whichInfo == "user-info") {
                        showErrorBlock("user-info-editing-error", responseJson.errorDescription);
                    } else if (whichInfo == "login-info") {
                        showErrorBlock("login-info-editing-error", responseJson.errorDescription);
                    }
                });
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function refreshEmployeeInfoEditingFormData(responseJson) {
    employeeInfoEditingForm.querySelector('input[name="post"]').value = responseJson.post;
    employeeInfoEditingForm.querySelector('input[name="post"]').setAttribute("value", responseJson.post);
}

function refreshEmployeeInfo(responseJson) {
    document.getElementById("info-post").innerText = responseJson.post;
}

function refreshUserInfoEditingFormData(responseJson) {
    userInfoEditingForm.querySelector('input[name="name"]').value = responseJson.name;
    userInfoEditingForm.querySelector('input[name="name"]').setAttribute("value", responseJson.name);
    userInfoEditingForm.querySelector('input[name="lastname"]').value = responseJson.lastname;
    userInfoEditingForm.querySelector('input[name="lastname"]').setAttribute("value", responseJson.lastname);
    userInfoEditingForm.querySelector('input[name="patronymic"]').value = responseJson.patronymic;
    userInfoEditingForm.querySelector('input[name="patronymic"]').setAttribute("value", responseJson.patronymic);
    userInfoEditingForm.querySelector('input[name="phoneNumber"]').value = responseJson.sourcePhoneNumber;
    userInfoEditingForm.querySelector('input[name="phoneNumber"]').setAttribute("value", responseJson.sourcePhoneNumber);
    if (responseJson.gender != null) {
        document.getElementById("MALE").removeAttribute("checked");
        document.getElementById("FEMALE").removeAttribute("checked");
        document.getElementById(responseJson.gender).checked = "checked";
        document.getElementById(responseJson.gender).setAttribute("checked", "checked");
    }
    userInfoEditingForm.querySelector('input[name="birthdate"]').value = responseJson.birthdate;
    userInfoEditingForm.querySelector('input[name="birthdate"]').setAttribute("value", responseJson.birthdate);
}

function refreshUserInfo(responseJson) {
    document.title = responseJson.fullName;
    document.getElementById("profile-full-name").innerText = responseJson.fullName;
    document.getElementById("info-full-name").innerText = responseJson.fullName;
    document.getElementById("info-phone-number").innerText = responseJson.phoneNumber;
    document.getElementById("info-gender").innerText = responseJson.genderAlias;
    document.getElementById("info-birthdate").innerText = responseJson.formattedBirthdate;
    if (responseJson.genderAlias != null) {
        document.getElementById("info-gender-block").classList.remove("visually-hidden");
    }
    if (responseJson.formattedBirthdate != null) {
        document.getElementById("info-birthdate-block").classList.remove("visually-hidden");
        document.getElementById("info-birthdate-block").classList.add("mt-1");
    }
}

function refreshLoginInfoEditingFormData(responseJson) {
    loginInfoEditingForm.querySelector('input[name="email"]').value = responseJson.email;
    loginInfoEditingForm.querySelector('input[name="email"]').setAttribute("value", responseJson.email);
}

function refreshLoginInfo(responseJson) {
    document.getElementById("info-email").innerText = responseJson.email;
}

function refreshForm(form, errorBlockId) {
    form.reset();
    hideErrorBlock(errorBlockId);
    if (form == loginInfoEditingForm) {
        document.getElementById("password-block").classList.add("visually-hidden");
    }
}

function changePasswordFlag() {
    document.getElementById("change-password").addEventListener("change", (check) => {
        if (check.target.checked) {
            document.getElementById("password-block").classList.remove("visually-hidden");
        } else {
            document.getElementById("password-block").classList.add("visually-hidden");
        }
    });
}

/**/

function vacationModalInit() {
    let vacationModal = document.getElementById("vacation-modal");
    if (vacationModal) {
        vacationForm.addEventListener("submit", (event) => {
            event.preventDefault();
            let data = {
                toWhomId: vacationForm.querySelector('input[name="vacation-to-whom-id"]').value,
                endDate: vacationForm.querySelector('input[name="vacation-end-date"]').value,
                comment: vacationForm.querySelector('input[name="vacation-comment"]').value == "" ? null : vacationForm.querySelector('input[name="vacation-comment"]').value,
            };
            fetchInactivityAdd(data, "vacation");
        });
        vacationModal.addEventListener("hidden.bs.modal", () => {
            refreshForm(vacationForm, "vacation-error");
        });
    }
}

function sickLeaveModalInit() {
    let sickLeaveModal = document.getElementById("sick-leave-modal");
    if (sickLeaveModal) {
        sickLeaveForm.addEventListener("submit", (event) => {
            event.preventDefault();
            let data = {
                toWhomId: sickLeaveForm.querySelector('input[name="sick-leave-to-whom-id"]').value,
                endDate: sickLeaveForm.querySelector('input[name="sick-leave-end-date"]').value,
                comment: sickLeaveForm.querySelector('input[name="sick-leave-comment"]').value == "" ? null : vacationForm.querySelector('input[name="sick-leave-comment"]').value,
            };
            fetchInactivityAdd(data, "sick-leave");
        });
        sickLeaveModal.addEventListener("hidden.bs.modal", () => {
            refreshForm(sickLeaveForm, "sick-leave-error");
        });
    }
}

function dismissalModalInit() {
    let dismissalModal = document.getElementById("dismissal-modal");
    if (dismissalModal) {
        dismissalForm.addEventListener("submit", (event) => {
            event.preventDefault();
            let data = {
                toWhomId: dismissalForm.querySelector('input[name="dismissal-to-whom-id"]').value,
                endDate: dismissalForm.querySelector('input[name="dismissal-end-date"]').value,
            };
            fetchInactivityAdd(data, "dismissal");
        });
        dismissalModal.addEventListener("hidden.bs.modal", () => {
            refreshForm(dismissalForm, "dismissal-error");
        });
    }
}

function patientInactivityModalInit() {
    let patientInactivityModal = document.getElementById("patient-inactivity-modal");
    if (patientInactivityModal) {
        patientInactivityForm.addEventListener("submit", (event) => {
            event.preventDefault();
            let data = {
                toWhomId: patientInactivityForm.querySelector('input[name="patient-inactivity-to-whom-id"]').value,
                endDate: patientInactivityForm.querySelector('input[name="patient-inactivity-end-date"]').value,
            };
            fetchInactivityAdd(data, "patient-inactivity");
        });
        patientInactivityModal.addEventListener("hidden.bs.modal", () => {
            refreshForm(patientInactivityForm, "patient-inactivity-error");
        });
    }
}

function fetchInactivityAdd(data, whichInactivity) {
    fetch("http://localhost:8080/rest/user-inactivities/add/" + whichInactivity, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                if (whichInactivity == "vacation") {
                    document.getElementById("vacation-modal-close-1").click();
                    refreshInactivity(response);
                    showSuccessModal("Отпуск был успешно назначен");
                } else if (whichInactivity == "sick-leave") {
                    document.getElementById("sick-leave-modal-close-1").click();
                    refreshInactivity(response);
                    showSuccessModal("Больничный был успешно назначен");
                } else if (whichInactivity == "dismissal") {
                    document.getElementById("dismissal-modal-close-1").click();
                    refreshInactivity(response);
                    showSuccessModal("Сотрудник был успешно уволен");
                } else if (whichInactivity == "patient-inactivity") {
                    document.getElementById("patient-inactivity-modal-close-1").click();
                    refreshInactivity(response);
                    showSuccessModal("Пациент был успешно переведён в неактивное состояние");
                }
            } else {
                response.json().then((responseJson) => {
                    if (whichInactivity == "vacation") {
                        showErrorBlock("vacation-error", responseJson.errorDescription);
                    } else if (whichInactivity == "sick-leave") {
                        showErrorBlock("sick-leave-error", responseJson.errorDescription);
                    } else if (whichInactivity == "dismissal") {
                        showErrorBlock("dismissal-error", responseJson.errorDescription);
                    } else if (whichInactivity == "patient-inactivity") {
                        showErrorBlock("patient-inactivity-error", responseJson.errorDescription);
                    }
                });
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function refreshInactivity(response) {
    /*response.json().then((responseJson) => {
        document.getElementById("active").remove();

        let inactivityBlock = document.createElement("div");
        inactivityBlock.classList = "mb-0";

        let inactivityMessage = document.createElement("p");
        inactivityMessage.classList = "text-danger mb-1";
    });*/
    console.log("success");
}

function showSuccessModal(message) {
    document.getElementById("success-modal-text").innerText = message;
    new bootstrap.Modal(document.getElementById("success-modal")).show();
}

function showErrorBlock(errorBlockId, errorDescription) {
    let errorBlock = document.getElementById(errorBlockId);
    errorBlock.querySelector("span").innerText = errorDescription;
    errorBlock.classList.remove("visually-hidden");
}

function hideErrorBlock(errorBlockId) {
    document.getElementById(errorBlockId).classList.add("visually-hidden");
}
