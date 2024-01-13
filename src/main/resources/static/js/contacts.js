const contactsForm = document.getElementById("contacts-form");

contactsForm.addEventListener("submit", function (event) {
    event.preventDefault();

    let data = {
        id: contactsForm.querySelector('input[name="id"]').value,
        contact: contactsForm.querySelector('input[name="contact"]').value,
        description: contactsForm.querySelector('input[name="description"]').value,
    };

    fetchSave(data);
});

Array.from(document.getElementById("contacts").children).forEach((contact) => {
    addEditDeleteEventListenersToContact(contact);
});

function addEditDeleteEventListenersToContact(contact) {
    let contactId = extractEntityId(contact.id);

    document.getElementById("edit-contact-" + contactId).addEventListener("click", function (event) {
        event.preventDefault();
        contactsForm.querySelector('input[name="id"]').value = contactId;
        contactsForm.querySelector('input[name="contact"]').value = document.getElementById("contact-contact-" + contactId).textContent;
        contactsForm.querySelector('input[name="description"]').value = document.getElementById("description-contact-" + contactId).textContent;
    });

    document.getElementById("delete-contact-" + contactId).addEventListener("click", function (event) {
        event.preventDefault();
        fetchDelete(contact);
    });
}

function fetchSave(data) {
    fetch("http://localhost:8080/rest/contacts/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                contactsForm.reset();
                response.json().then((responseJson) => {
                    try {
                        document.getElementById("contact-contact-" + responseJson.id).innerText = responseJson.contact;
                        document.getElementById("description-contact-" + responseJson.id).innerText = responseJson.description;
                        console.log("Контакт был успешно изменён");
                    } catch (error) {
                        if (document.getElementById("empty-contacts-message")) {
                            removeEmptyContactsMessage();
                        }
                        newContact(responseJson, document.getElementById("contacts").childElementCount == 0);
                        console.log("Контакт был успешно добавлен");
                    }
                });
            } else {
                showModalError(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка при сохранении контакта", error);
        });
}

function fetchDelete(contact) {
    fetch("http://localhost:8080/rest/contacts/delete/" + extractEntityId(contact.id), {
        method: "POST",
    })
        .then((response) => {
            if (response.ok) {
                contact.remove();
                if (document.getElementById("contacts").childElementCount == 0) {
                    addEmptyContactsMessage();
                } else {
                    let contacts = Array.from(document.getElementById("contacts").children);
                    let lastContact = contacts[contacts.length - 1];
                    let lastContactHrs = lastContact.querySelectorAll("hr");
                    if (lastContactHrs.length == 1) {
                        lastContact.removeChild(lastContactHrs[0]);
                    }
                }
                console.log("Контакт был успешно удалён");
            } else {
                showModalError(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка при удалении контакта", error);
        });
}

function showModalError(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function newContact(responseJson, isFirst) {
    let contact = document.createElement("div");
    contact.id = "contact-" + responseJson.id;
    contact.className = "text-start mb-2";

    let contactDescription = document.createElement("h4");
    contactDescription.id = "description-contact-" + responseJson.id;
    contactDescription.className = "text-secondary";
    contactDescription.textContent = responseJson.description;

    let contactContact = document.createElement("p");
    contactContact.id = "contact-contact-" + responseJson.id;
    contactContact.className = "text-dark break-all mb-2";
    contactContact.textContent = responseJson.contact;

    let contactEdit = document.createElement("a");
    contactEdit.id = "edit-contact-" + responseJson.id;
    contactEdit.href = "#";
    contactEdit.className = "text-secondary me-1";
    contactEdit.textContent = "Изменить";

    let contactDelete = document.createElement("a");
    contactDelete.id = "delete-contact-" + responseJson.id;
    contactDelete.href = "#";
    contactDelete.className = "text-secondary ms-1";
    contactDelete.textContent = "Удалить";

    let contactHr = document.createElement("hr");

    contact.appendChild(contactDescription);
    contact.appendChild(contactContact);
    contact.appendChild(contactEdit);
    contact.appendChild(contactDelete);
    if (!isFirst) {
        contact.appendChild(contactHr);
    }

    let contacts = document.getElementById("contacts");
    contacts.insertBefore(contact, contacts.firstChild);

    addEditDeleteEventListenersToContact(contact);
}

function addEmptyContactsMessage() {
    let row = document.createElement("div");
    row.id = "empty-contacts-message";
    row.className = "row";

    let col = document.createElement("div");
    col.className = "col-12";

    let message = document.createElement("div");
    message.className = "text-secondary text-center fst-italic fs-1";
    message.textContent = "Список контактов пуст";

    col.appendChild(message);
    row.appendChild(col);
    document.getElementById("contacts-container").appendChild(row);
}

function removeEmptyContactsMessage() {
    document.getElementById("empty-contacts-message").remove();
}

function extractEntityId(elementId) {
    let parts = elementId.split("-");
    return parts[parts.length - 1];
}
