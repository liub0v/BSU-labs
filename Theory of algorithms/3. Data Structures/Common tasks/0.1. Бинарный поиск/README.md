# Задача 0.1. Бинарный поиск

Имя входного файла: стандартный ввод

Имя выходного файла: стандартный вывод

Ограничение по времени: 2 с

Ограничение по памяти: 256 МБ

Необходимо реализовать бинарный поиск на отсортированном в неубывающем порядке массиве чисел.
Использование готовых функций бинарного поиска из стандартных библиотек запрещается.

**Формат входных данных**

В первой строке записано число n (0 < n ≤ 3⋅10^5) — длина массива чисел. Во второй строке записано nn чисел через пробел.
В третьей строке записано число k (0 < k ≤ 3⋅10^5) запросов. В четвертой строке записано k чисел-запросов, разделённых пробелом.

**Формат выходных данных**
Для каждого числа-запроса x в отдельной строке выведите через пробел числа b, l и r, где b равно 1, если x присутствует в массиве, и 0 в противном случае, l — индекс первого элемента, большего либо равного x, r — индекс первого элемента, большего x.
