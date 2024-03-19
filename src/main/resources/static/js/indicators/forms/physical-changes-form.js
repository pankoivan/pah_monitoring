const physicalChangesForm = document.getElementById("physical-changes-form");

physicalChangesForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        abdominalEnlargement: checked(physicalChangesForm.querySelector('input[name="abdominalEnlargement"]:checked')),
        legsSwelling: checked(physicalChangesForm.querySelector('input[name="legsSwelling"]:checked')),
        vascularAsterisks: checked(physicalChangesForm.querySelector('input[name="vascularAsterisks"]:checked')),
        skinColor: checked(physicalChangesForm.querySelector('input[name="skinColor"]:checked')),
        fingersPhalanges: checked(physicalChangesForm.querySelector('input[name="fingersPhalanges"]:checked')),
        chest: checked(physicalChangesForm.querySelector('input[name="chest"]:checked')),
        neckVeins: checked(physicalChangesForm.querySelector('input[name="neckVeins"]:checked')),
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/indicators/add/physical-changes", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": physicalChangesForm.querySelector('input[name="_csrf"]').value,
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                physicalChangesForm.reset();
                showSuccessModal(response);
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showSuccessModal() {
    document.getElementById("success-modal-text").innerText = 'Показатель "Физические изменения" был успешно отправлен';
    new bootstrap.Modal(document.getElementById("success-modal")).show();
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}

function checked(checked) {
    return checked ? checked.value : checked;
}
