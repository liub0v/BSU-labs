const menuForm = document.getElementById('task1');
const menuMap = document.getElementById('task2');
const menuTable = document.getElementById('task3');


menuForm.onclick = function () {
    window.open("form.html", "task1");
}

menuMap.onclick = function () {
    window.open("table.html", "task2");
}

menuTable.onclick = function () {
    window.open("map.html", "task3");
}