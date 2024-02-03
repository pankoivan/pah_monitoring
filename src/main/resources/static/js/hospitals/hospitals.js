document.getElementById("filter-button").addEventListener("click", () => {
    let filtration = console.log(new URLSearchParams(window.location.search).get("byb"));
});

document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    let filtration = params.get("filtration");
    let sorting = params.get("sorting");
    let searching = params.get("searching");

    checked(filtration);
    checked(sorting);
    searched(searching);
});

function checked(id) {
    if (document.getElementById(id)) {
        document.getElementById(id).setAttribute("checked", "checked");
        document.getElementById(id).checked = "checked";
    }
}

function searched(searching) {
    document.querySelector('input[name="searching"]').value = searching;
    document.querySelector('input[name="searching"]').setAttribute("value", searching);
}
