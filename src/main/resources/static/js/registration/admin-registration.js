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

    fetchCheck(data);
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
                        redirectRegistrationPage(data.code);
                    } else {
                        showErrorMessage();
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
        document.getElementById("success-modal-text-hospital-name").innerText = " " + responseJson.hospital.name + " ";
        document.getElementById("success-modal-text-email").innerText = responseJson.email;
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function showModalError(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}
