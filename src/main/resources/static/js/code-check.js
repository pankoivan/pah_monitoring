const codeInputForm = document.getElementById("code-input-form");

codeInputForm.addEventListener("submit", function (event) {
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
