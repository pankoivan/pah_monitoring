const form = document.getElementById("contactForm");

const id = form.querySelector('input[name="id"]').value;
console.log("---id: " + id);

contactForm.addEventListener("submit", function (event) {
    event.preventDefault();

    const id = form.querySelector('input[name="id"]').value;
    console.log("---id: " + id);
    const contact = form.querySelector('input[name="contact"]').value;
    const description = form.querySelector('input[name="description"]').value;

    const data = {
        id: id,
        contact: contact,
        description: description,
    };

    fetch("http://localhost:8080/rest/contacts/save", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                console.log("Данные успешно отправлены");
                form.reset();
                response.json().then((responseJson) => {
                    changedContact = document.getElementById(
                        "contact" + responseJson.id
                    );
                    changedDescription = document.getElementById(
                        "description" + responseJson.id
                    );
                    changedContact.innerText = responseJson.contact;
                    changedDescription.innerText = responseJson.description;
                });
            } else {
                response.json().then((responseJson) => {
                    errorModalText = document.getElementById("errorModalText");
                    errorModalText.innerText = responseJson.errorDescription;
                    var myModal = new bootstrap.Modal(
                        document.getElementById("errorModal")
                    );
                    myModal.show();
                });
            }
        })
        .catch((error) => {
            console.error("Произошла ошибка при отправке данных:", error);
        });
});

document
    .getElementById("contactsDiv")
    .querySelectorAll("div")
    .forEach((element) => {
        editLink = document.getElementById("edit" + element.id);
        deleteLink = document.getElementById("delete" + element.id);

        console.log("editLinkId: " + editLink.id);
        console.log("deleteLinkId: " + deleteLink.id);

        editLink.addEventListener("click", function (event) {
            event.preventDefault();
            contact = document.getElementById("contact" + element.id);
            description = document.getElementById("description" + element.id);
            form.querySelector('input[name="contact"]').value =
                contact.textContent;
            form.querySelector('input[name="description"]').value =
                description.textContent;
            form.querySelector('input[name="id"]').value = element.id;
        });

        deleteLink.addEventListener("click", function (event) {
            event.preventDefault();
            //element.remove();

            fetch("http://localhost:8080/rest/contacts/delete/" + element.id, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                //body: JSON.stringify(data),
            })
                .then((response) => {
                    if (response.ok) {
                        element.remove();
                        console.log("Данные успешно удалены");
                    } else {
                        response.json().then((responseJson) => {
                            errorModalText =
                                document.getElementById("errorModalText");
                            errorModalText.innerText =
                                responseJson.errorDescription;
                            var myModal = new bootstrap.Modal(
                                document.getElementById("errorModal")
                            );
                            myModal.show();
                        });
                    }
                })
                .catch((error) => {
                    console.error(
                        "Произошла ошибка при отправке данных:",
                        error
                    );
                });
        });
    });

/*for (var i = 0; i < contacts.length; i++) {
    editLink = document.getElementById("edit" + contacts[i].id);
    deleteLink = document.getElementById("delete" + contacts[i].id);
    console.log("editLinkId: " + editLink.id);
    console.log("deleteLinkId: " + deleteLink.id);
}*/

/*for (const contact in contacts) {
    editLink = contact.getElementById("edit" + contact.id);
    deleteLink = contact.getElementById("delete" + contact.id);
    console.log("editLinkId: " + editLink.id);
    console.log("deleteLinkId: " + deleteLink.id);

    deleteLink.addEventListener("click", function (event) {
        event.preventDefault();
        console.log("editLinkId: " + editLink.id);
        console.log("deleteLinkId: " + deleteLink.id);
    });
}
*/
