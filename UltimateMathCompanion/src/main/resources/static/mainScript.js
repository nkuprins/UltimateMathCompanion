
function selectRow(row) {
    const rows = document.querySelectorAll("tr");
    rows.forEach(el => el.className='')
    row.className  = 'bg-info';
}

function selectFirstRow() {
    const rows = document.getElementById("expressions-list").querySelectorAll("tr");
    rows[1].className = 'bg-info';
}

window.onload = function() {
    selectFirstRow();
};