let schedules;

document.addEventListener("DOMContentLoaded", () => {
    fetch("http://localhost:8080/rest/schedules/get/for/" + document.getElementById("patient-id").value, {
        method: "GET",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                response.json().then((responseJson) => {
                    schedules = new Map(Object.entries(responseJson));
                });
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
});

document.querySelectorAll("a[data-key]").forEach((action) => {
    action.addEventListener("click", (event) => {
        event.preventDefault();

        let schedule = schedules.get(action.dataset.key);

        if (schedule.id == null) {
            document.getElementById("delete").classList.add("visually-hidden");
            document.getElementById("save").addEventListener("click", (event) => {
                event.preventDefault();
                let data = {
                    patientId: schedule.patientId,
                    indicatorType: schedule.indicatorType,
                    schedule: schedule.schedule,
                };
                fetchAdd(data);
            });
        } else {
            document.getElementById("delete").addEventListener("click", (event) => {
                event.preventDefault();
                let id = schedule.id;
                fetchDelete(id);
            });
            document.getElementById("save").addEventListener("click", (event) => {
                event.preventDefault();
                let data = {
                    id: schedule.id,
                    schedule: schedule.schedule,
                };
                fetchEdit(data);
            });
        }

        new bootstrap.Modal(document.getElementById("schedule-editing-modal")).show();
    });
});

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/schedules/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                showModalSuccessForAdding(response);
            } else {
                showModalError(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function fetchEdit(data) {
    fetch("http://localhost:8080/rest/schedules/edit", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            if (response.ok) {
                showModalSuccessForEditing(response);
            } else {
                showModalError(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function fetchDelete(id) {
    fetch("http://localhost:8080/rest/schedules/delete/" + id, {
        method: "POST",
    })
        .then((response) => {
            if (response.ok) {
                showModalSuccessForDeleting(response);
            } else {
                showModalError(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showModalSuccessForAdding(response) {
    response.json().then(() => {
        document.getElementById("success-modal-text").innerText = "Расписание было успешно добавлено";
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function showModalSuccessForEditing(response) {
    response.json().then(() => {
        document.getElementById("success-modal-text").innerText = "Расписание было успешно заменено";
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function showModalSuccessForDeleting(response) {
    response.json().then(() => {
        document.getElementById("success-modal-text").innerText = "Расписание было успешно удалено";
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function showModalError(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}
