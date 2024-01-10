document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

    const csrfToken = document.querySelector('input[name="_csrf"]').value;

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Предотвращаем стандартное поведение отправки формы

        // Получаем значения из input-полей
        const contact = document.querySelector('input[name="contact"]').value;
        const description = document.querySelector(
            'input[name="description"]'
        ).value;

        // Формируем JSON из полученных значений
        const data = {
            contact: contact,
            description: description,
        };

        // Отправляем данные методом POST на сервер
        fetch("http://localhost:8080/rest/contacts", {
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
                    response.json().then((responseJson) => {
                        // Получаем экземпляр модального окна
                        var myModal = new bootstrap.Modal(
                            document.getElementById("errorModal")
                        );

                        // Показываем модальное окно
                        myModal.show();
                    });
                }
            })
            .catch((error) => {
                console.error("Произошла ошибка при отправке данных:", error);
            });
    });
});
