const messageForm = document.getElementById("message-form");

const recipientId = Number(window.location.pathname.split("/").pop());

const authorId = document.querySelector("div[data-author]").dataset.author;

let isAdding = true;

document.querySelectorAll("a[data-edit]").forEach((edit) => {
    edit.addEventListener("click", (event) => {
        event.preventDefault();
        isAdding = false;
        messageForm.querySelector('input[name="id"]').value = edit.dataset.edit;
        messageForm.querySelector('textarea[name="message"]').value = document.querySelector(`div[data-text="${edit.dataset.edit}"]`).innerText;
    });
});

document.querySelectorAll("a[data-delete]").forEach((deleteButton) => {
    deleteButton.addEventListener("click", (event) => {
        event.preventDefault();
        fetchDelete(deleteButton.dataset.delete);
    });
});

if (document.getElementById("send")) {
    document.getElementById("send").addEventListener("click", (event) => {
        event.preventDefault();
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
            Accept: "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    sendNotification({
                        messageId: responseJson.id,
                        recipientId: responseJson.recipientId,
                        messageState: "ADDED",
                    });
                });
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
            Accept: "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    sendNotification({
                        messageId: responseJson.id,
                        recipientId: responseJson.recipientId,
                        messageState: "EDITED",
                    });
                });
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function fetchDelete(id) {
    fetch(`http://localhost:8080/rest/messages/delete/${id}`, {
        method: "POST",
    })
        .then((response) => {
            if (response.ok) {
                sendNotification({
                    messageId: id,
                    recipientId: recipientId,
                    messageState: "DELETED",
                });
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

// ------------------------------------------------------------------------------------------------------

const onMessageReceived = (msg) => {
    console.log("Уведомление!!!");
    console.log(msg);
    const imem = document.createElement("div");
    imem.classList = "border p-2 rounded bg-indicator";
    imem.textContent = "Буб уббу буб убуб уб у";
    const message = document.createElement("div");
    message.classList = "col-12 mt-2";
    message.appendChild(imem);
    document.getElementById("messenger").appendChild(message);
};

const onConnected = () => {
    console.log("Годно!!!");
    stompClient.subscribe(`/recipient/${authorId}/messages`, onMessageReceived);
};

const onError = () => {
    console.error("Ахтунг!!!");
};

const socket = new WebSocket("ws://localhost:8080/websocket/messenger");
stompClient = Stomp.over(socket);
stompClient.connect({}, onConnected, onError);

function sendNotification(notification) {
    stompClient.send("/app/messenger", {}, JSON.stringify(notification));
}
