const codeInputForm = document.getElementById("code-input-form");

codeInputForm.addEventListener("submit", function (event) {
    event.preventDefault();

    let data = {
        code: codeInputForm.querySelector('input[name="code"]'),
    };

    fetchCheck(data);
});

function fetchCheck(data) {
    fetch("http://localhost:8080/rest/temp", {
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
                        window.location.href = "registration?code=`${data.code}`";
                    } else {
                    }
                });
            } else {
                console.error("Произошла ошибка, для которой не предусмотрено никаких действий");
            }
        })
        .catch((error) => {
            console.error("Ошибка при сохранении заявки", error);
        });
}

function addElement() {}

function removeElement() {}
