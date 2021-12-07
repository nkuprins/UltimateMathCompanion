
let selectedId;

function selectRow(row, id) {
    const rows = document.getElementById("expressions-list").querySelectorAll("tr");
    rows.forEach(el => el.className='text-center')
    row.className  = 'bg-info text-center';
    selectedId = id;
}

function selectFirstRow() {
    const rows = document.getElementById("expressions-list").querySelectorAll("tr");
    rows[1].className = 'bg-info text-center';
}

function redirectToUpdate() {
    window.location.href = "/updateExpression?id=" + selectedId;
}

function redirectToDelete() {
    window.location.href = "/deleteExpression?id=" + selectedId;
}

window.onload = function() {
    selectFirstRow();
};