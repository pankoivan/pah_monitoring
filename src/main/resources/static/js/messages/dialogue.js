const messageForm = document.getElementById("message-form");

const recipientId = Number(window.location.pathname.split("/").pop());

let isAdding = true;

document.querySelectorAll("a[data-edit]").forEach((edit) => {
    edit.addEventListener("click", () => {
        isAdding = false;
        messageForm.querySelector('input[name="id"]').value = edit.dataset.edit;
        messageForm.querySelector('textarea[name="message"]').value = document.querySelector(`div[data-text="${edit.dataset.edit}"]`).innerText;
    });
});

document.querySelectorAll("a[data-delete]").forEach((deleteButton) => {
    deleteButton.addEventListener("click", () => {
        fetchDelete(deleteButton.dataset.delete);
    });
});

if (document.getElementById("send")) {
    document.getElementById("send").addEventListener("click", () => {
        let data;
        if (isAdding) {
            data = {
                recipientId: recipientId,
                text: messageForm.querySelector('textarea[name="message"]').value,
            };
            fetchAdd(data);
        } else {
            data = {
                id: messageForm.querySelector('input[name="id"]').value,
                text: messageForm.querySelector('textarea[name="message"]').value,
            };
            fetchEdit(data);
        }
    });
}

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/messages/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                showSuccessModal("Сообщение было успешно отправлено");
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function fetchEdit(data) {
    fetch("http://localhost:8080/rest/messages/edit", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                showSuccessModal("Сообщение было успешно изменено");
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function fetchDelete(id) {
    fetch("http://localhost:8080/rest/messages/delete/" + id, {
        method: "POST",
    })
        .then((response) => {
            if (response.ok) {
                showSuccessModal("Сообщение было успешно удалено");
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
    new bootstrap.Modal(document.getElementById("success-modal")).show();
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

if (document.getElementById("success-modal")) {
    document.getElementById("success-modal").addEventListener("hidden.bs.modal", () => {
        window.location.reload();
    });
}
