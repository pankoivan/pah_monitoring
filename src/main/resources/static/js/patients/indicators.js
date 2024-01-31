document.querySelectorAll('a[id^="edit-"]').forEach((edit) => {
    edit.addEventListener("click", function (event) {
        event.preventDefault();

        let data = {
            patientId: document.getElementById("patient-id").value,
            indicatorType: extractIndicatorType(edit.id),
        };

        fetchCheck(data);
    });
});

function fetchCheck(data) {
    fetch("http://localhost:8080/rest/schedules/check", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    fillAndShowScheduleEditingModal(responseJson);
                });
            } else {
                console.error("На сервере произошла ошибка, для которой здесь не предусмотрено никаких действий");
            }
        })
        .catch((error) => {
            console.error("Произошла ошибка, для которой не предусмотрено никаких действий", error);
        });
}

function fillAndShowScheduleEditingModal(responseJson) {
    let indicator = document.getElementById("indicator");
    indicator.value = responseJson.indicatorTypeAlias;
    if (responseJson.schedule == null) {
        document.getElementById("delete").classList.add("visually-hidden");
    } else {
        document.getElementById("schedule").value = responseJson.schedule;
    }
    new bootstrap.Modal(document.getElementById("schedule-editing-modal")).show();
}

function extractIndicatorType(str) {
    return str.split("-").pop();
}
