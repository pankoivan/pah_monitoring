const overallHealthForm = document.getElementById("overall-health-form");

overallHealthForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        breathlessness: checked(overallHealthForm.querySelector('input[name="breathlessness"]:checked')),
        fatigue: checked(overallHealthForm.querySelector('input[name="fatigue"]:checked')),
        restFeeling: checked(overallHealthForm.querySelector('input[name="restFeeling"]:checked')),
        drowsiness: checked(overallHealthForm.querySelector('input[name="drowsiness"]:checked')),
        concentration: checked(overallHealthForm.querySelector('input[name="concentration"]:checked')),
        weakness: checked(overallHealthForm.querySelector('input[name="weakness"]:checked')),
        appetite: checked(overallHealthForm.querySelector('input[name="appetite"]:checked')),
        coldExtremities: checked(overallHealthForm.querySelector('input[name="coldExtremities"]:checked')),
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/indicators/add/overall-health", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                overallHealthForm.reset();
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
    document.getElementById("success-modal-text").innerText = 'Показатель "Общее самочувствие" был успешно отправлен';
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
