const requestedPage = new URLSearchParams(window.location.search).get("page");

const lastPage = Number(document.querySelector("li[data-count]").dataset.count);

const currentPage = requestedPage == null || requestedPage == "" ? 1 : Number(requestedPage);

/*if (lastPage < requestedPage) {
    toPage(1);
}*/

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
    toPage(lastPage);
});

function toPage(page) {
    const url = new URL(window.location.href);
    url.searchParams.set("page", page);
    window.location.href = url;
}
