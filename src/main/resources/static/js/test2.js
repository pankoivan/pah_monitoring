const contactsForm = document.getElementById("contacts-form");

contactsForm.addEventListener("submit", function (event) {
    event.preventDefault();

    const data = {
        id: contactsForm.querySelector('input[name="id"]').value,
        contact: contactsForm.querySelector('input[name="contact"]').value,
        description: contactsForm.querySelector('input[name="description"]').value,
    };

    fetchSave(data);
});

document
    .getElementById("contacts")
    .querySelectorAll("div")
    .forEach((contact) => {
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
                        newContact(responseJson);
                        console.log("Контакт был успешно добавлен");
                    }
                });
            } else {
                showModalError(response);
            }
        })
        .catch((error) => {
            console.error("Контакт не был сохранён:", error);
        });
}

function fetchDelete(contact) {
    fetch("http://localhost:8080/rest/contacts/delete/" + extractEntityId(contact.id), {
        method: "POST",
    })
        .then((response) => {
            if (response.ok) {
                contact.remove();
                console.log("Контакт был успешно удалён");
            } else {
                showModalError(response);
            }
        })
        .catch((error) => {
            console.error("Контакт не был удалён:", error);
        });
}

function showModalError(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function newContact(responseJson) {
    contact = document.createElement("div");
    contact.id = "contact-" + responseJson.id;
    contact.className = "text-start mb-2";

    contactDescription = document.createElement("h4");
    contactDescription.id = "description-contact-" + responseJson.id;
    contactDescription.className = "text-secondary";
    contactDescription.textContent = responseJson.description;

    contactContact = document.createElement("p");
    contactContact.id = "contact-contact-" + responseJson.id;
    contactContact.className = "text-dark break-all mb-2";
    contactContact.textContent = responseJson.contact;

    contactEdit = document.createElement("a");
    contactEdit.id = "edit-contact-" + responseJson.id;
    contactEdit.href = "#";
    contactEdit.className = "text-secondary me-1";
    contactEdit.textContent = "Изменить";

    contactDelete = document.createElement("a");
    contactDelete.id = "delete-contact-" + responseJson.id;
    contactDelete.href = "#";
    contactDelete.className = "text-secondary ms-1";
    contactDelete.textContent = "Удалить";

    contactHr = document.createElement("hr");

    contact.appendChild(contactDescription);
    contact.appendChild(contactContact);
    contact.appendChild(contactEdit);
    contact.appendChild(contactDelete);
    contact.appendChild(contactHr);

    contacts = document.getElementById("contacts");
    contacts.insertBefore(contact, contacts.firstChild);

    addEditDeleteEventListenersToContact(contact);
}

function extractEntityId(elementId) {
    let parts = elementId.split("-");
    return parts[parts.length - 1];
}
