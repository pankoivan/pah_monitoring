const contactsForm = document.getElementById("contacts-form");

contactsForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
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
    const contactId = extractEntityId(contact.id);

    document.getElementById(`edit-contact-${contactId}`).addEventListener("click", () => {
        contactsForm.querySelector('input[name="id"]').value = contactId;
        contactsForm.querySelector('input[name="contact"]').value = document.getElementById(`contact-contact-${contactId}`).textContent;
        contactsForm.querySelector('input[name="description"]').value = document.getElementById(`description-contact-${contactId}`).textContent;
    });

    document.getElementById(`delete-contact-${contactId}`).addEventListener("click", (event) => {
        event.preventDefault();
        fetchDelete(contact);
    });
}

function fetchSave(data) {
    fetch("http://localhost:8080/rest/contacts/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                contactsForm.reset();
                response.json().then((responseJson) => {
                    try {
                        document.getElementById(`contact-contact-${responseJson.id}`).innerText = responseJson.contact;
                        document.getElementById(`description-contact-${responseJson.id}`).innerText = responseJson.description;
                        showSuccessModal("Контакт был успешно изменён");
                    } catch (error) {
                        if (document.getElementById("empty-contacts-message")) {
                            removeEmptyContactsMessage();
                        }
                        newContact(responseJson, document.getElementById("contacts").childElementCount == 0);
                        showSuccessModal("Контакт был успешно добавлен");
                    }
                });
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка при сохранении контакта", error);
        });
}

function fetchDelete(contact) {
    fetch(`http://localhost:8080/rest/contacts/delete/${extractEntityId(contact.id)}`, {
        method: "POST",
    })
        .then((response) => {
            if (response.ok) {
                contact.remove();
                if (document.getElementById("contacts").childElementCount == 0) {
                    addEmptyContactsMessage();
                } else {
                    const contacts = Array.from(document.getElementById("contacts").children);
                    const lastContact = contacts[contacts.length - 1];
                    const lastContactHrs = lastContact.querySelectorAll("hr");
                    if (lastContactHrs.length == 1) {
                        lastContact.removeChild(lastContactHrs[0]);
                    }
                }
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка при удалении контакта", error);
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

function newContact(responseJson, isFirst) {
    const contact = document.createElement("div");
    contact.id = `contact-${responseJson.id}`;
    contact.className = "text-start mb-2";

    const contactDescription = document.createElement("span");
    contactDescription.id = `description-contact-${responseJson.id}`;
    contactDescription.textContent = responseJson.description;

    const contactDescriptionBlock = document.createElement("h4");
    contactDescriptionBlock.appendChild(contactDescription);
    contactDescriptionBlock.appendChild(editDeleteLink("edit", responseJson.id));
    contactDescriptionBlock.appendChild(editDeleteLink("delete", responseJson.id));

    const contactContact = document.createElement("p");
    contactContact.id = `contact-contact-${responseJson.id}`;
    contactContact.className = "text-dark text-break mb-2";
    contactContact.textContent = responseJson.contact;

    const contactHr = document.createElement("hr");

    contact.appendChild(contactDescriptionBlock);
    contact.appendChild(contactContact);
    if (!isFirst) {
        contact.appendChild(contactHr);
    }

    const contacts = document.getElementById("contacts");
    contacts.insertBefore(contact, contacts.firstChild);

    addEditDeleteEventListenersToContact(contact);
}

function editDeleteLink(which, id) {
    const img = document.createElement("img");
    img.width = 24;
    img.src = "/svg/msg" + which + ".svg";

    const a = document.createElement("a");
    a.id = which + "-contact-" + id;
    a.href = "#";
    a.className = "text-decoration-none";

    a.appendChild(img);

    return a;
}

function addEmptyContactsMessage() {
    const row = document.createElement("div");
    row.className = "row";
    row.id = "empty-contacts-message";

    const col = document.createElement("div");
    col.className = "col-12";

    const message = document.createElement("div");
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
    return elementId.split("-").pop();
}
