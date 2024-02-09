const codeInputForm = document.getElementById("code-input-form");

codeInputForm.addEventListener("submit", (event) => {
    event.preventDefault();

    let data = codeInputForm.querySelector('input[name="code"]').value;

    fetchCheck(data);
});

function fetchCheck(data) {
    fetch("http://localhost:8080/rest/security-codes/check", {
        method: "POST",
        headers: {
            "Content-Type": "application/text",
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
