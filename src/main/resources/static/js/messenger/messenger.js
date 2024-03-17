const messageForm = document.getElementById("message-form");

const recipientId = Number(new URLSearchParams(window.location.search).get("recipientId"));

const authorId = Number(document.querySelector("div[data-you]").dataset.you);

let isAdding = true;

document.addEventListener("DOMContentLoaded", () => {
    messagesTooltips();
    scrollDown();
    messagesEvents();
});

function messagesTooltips() {
    [...document.querySelectorAll('[data-bs-toggle="tooltip"]')].map((tooltipTriggerEl) => new bootstrap.Tooltip(tooltipTriggerEl));
}

function scrollDown() {
    const scrollPanel = document.getElementById("messages-block");
    scrollPanel.scrollTop = scrollPanel.scrollHeight;
}

function messagesEvents() {
    document.querySelectorAll("div[data-author]").forEach((message) => {
        messageEditEvent(message);
        messageDeleteEvent(message);
    });
}

function messageEditEvent(message) {
    if (message.querySelector("a[data-edit]")) {
        message.querySelector("a[data-edit]").addEventListener("click", (event) => {
            event.preventDefault();
            isAdding = false;
            messageForm.querySelector('input[name="id"]').value = message.dataset.message;
            messageForm.querySelector('textarea[name="message"]').value = message.querySelector(`div[data-text]`).textContent;
        });
    }
}

function messageDeleteEvent(message) {
    if (message.querySelector("a[data-delete]")) {
        message.querySelector("a[data-delete]").addEventListener("click", (event) => {
            event.preventDefault();
            fetchDelete(message.dataset.message);
        });
    }
}

if (messageForm) {
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
            "X-CSRF-TOKEN": messageForm.querySelector('input[name="_csrf"]').value,
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                messageForm.reset();
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
            "X-CSRF-TOKEN": document.getElementById("editing-token").querySelector('input[name="_csrf"]').value,
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                isAdding = true;
                messageForm.reset();
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
        headers: {
            "X-CSRF-TOKEN": document.getElementById("deletion-token").querySelector('input[name="_csrf"]').value,
        },
    })
        .then((response) => {
            if (response.ok) {
                isAdding = true;
                messageForm.reset();
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
                <div class="message-author border p-2 rounded d-inline-block w-auto text-start text-black" data-message="${responseJson.id}" data-author>
                    <div data-text>${responseJson.text}</div>
                    <div class="d-flex flex-row align-items-center justify-content-between">
                        <div>
                            <a href="#" class="text-decoration-none" data-edit>
                                <img width="16" src="/svg/accept.svg" alt="Редактировать" />
                            </a>
                            <a href="#" class="text-decoration-none" data-delete>
                                <img width="16" src="/svg/decline.svg" alt="Удалить" />
                            </a>
                        </div>
                        <div
                            class="message-date text-end text-secondary"
                            data-bs-toggle="tooltip"
                            data-bs-placement="top"
                            data-bs-html="true"
                            title="${responseJson.longFormattedDate}"
                            data-editing-date
                        >
                            <span>${responseJson.shortFormattedDate}</span>
                            <span class="d-none" data-editing-info>(ред)</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        `.trim();
    } else {
        newMessage.innerHTML = `
        <div class="col-12 mt-2">
            <div class="col-10 ms-auto text-end">
                <div class="message-recipient border p-2 rounded d-inline-block w-auto text-start text-black" data-message="${responseJson.id}" data-recipient>
                    <div data-text>${responseJson.text}</div>
                    <div class="d-flex flex-row align-items-center justify-content-between">
                        <div></div>
                        <div
                            class="message-date text-end text-secondary"
                            data-bs-toggle="tooltip"
                            data-bs-placement="left"
                            data-bs-html="true"
                            title="${responseJson.longFormattedDate}"
                            data-editing-date
                        >
                            <span>${responseJson.shortFormattedDate}</span>
                            <span class="d-none" data-editing-info>(ред)</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        `.trim();
    }
    document.getElementById("messages-block").appendChild(newMessage);
    if (body.authorId == authorId) {
        const message = document.querySelector(`div[data-message="${body.messageId}"]`);
        messageEditEvent(message);
        messageDeleteEvent(message);
    }
    messagesTooltips();
    scrollDown();
}

function whenEdited(responseJson, body) {
    const message = document.querySelector(`div[data-message="${body.messageId}"]`);
    message.querySelector("div[data-text]").textContent = responseJson.text;
    message.querySelector("span[data-editing-info]").classList.remove("d-none");
    const editingDate = message.querySelector("div[data-editing-date]");
    editingDate.setAttribute("title", `${responseJson.longFormattedDate}<br>ред. ${responseJson.longFormattedEditingDate}`);
    messagesTooltips();
}

function whenDeleted(body) {
    document.querySelector(`div[data-message="${body.messageId}"]`).remove();
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
