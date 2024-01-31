let map;

document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/rest/schedules/get/for/" + document.getElementById("patient-id").value, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    map = new Map(Object.entries(responseJson));
                });
            } else {
                console.error("На сервере произошла ошибка, для которой здесь не предусмотрено никаких действий");
            }
        })
        .catch((error) => {
            console.error("Произошла ошибка, для которой не предусмотрено никаких действий", error);
        });
});

document.querySelectorAll("a[data-key]").forEach((action) => {
    action.addEventListener("click", function (event) {
        event.preventDefault();

        /*let data = {
            patientId: document.getElementById("patient-id").value,
            indicatorType: extractIndicatorType(edit.id),
        };*/

        let data;

        schedule = map.get(action.dataset.key);

        if (schedule.id == null) {
            document.getElementById("delete").classList.add("visually-hidden");
            document.getElementById("save").addEventListener("click", (event) => {
                event.preventDefault();
                data = null;
                fetchAdd(data);
            });
        } else {
            document.getElementById("delete").addEventListener("click", (event) => {
                event.preventDefault();
                id = null;
                fetchDelete(id);
            });
            document.getElementById("save").addEventListener("click", (event) => {
                event.preventDefault();
                data = null;
                fetchEdit(data);
            });
        }

        //fetchCheck(data);
    });
});

function fetchAdd(data) {}

function fetchEdit(data) {}

function fetchDelete(id) {}

/*function fetchCheck(data) {
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
}*/

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
