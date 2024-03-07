const messageForm = document.getElementById("message-form");

//const recipientId = Number(window.location.pathname.split("/").pop());
const recipientId = Number(new URLSearchParams(window.location.search).get("recipientId"));

const authorId = Number(document.querySelector("div[data-author]").dataset.author);

let isAdding = true;

document.addEventListener("DOMContentLoaded", () => {
    scrollDown();
});

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
                        authorId: authorId,
                        recipientId: recipientId,
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
                        authorId: authorId,
                        recipientId: recipientId,
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
                    authorId: authorId,
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

function scrollDown() {
    const scrollPanel = document.getElementById("messenger");
    scrollPanel.scrollTop = scrollPanel.scrollHeight;
}

// -------------------------------------------------- WebSocket --------------------------------------------------

/*function onReceived(id) {
    fetch(`http://localhost:8080/rest/messages/${id}`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    const imem = document.createElement("div");
                    imem.classList = "border p-2 rounded bg-indicator";
                    imem.textContent = responseJson.text;
                    const message = document.createElement("div");
                    message.classList = "col-12 mt-2";
                    message.appendChild(imem);
                    document.getElementById("messenger").appendChild(message);
                    scrollDown();
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}*/

const onMessageReceived = (msg) => {
    const body = JSON.parse(msg.body);
    if (body.authorId == recipientId || body.authorId == authorId) {
        if (body.messageState == "ADDED") {
            whenSaved(body);
        } else if (body.messageState == "EDITED") {
            whenSaved(body);
        } else if (body.messageState == "DELETED") {
            whenDeleted(body);
        }
    }
};

function whenSaved(body) {
    fetch(`http://localhost:8080/rest/messages/${body.messageId}`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    if (body.messageState == "ADDED") {
                        whenAdded(responseJson, body);
                    } else if (body.messageState == "EDITED") {
                        whenEdited(responseJson, body);
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

function whenAdded(responseJson, body) {
    const message = document.createElement("div");
    message.classList = "border p-2 rounded bg-indicator";
    message.textContent = responseJson.text;
    const column = document.createElement("div");
    column.classList = "col-12 mt-2";
    column.appendChild(message);
    console.log("COLUMN: " + column);
    document.getElementById("messenger").appendChild(column);
    scrollDown();
}

function whenEdited(responseJson, body) {
    // later
}

function whenDeleted(body) {
    // later
}

const onConnected = () => {
    stompClient.subscribe(`/recipient/${authorId}/messages`, onMessageReceived);
    stompClient.subscribe(`/recipient/${recipientId}/messages`, onMessageReceived);
};

const onError = () => {
    console.error("Ошибка веб-сокета");
};

const socket = new WebSocket("ws://localhost:8080/websocket/messenger");
stompClient = Stomp.over(socket);
stompClient.connect({}, onConnected, onError);

function sendNotification(notification) {
    stompClient.send(`/app/messenger`, {}, JSON.stringify(notification));
}
