# patch-visualizer

Требования к программе:

Реализовать программу визуализации дельты (изменений) между двумя версиями файлов. Изменения должны визуализироваться в html формате. 

Пример файла патча: https://github.com/mrkurbatov/diffTest/commit/0a6463fb6372f5a8ba3fcc5e27eaa48d6db308be.patch

Пример визуализации:
https://github.com/mrkurbatov/diffTest/commit/0a6463fb6372f5a8ba3fcc5e27eaa48d6db308be

Функциональные требования:

Отображение информации о патче (автор, время изменения, имя файла)
Построчная визуализация сравнения как на Github, но отображать файл полностью.

Два режима визуализации:
- Unified -  https://github.com/mrkurbatov/diffTest/commit/0a6463fb6372f5a8ba3fcc5e27eaa48d6db308be?diff=unified
- Split - https://github.com/mrkurbatov/diffTest/commit/0a6463fb6372f5a8ba3fcc5e27eaa48d6db308be?diff=split

Пример использования:

`diffVisualizer --file HelloWorld.java --patch changes.patch --out changes.html --mode split`
- --file - исходный файл
- --patch - файл патча
- --out - файл визуализации
- --mode - режим визуализации
