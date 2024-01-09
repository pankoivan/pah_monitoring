document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

    const csrfToken = document.querySelector('input[name="_csrf"]').value;

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Предотвращаем стандартное поведение отправки формы

        // Получаем значения из input-полей
        /*const name = document.querySelector('input[name="name"]').value;
        const lastname = document.querySelector('input[name="lastname"]').value;
        const patronymic = document.querySelector(
            'input[name="patronymic"]'
        ).value;*/
        const expirationDateEnum = document.querySelector(
            'select[name="expirationDateEnum"]'
        ).value;

        // Формируем JSON из полученных значений
        const data = {
            /*name: name,
            lastname: lastname,
            patronymic: patronymic,*/
            expirationDateEnum: expirationDateEnum,
        };

        // Отправляем данные методом POST на сервер
        fetch("http://localhost:8080/rest/code-generation", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-CSRF-TOKEN": csrfToken,
            },
            body: JSON.stringify(data),
        })
            .then((response) => {
                // Обрабатываем ответ от сервера
                if (response.ok) {
                    console.log("Данные успешно отправлены");
                } else {
                    console.error("Произошла ошибка при отправке данных");
                }
            })
            .catch((error) => {
                console.error("Произошла ошибка при отправке данных:", error);
            });
    });
});
