const patientId = document.querySelector("div[data-patient]").dataset.patient;

document.addEventListener("DOMContentLoaded", () => {
    fetchInit();
});

function fetchInit() {
    fetch(`http://localhost:8080/rest/patients/${patientId}/examinations/graphics/pulse-oximetry`, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((pulseOximetries) => {
                    beforeExerciseOxygenPercentagePulseOximetryTable(pulseOximetries);
                    beforeExercisePulseRatePulseOximetryTable(pulseOximetries);
                    afterExerciseOxygenPercentagePulseOximetryTable(pulseOximetries);
                    afterExercisePulseRatePulseOximetryTable(pulseOximetries);
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function beforeExerciseOxygenPercentagePulseOximetryTable(pulseOximetries) {
    new Chart(document.getElementById("oxygenPercentageBefore"), {
        type: "line",
        data: {
            labels: pulseOximetryDate(pulseOximetryBeforeExercise(pulseOximetries)),
            datasets: [
                {
                    label: "Содержание кислорода в крови (%)",
                    data: pulseOximetryOxygenPercentage(pulseOximetryBeforeExercise(pulseOximetries)),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function beforeExercisePulseRatePulseOximetryTable(pulseOximetries) {
    new Chart(document.getElementById("pulseRateBefore"), {
        type: "line",
        data: {
            labels: pulseOximetryDate(pulseOximetryBeforeExercise(pulseOximetries)),
            datasets: [
                {
                    label: "Пульс (уд/мин)",
                    data: pulseOximetryPulseRate(pulseOximetryBeforeExercise(pulseOximetries)),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function afterExerciseOxygenPercentagePulseOximetryTable(pulseOximetries) {
    new Chart(document.getElementById("oxygenPercentageAfter"), {
        type: "line",
        data: {
            labels: pulseOximetryDate(pulseOximetryAfterExercise(pulseOximetries)),
            datasets: [
                {
                    label: "Содержание кислорода в крови (%)",
                    data: pulseOximetryOxygenPercentage(pulseOximetryAfterExercise(pulseOximetries)),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function afterExercisePulseRatePulseOximetryTable(pulseOximetries) {
    new Chart(document.getElementById("pulseRateAfter"), {
        type: "line",
        data: {
            labels: pulseOximetryDate(pulseOximetryAfterExercise(pulseOximetries)),
            datasets: [
                {
                    label: "Пульс (уд/мин)",
                    data: pulseOximetryPulseRate(pulseOximetryAfterExercise(pulseOximetries)),
                    borderWidth: 1,
                    tension: 0.1,
                },
            ],
        },
    });
}

function pulseOximetryBeforeExercise(pulseOximetries) {
    return pulseOximetries.filter((pulseOximetry) => pulseOximetry.afterExercise == false);
}

function pulseOximetryAfterExercise(pulseOximetries) {
    return pulseOximetries.filter((pulseOximetry) => pulseOximetry.afterExercise == true);
}

function pulseOximetryOxygenPercentage(pulseOximetries) {
    return pulseOximetries.map((pulseOximetry) => pulseOximetry.oxygenPercentage);
}

function pulseOximetryPulseRate(pulseOximetries) {
    return pulseOximetries.map((pulseOximetry) => pulseOximetry.pulseRate);
}

function pulseOximetryDate(pulseOximetries) {
    return pulseOximetries.map((pulseOximetry) => pulseOximetry.formattedDate);
}
