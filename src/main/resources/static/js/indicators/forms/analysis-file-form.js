const whichFile = document.location.pathname.split("/").pop();

const fileForm = document.getElementById("file-form");

fileForm.addEventListener("submit", (event) => {
    event.preventDefault();

    const formData = new FormData();
    formData.append("file", fileForm.querySelector('input[name="file"]').files[0]);

    fetchAdd(formData);
});

function fetchAdd(formData) {
    fetch(`http://localhost:8080/rest/indicators/add/analysis-file/${whichFile}`, {
        method: "POST",
        body: formData,
    })
        .then((response) => {
            if (response.ok) {
                fileForm.reset();
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
    document.getElementById("success-modal-text").innerText = "Файл был успешно отправлен";
    new bootstrap.Modal(document.getElementById("success-modal")).show();
}

function showErrorModal(response) {
    response.json().then((responseJson) => {
        document.getElementById("error-modal-text").innerText = responseJson.errorDescription;
        new bootstrap.Modal(document.getElementById("error-modal")).show();
    });
}
