# alternating_threads
## Test task "alternating threads"
This program implements the synchronized operation of two similar threads.

- First, an object of **WriteAndRead** class is created, whose constructor creates a text file, in **/resources/** directory, **out.txt** and writes value "0" to it.
- Then two similar threads are created, objects of **ThreadWriteAndRead** class, each of them synchronously interacts with an object of WriteAndRead class. 
- The thread first reads a value from **out.txt**, then iterates it and outputs it to the console:
  - Old value: "value"
  - New value: "value"
  - name_thread that made this iteration.
- When this loop is complete, the program waits until both threads are finished and terminates.

The **alternating_threads** class checks the value received from the user. It must be > 0 and even, otherwise the program will ask to try again.

#
# alternating_threads
## Тестовое задание "чередующиеся потоки"
В этой программе реализована синхронизированная работа двух подобных потоков.
- Сначала создаётся объект класса **WriteAndRead**, конструктор которого создает текстовый файл, в директории **/resources/**, **out.txt** и записывает в него значение "0".
- Затем создаются два подобных потока, объекты класса **ThreadWriteAndRead**, каждый из них синхронизировано взаимодействует с объектом класса WriteAndRead. 
- Поток сначала считывает значение из **out.txt**, затем итерирует его и выводит в консоль:
  - Старое значение: "значение"
  - Новое значение: "значение"
  - имя_потока, совершившего данную итерацию.
- По завершению данного цикла программа дожидается завершения обоих потоков и завершает выполнение.

В классе **alternating_threads** проверяется значение, получаемое от пользователя. Оно должно быть > 0 и быть чётным, иначе программа попросит попробовать ещё раз.
