const patientId = document.querySelector("div[data-patient]").dataset.patient;

new gridjs.Grid({
    columns: ["Верхнее", "Нижнее", "После нагрузки"],
    server: {
        url: "http://localhost:8080/rest/indicators/tables/pressure/for/" + patientId,
        then: (pressures) => pressures.map((pressure) => [pressure.upper, pressure.lower, pressure.afterExercise]),
    },
}).render(document.getElementById("table"));
