document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    checked(params.get("period"));
    showFilterOnStart();
});

document.getElementById("period-apply-button").addEventListener("click", () => {
    applyFilter();
});

document.getElementById("period-clear-button").addEventListener("click", () => {
    clearFilter();
});

function showFilterOnStart() {
    const params = new URLSearchParams(window.location.search);
    if (params.get("filtration") || params.get("sorting") || params.get("searching")) {
        document.getElementById("filter").classList.add("show");
    }
}

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

function applyFilter() {
    const url = new URL(window.location.href);
    if (document.querySelector('input[name="filtration"]:checked')) {
        url.searchParams.set("filtration", document.querySelector('input[name="filtration"]:checked').id);
    }
    if (document.querySelector('input[name="sorting"]:checked')) {
        url.searchParams.set("sorting", document.querySelector('input[name="sorting"]:checked').id);
    }
    if (document.querySelector('input[name="searching"]').value != "") {
        url.searchParams.set("searching", document.querySelector('input[name="searching"]').value);
    } else {
        url.searchParams.delete("searching");
    }
    url.searchParams.set("page", 1);
    window.location.href = url;
}

function clearFilter() {
    const url = new URL(window.location.href);
    url.searchParams.delete("filtration");
    url.searchParams.delete("sorting");
    url.searchParams.delete("searching");
    url.searchParams.set("page", 1);
    window.location.href = url;
}
