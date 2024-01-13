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
        hospitalDto: {
            name: hospitalRegistrationForm.querySelector('input[name="hospitalName"]').value,
        },
    };

    fetchSave(data);
});

function fetchSave(data) {
    fetch("http://localhost:8080/rest/hospital-registration-requests/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                hospitalRegistrationForm.reset();
                response.json().then((responseJson) => {
                    console.log(responseJson);
                });
            } else {
                showModalError(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка при сохранении контакта", error);
        });
}

function showModalError(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}
