const codeInputForm = document.getElementById("code-input-form");

codeInputForm.addEventListener("submit", (event) => {
    event.preventDefault();
    fetchCheck(codeInputForm.querySelector('input[name="code"]').value);
});

function fetchCheck(data) {
    fetch("http://localhost:8080/rest/security-codes/check", {
        method: "POST",
        headers: {
            "Content-Type": "application/text",
            Accept: "application/json",
            "X-CSRF-TOKEN": codeInputForm.querySelector('input[name="_csrf"]').value,
        },
        body: data,
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    if (responseJson.exists) {
                        redirectRegistrationPage(data);
                    } else {
                        showErrorMessage();
                    }
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function redirectRegistrationPage(code) {
    window.location.href = `/registration?code=${code}`;
}

function showErrorMessage() {
    document.getElementById("code-input-error").classList.remove("visually-hidden");
}

function hideErrorMessage() {
    document.getElementById("code-input-error").classList.add("visually-hidden");
}

document.getElementById("code-input-modal").addEventListener("hidden.bs.modal", () => {
    codeInputForm.reset();
    hideErrorMessage();
});
