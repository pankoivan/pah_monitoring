const codeInputForm = document.getElementById("code-input-form");

codeInputForm.addEventListener("submit", function (event) {
    event.preventDefault();

    let data = {
        code: codeInputForm.querySelector('input[name="code"]').value,
    };

    fetchCheck(data);
});

function fetchCheck(data) {
    fetch("http://localhost:8080/rest/security-codes/check", {
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

function redirectRegistrationPage(code) {
    window.location.href = `/registration?code=${code}`;
}

function showErrorMessage() {
    document.getElementById("code-input-error").style.display = "";
}
