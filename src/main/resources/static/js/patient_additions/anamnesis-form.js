const anamnesisForm = document.getElementById("anamnesis-form");

anamnesisForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        heartDisease: checked(anamnesisForm.querySelector('input[name="heartDisease"]:checked')),
        lungDisease: checked(anamnesisForm.querySelector('input[name="lungDisease"]:checked')),
        relativesDiseases: checked(anamnesisForm.querySelector('input[name="relativesDiseases"]:checked')),
        bloodClotting: checked(anamnesisForm.querySelector('input[name="bloodClotting"]:checked')),
        diabetes: checked(anamnesisForm.querySelector('input[name="diabetes"]:checked')),
        height: anamnesisForm.querySelector('input[name="height"]').value,
        weight: anamnesisForm.querySelector('input[name="weight"]').value,
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/anamnesis/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
            "X-CSRF-TOKEN": anamnesisForm.querySelector('input[name="_csrf"]').value,
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                showSuccessModal(response);
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showSuccessModal(response) {
    response.json().then((responseJson) => {
        const modal = document.getElementById("success-modal");
        modal.addEventListener("hidden.bs.modal", () => {
            window.location.href = `/anamnesis/for/${responseJson.patientId}`;
        });
        fillSuccessModalText(responseJson);
        new bootstrap.Modal(modal).show();
    });
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function fillSuccessModalText(responseJson) {
    const successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    const toAnamnesis = document.createElement("a");
    toAnamnesis.className = "href-success";
    toAnamnesis.textContent = "анамнеза";
    toAnamnesis.href = `/anamnesis/for/${responseJson.patientId}`;

    successModalText.appendChild(document.createTextNode("Анамнез был успешно отправлен."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Перейти к просмотру "));
    successModalText.appendChild(toAnamnesis);
    successModalText.appendChild(document.createTextNode("."));
}

function checked(checked) {
    return checked ? checked.value : checked;
}
