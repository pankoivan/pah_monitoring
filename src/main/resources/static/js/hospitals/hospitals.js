document.getElementById("filter-button").addEventListener("click", () => {
    console.log(new URLSearchParams(window.location.search).get("byb"));
});
