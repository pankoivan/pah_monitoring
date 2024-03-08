const messageForm = document.getElementById("message-form");

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
    const scrollPanel = document.getElementById("messages-block");
    scrollPanel.scrollTop = scrollPanel.scrollHeight;
}

// -------------------------------------------------- WebSocket --------------------------------------------------

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
    const newMessage = document.createElement("div");
    if (body.authorId == authorId) {
        newMessage.innerHTML = `
        <div class="col-12 mt-2">
            <div class="col-10 me-auto text-start">
                <div class="message-author border p-2 rounded d-inline-block w-auto text-start text-black" data-message=${responseJson.id}>
                    <div>${responseJson.text}</div>
                    <div class="d-inline-block w-auto d-flex flex-row align-items-center justify-content-between">
                        <div>
                            <a href="#" class="text-decoration-none d-none">
                                <img width="16" src="../../static/svg/msgedit.svg" th:src="@{/svg/msgedit.svg}" alt="Редактировать" />
                            </a>
                            <a href="#" class="text-decoration-none d-none">
                                <img width="16" src="../../static/svg/msgdelete.svg" th:src="@{/svg/msgdelete.svg}" alt="Удалить" />
                            </a>
                        </div>
                        <div class="message-date text-end text-secondary">${responseJson.formattedDate}</div>
                    </div>
                </div>
            </div>
        </div>
        `.trim();
    } else {
        newMessage.innerHTML = `
        <div class="col-12 mt-2">
            <div class="col-10 ms-auto text-end">
                <div class="message-recipient border p-2 rounded d-inline-block w-auto text-start text-black">
                    <div>${responseJson.text}</div>
                    <div class="message-date text-end text-secondary">${responseJson.formattedDate}</div>
                </div>
            </div>
        </div>
    `.trim();
    }
    document.getElementById("messages-block").appendChild(newMessage);
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

// ---

document.querySelectorAll("div[data-message]").forEach((message) => {
    message.addEventListener("mouseover", (event) => {
        event.currentTarget.querySelectorAll("a").forEach((a) => {
            a.classList.remove("d-none");
        });
    });
});

document.querySelectorAll("div[data-message]").forEach((message) => {
    message.addEventListener("mouseout", (event) => {
        event.currentTarget.querySelectorAll("a").forEach((a) => {
            a.classList.add("d-none");
        });
    });
});
