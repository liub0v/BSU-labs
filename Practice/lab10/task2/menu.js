const menuTable = document.getElementById('table');
const menu2020 = document.getElementById('2020');
const menuFilms = document.getElementById('films');
const menuForm=document.getElementById('form');

menuTable.onclick = function () {
    window.open("table.html", "table");
}

menu2020.onclick = function () {
    window.open("2020.html", "2020");
}

menuFilms.onclick = function () {
    window.open("films.html", "films");
}
menuForm.onclick = function () {
    window.open("form.html", "form");
}
