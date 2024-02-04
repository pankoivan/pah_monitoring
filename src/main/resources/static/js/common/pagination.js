let currentPage;

document.addEventListener("DOMContentLoaded", () => {
    if (new URLSearchParams(window.location.search).get("page") == null) {
        currentPage = 1;
    } else {
        currentPage = Number(new URLSearchParams(window.location.search).get("page"));
    }
});

document.getElementById("first-page").addEventListener("click", () => {
    toPage(1);
});

document.getElementById("prev-page").addEventListener("click", () => {
    toPage(currentPage - 1);
});

document.getElementById("next-page").addEventListener("click", () => {
    toPage(currentPage + 1);
});

document.getElementById("last-page").addEventListener("click", () => {
    toPage(Number(document.querySelector("li[data-count]").dataset.count));
});

function toPage(page) {
    const url = new URL(window.location.href);
    url.searchParams.set("page", page);
    window.location.href = url;
}
