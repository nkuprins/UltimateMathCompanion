function highlightRow(row) {
    const rows = document.querySelectorAll("tr");
    rows.forEach(el => el.className='')
    row.className  = 'bg-info';
}
function highlightFirstRow() {
    const rows = document.querySelectorAll("tr");
    rows[1].className = 'bg-info';
}
window.onload = function() {
    highlightFirstRow();
};