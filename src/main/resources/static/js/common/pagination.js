let currentPage;

document.addEventListener("DOMContentLoaded", () => {
    const page = new URLSearchParams(window.location.search).get("page");
    if (page == null || page == "") {
        currentPage = 1;
    } else {
        currentPage = Number(page);
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
