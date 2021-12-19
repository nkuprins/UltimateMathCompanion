
let selectedId;

const selectRow = function (id) {
    document.getElementById(selectedId).className = 'text-center';
    document.getElementById(id).className = 'bg-info text-center';
    selectedId = id;
}

const redirectWithId = function (link) {
    window.location.href = link + "?id=" + selectedId;
}

window.onload = function () {
    const firstRow = document.getElementById("expressions-list").querySelectorAll("tr")[1];
    firstRow.className = 'bg-info text-center';
    selectedId = firstRow.id;
};