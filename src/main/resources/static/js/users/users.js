const patientId = new URLSearchParams(window.location.search).get("patientId");

document.querySelectorAll("a[data-doctor-assign]").forEach((a) => {
    a.addEventListener("click", (event) => {
        event.preventDefault();
        fetchAssignToDoctor(patientId, a.dataset.doctorAssign);
    });
});

document.querySelectorAll("a[data-doctor-remove]").forEach((a) => {
    a.addEventListener("click", (event) => {
        event.preventDefault();
        fetchRemoveFromDoctor(patientId);
    });
});

function fetchAssignToDoctor(patientId, doctorId) {
    fetch("http://localhost:8080/rest/patient-doctor-connection/assign", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
            "X-CSRF-TOKEN": document.getElementById("doctor-assigning-token").querySelector('input[name="_csrf"]').value,
        },
        body: JSON.stringify({
            patientId: patientId,
            doctorId: doctorId,
        }),
    })
        .then((response) => {
            if (response.ok) {
                showSuccessModalForDoctorAssigning(response);
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function fetchRemoveFromDoctor(patientId) {
    fetch(`http://localhost:8080/rest/patient-doctor-connection/remove/${patientId}`, {
        method: "POST",
        headers: {
            Accept: "application/json",
            "X-CSRF-TOKEN": document.getElementById("doctor-removal-token").querySelector('input[name="_csrf"]').value,
        },
    })
        .then((response) => {
            if (response.ok) {
                showSuccessModalForDoctorRemoval(response);
            } else {
                showErrorModal(response);
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showSuccessModalForDoctorAssigning(response) {
    response.json().then((responseJson) => {
        fillSuccessModalTextForDoctorAssigning(responseJson);
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function fillSuccessModalTextForDoctorAssigning(responseJson) {
    const successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    const patient = document.createElement("span");
    patient.className = "fw-bold";
    patient.textContent = `${responseJson.patientFullName}`;

    const doctor = document.createElement("span");
    doctor.className = "fw-bold";
    doctor.textContent = `${responseJson.doctorFullName}`;

    const toPatient = document.createElement("a");
    toPatient.className = "href-success";
    toPatient.textContent = "профилю";
    toPatient.href = `/patients/${patientId}`;

    successModalText.appendChild(document.createTextNode("Пациент "));
    successModalText.appendChild(patient);
    successModalText.appendChild(document.createTextNode(" был успешно закреплён за врачом "));
    successModalText.appendChild(doctor);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Вернуться к "));
    successModalText.appendChild(toPatient);
    successModalText.appendChild(document.createTextNode(" пациента."));
}

function showSuccessModalForDoctorRemoval(response) {
    response.json().then((responseJson) => {
        fillSuccessModalTextForDoctorRemoval(responseJson);
        new bootstrap.Modal(document.getElementById("success-modal")).show();
    });
}

function fillSuccessModalTextForDoctorRemoval(responseJson) {
    const successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    const patient = document.createElement("span");
    patient.className = "fw-bold";
    patient.textContent = `${responseJson.patientFullName}`;

    const doctor = document.createElement("span");
    doctor.className = "fw-bold";
    doctor.textContent = `${responseJson.doctorFullName}`;

    const toPatient = document.createElement("a");
    toPatient.className = "href-success";
    toPatient.textContent = "профилю";
    toPatient.href = `/patients/${patientId}`;

    successModalText.appendChild(document.createTextNode("Пациент "));
    successModalText.appendChild(patient);
    successModalText.appendChild(document.createTextNode(" был успешно откреплён от врача "));
    successModalText.appendChild(doctor);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Теперь этот пациент временно находится без врача, а значит, не может отправлять показатели. Необходимо как можно скорее назначить ему нового врача."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Вернуться к "));
    successModalText.appendChild(toPatient);
    successModalText.appendChild(document.createTextNode(" пациента."));
}

if (document.getElementById("success-modal")) {
    document.getElementById("success-modal").addEventListener("hidden.bs.modal", () => {
        window.location.href = `/patients/${patientId}`;
    });
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}
