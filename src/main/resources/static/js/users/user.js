document.getElementById("doctor-editing").addEventListener("click", function (event) {
    event.preventDefault();
    new bootstrap.Modal(document.getElementById("doctor-editing-modal")).show();
});
