document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    checked(params.get("period"));
    showFilterOnStart();
});

document.getElementById("filter-apply-button").addEventListener("click", () => {
    applyFilter();
});

document.getElementById("filter-clear-button").addEventListener("click", () => {
    clearFilter();
});

function showFilterOnStart() {
    const params = new URLSearchParams(window.location.search);
    if (params.get("period")) {
        document.getElementById("filter").classList.add("show");
    }
}

function checked(id) {
    if (document.getElementById(id)) {
        document.getElementById(id).setAttribute("checked", "checked");
        document.getElementById(id).checked = "checked";
    }
}

function applyFilter() {
    const url = new URL(window.location.href);
    if (document.querySelector('input[name="period"]:checked')) {
        url.searchParams.set("period", document.querySelector('input[name="period"]:checked').id);
    }
    window.location.href = url;
}

function clearFilter() {
    const url = new URL(window.location.href);
    url.searchParams.delete("period");
    window.location.href = url;
}
