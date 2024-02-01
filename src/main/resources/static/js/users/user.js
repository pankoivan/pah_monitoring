document.getElementById("doctor-editing").addEventListener("click", function (event) {
    event.preventDefault();
    new bootstrap.Modal(document.getElementById("doctor-editing-modal")).show();
});

document.getElementById("change-password").addEventListener("change", (check) => {
    if (check.target.checked) {
        document.getElementById("password-block").classList.remove("visually-hidden");
    } else {
        document.getElementById("password-block").classList.add("visually-hidden");
    }
});
