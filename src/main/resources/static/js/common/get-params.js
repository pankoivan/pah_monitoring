document.addEventListener("DOMContentLoaded", () => {
    const params = new URLSearchParams(window.location.search);
    checked(params.get("filtration"));
    checked(params.get("sorting"));
    searched(params.get("searching"));
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
    if (params.size > 1 || (params.size == 1 && !params.get("page"))) {
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
    if (url.searchParams.get("page")) {
        url.searchParams.set("page", url.searchParams.get("page"));
    }
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
    window.location.href = url;
}

function clearFilter() {
    const params = new URLSearchParams(window.location.search);
    let url = window.location.href.split("?")[0];
    if (params.get("page")) {
        url = url + `?page=${params.get("page")}`;
    }
    window.location.href = url;
}
