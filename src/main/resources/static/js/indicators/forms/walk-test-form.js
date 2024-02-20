const walkTestForm = document.getElementById("walk-test-form");

walkTestForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const data = {
        oxygenSupport: checked(walkTestForm.querySelector('input[name="oxygenSupport"]:checked')),
        auxiliaryDevices: checked(walkTestForm.querySelector('input[name="auxiliaryDevices"]:checked')),
        distance: walkTestForm.querySelector('input[name="distance"]').value,
        numberOfStops: walkTestForm.querySelector('input[name="numberOfStops"]').value,
        breathlessness: checked(walkTestForm.querySelector('select[name="breathlessness"]')).value,
        pressureBefore: {
            upper: walkTestForm.querySelector('input[name="upperBefore"]').value,
            lower: walkTestForm.querySelector('input[name="lowerBefore"]').value,
        },
        pulseOximetryBefore: {
            oxygenPercentage: walkTestForm.querySelector('input[name="oxygenPercentageBefore"]').value,
            pulseRate: walkTestForm.querySelector('input[name="pulseRateBefore"]').value,
        },
        pressureAfter: {
            upper: walkTestForm.querySelector('input[name="upperAfter"]').value,
            lower: walkTestForm.querySelector('input[name="lowerAfter"]').value,
        },
        pulseOximetryAfter: {
            oxygenPercentage: walkTestForm.querySelector('input[name="oxygenPercentageAfter"]').value,
            pulseRate: walkTestForm.querySelector('input[name="pulseRateAfter"]').value,
        },
    };

    fetchAdd(data);
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/indicators/add/walk-test", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                pulseOximetryForm.reset();
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
    document.getElementById("success-modal-text").innerText = 'Показатель "Т6МХ" был успешно отправлен';
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
