const scheduleEditingForm = document.getElementById("schedule-editing-form");

let schedules;

let errorModal;
let successModal;
let scheduleEditingModal;

document.addEventListener("DOMContentLoaded", () => {
    schedulesInit();
    schedulesEventListenersInit();
    modalsInit();
});

function schedulesInit() {
    fetch("http://localhost:8080/rest/schedules/get/for/" + patientId(), {
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
}

function schedulesEventListenersInit() {
    document.querySelectorAll("a[data-edit]").forEach((edit) => {
        addScheduleObjectEventListeners(edit);
    });
}

function modalsInit() {
    errorModal = new bootstrap.Modal(document.getElementById("error-modal"));
    successModal = new bootstrap.Modal(document.getElementById("success-modal"));
    scheduleEditingModal = new bootstrap.Modal(document.getElementById("schedule-editing-modal"));
}

function addScheduleObjectEventListeners(edit) {
    edit.addEventListener("click", (event) => {
        event.preventDefault();

        refreshScheduleEditingForm();

        let schedule = schedules.get(edit.dataset.edit);

        scheduleEditingForm.querySelector('input[name="indicator"]').value = schedule.indicatorTypeAlias;
        scheduleEditingForm.querySelector('input[name="schedule"]').value = schedule.schedule == null ? "" : schedule.schedule;

        if (schedule.id == null) {
            document.getElementById("delete").classList.add("visually-hidden");
            document.getElementById("save").addEventListener("click", (event) => {
                event.preventDefault();
                let data = {
                    patientId: schedule.patientId,
                    indicatorType: schedule.indicatorType,
                    schedule: scheduleEditingForm.querySelector('input[name="schedule"]').value,
                };
                fetchAdd(data);
            });
        } else {
            document.getElementById("delete").addEventListener("click", (event) => {
                event.preventDefault();
                let id = schedule.id;
                fetchDelete(id, schedule.indicatorType);
            });
            document.getElementById("save").addEventListener("click", (event) => {
                event.preventDefault();
                let data = {
                    id: schedule.id,
                    schedule: scheduleEditingForm.querySelector('input[name="schedule"]').value,
                };
                fetchEdit(data);
            });
        }

        showScheduleEditingModal();
    });
}

function fetchAdd(data) {
    fetch("http://localhost:8080/rest/schedules/add", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
    })
        .then((response) => {
            closeScheduleEditingModal();
            if (response.ok) {
                response.json().then((responseJson) => {
                    refreshScheduleObject(responseJson.indicatorType, responseJson);
                    addScheduleObjectEventListeners(refreshScheduleObjectEventListeners(responseJson.indicatorType));
                    refreshScheduleHtml(responseJson.indicatorType, responseJson.schedule);
                });
                showSuccessModal("Расписание было успешно добавлено");
            } else {
                showErrorModal(response);
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
            closeScheduleEditingModal();
            if (response.ok) {
                response.json().then((responseJson) => {
                    refreshScheduleObject(responseJson.indicatorType, responseJson);
                    addScheduleObjectEventListeners(refreshScheduleObjectEventListeners(responseJson.indicatorType));
                    refreshScheduleHtml(responseJson.indicatorType, responseJson.schedule);
                });
                showSuccessModal("Расписание было успешно изменено");
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function fetchDelete(id, indicatorType) {
    fetch("http://localhost:8080/rest/schedules/delete/" + id, {
        method: "POST",
    })
        .then((response) => {
            closeScheduleEditingModal();
            if (response.ok) {
                refreshScheduleObject(indicatorType, null);
                addScheduleObjectEventListeners(refreshScheduleObjectEventListeners(indicatorType));
                refreshScheduleHtml(indicatorType, "Отсутствует");
                showSuccessModal("Расписание было успешно удалено");
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showScheduleEditingModal() {
    scheduleEditingModal.show();
}

function closeScheduleEditingModal() {
    document.getElementById("schedule-editing-modal-close-1").click();
}

function showSuccessModal(message) {
    document.getElementById("success-modal-text").innerText = message;
    successModal.show();
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        errorModal.show();
    });
}

document.getElementById("error-modal-close-1").addEventListener("click", () => {
    showScheduleEditingModal();
});

document.getElementById("error-modal-close-2").addEventListener("click", () => {
    showScheduleEditingModal();
});

function refreshScheduleObject(key, schedule) {
    if (schedule != null) {
        schedules.set(key, schedule);
    } else {
        schedules.get(key).id = null;
        schedules.get(key).schedule = null;
    }
}

function refreshScheduleObjectEventListeners(key) {
    edit = document.querySelector(`a[data-edit="${key}"]`);
    edit.outerHTML = edit.outerHTML;
    return document.querySelector(`a[data-edit="${key}"]`);
}

function refreshScheduleHtml(key, schedule) {
    document.querySelector(`span[data-schedule="${key}"]`).innerText = schedule;
}

function refreshScheduleEditingForm() {
    document.getElementById("delete").classList.remove("visually-hidden");
    let saveButton = document.getElementById("save");
    saveButton.outerHTML = saveButton.outerHTML;
    let deleteButton = document.getElementById("delete");
    deleteButton.outerHTML = deleteButton.outerHTML;
}

function patientId() {
    return window.location.pathname.split("/")[2];
}
