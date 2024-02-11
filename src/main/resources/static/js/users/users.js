const patientId = new URLSearchParams(window.location.search).get("patientId");

const successModal = document.getElementById("success-modal");

document.querySelectorAll(".btn.btn-sm.btn-success").forEach((button) => {
    button.addEventListener("click", () => {
        if (!button.disabled) {
            fetchAssignToDoctor(patientId, button.dataset.doctor);
        }
    });
});

document.querySelectorAll(".btn.btn-sm.btn-danger").forEach((button) => {
    button.addEventListener("click", () => {
        if (!button.disabled) {
            fetchRemoveFromDoctor(patientId);
        }
    });
});

function fetchAssignToDoctor(patientId, doctorId) {
    fetch("http://localhost:8080/rest/patient-doctor-connection/assign", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
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
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showSuccessModalForDoctorAssigning(response) {
    response.json().then((responseJson) => {
        fillSuccessModalTextForDoctorAssigning(responseJson);
        new bootstrap.Modal(successModal).show();
    });
}

function fillSuccessModalTextForDoctorAssigning(responseJson) {
    let successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    let patient = document.createElement("span");
    patient.className = "fw-bold";
    patient.innerText = `${responseJson.patientFullName}`;

    let doctor = document.createElement("span");
    doctor.className = "fw-bold";
    doctor.innerText = `${responseJson.doctorFullName}`;

    let toPatient = document.createElement("a");
    toPatient.className = "href-success";
    toPatient.innerText = "профилю";
    toPatient.href = "/patients/" + patientId;

    successModalText.appendChild(document.createTextNode("Пациент "));
    successModalText.appendChild(patient);
    successModalText.appendChild(document.createTextNode(" был успешно закреплён за врачом "));
    successModalText.appendChild(doctor);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Вернуться к "));
    successModalText.appendChild(toPatient);
    successModalText.appendChild(document.createTextNode(" профилю пациента."));
}

function fetchRemoveFromDoctor(patientId) {
    fetch("http://localhost:8080/rest/patient-doctor-connection/remove/" + patientId, {
        method: "POST",
        headers: {
            Accept: "application/json",
        },
    })
        .then((response) => {
            if (response.ok) {
                showSuccessModalForDoctorRemoval(response);
            } else {
                console.error("Ошибка сервера");
            }
        })
        .catch((error) => {
            console.error("Ошибка запроса", error);
        });
}

function showSuccessModalForDoctorRemoval(response) {
    response.json().then((responseJson) => {
        fillSuccessModalTextForDoctorRemoval(responseJson);
        new bootstrap.Modal(successModal).show();
    });
}

function fillSuccessModalTextForDoctorRemoval(responseJson) {
    let successModalText = document.getElementById("success-modal-text");

    successModalText.textContent = "";

    let patient = document.createElement("span");
    patient.className = "fw-bold";
    patient.innerText = `${responseJson.patientFullName}`;

    let doctor = document.createElement("span");
    doctor.className = "fw-bold";
    doctor.innerText = `${responseJson.doctorFullName}`;

    let toPatient = document.createElement("a");
    toPatient.className = "href-success";
    toPatient.innerText = "профилю";
    toPatient.href = "/patients/" + patientId;

    successModalText.appendChild(document.createTextNode("Пациент "));
    successModalText.appendChild(patient);
    successModalText.appendChild(document.createTextNode(" был успешно откреплён от врача "));
    successModalText.appendChild(doctor);
    successModalText.appendChild(document.createTextNode("."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Теперь этот пациент временно находится без врача, а значит, не может сдавать показатели. Необходимо как можно скорее назначить ему нового врача."));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createElement("br"));
    successModalText.appendChild(document.createTextNode("Вернуться к "));
    successModalText.appendChild(toPatient);
    successModalText.appendChild(document.createTextNode(" профилю пациента."));
}

if (successModal) {
    successModal.addEventListener("hidden.bs.modal", () => {
        window.location.href = "/patients/" + patientId;
    });
}
