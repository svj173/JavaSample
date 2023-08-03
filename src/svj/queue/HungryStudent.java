package svj.queue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Пусть каждому Коку теперь для приготовления еды для голодного студента надо10^10 наносекунд умноженное на идентификатор Кока,
 * тогда давайте поместим всех Коков в очередь, которая будет нам выдавать Кока, который уже готов к работе,
 * если такой Кок имеется. Для решения данной задачи будем использовать DelayQueue, но для помещения класса
 * в очередь он должен реализовывать интерфейс Delay, чтобы реализовать данный интерфейс нужно переопределить
 * два метода: public long getDelay(TimeUnit unit),  public int compareTo(Delayed o).
 * Первый метод должен возвращать количество времени через которое класс будет готов к работе,
 * а второй метод результат сравнения, как и все методы compareTO(); DelayQueue гарантирует, что при любом
 * обращении характерным для очереди она вернет объект с наименьшим временем задержки, и это время будет не положительным.
 * <BR/>
 * <BR/> Если в методе main() убрать sleep, то мы получим   java.lang.NullPointerException,
 * т.к еще не один объект, находящийся в очереди не готов к применению.
 * <BR/>
 * <BR/>  take блокируется до тех пор, пока в очереди не будет элемента, для которго getDelay вернет неположительное значение.
 * Тоже самое произойдет, если в очереди вообще не будет элементов.
 * <BR/>
 * <BR/> Т.е. извлечение из очереди тогда, когда элемент вернет отрицательное значение.
 * <BR/>
 * <BR/>  Особенностью этой очереди является то, что элементы будут доступны только после того,
 * как их метод getDelay будет возвращать значение меньше или раное 0.
 * Внутри себя содержит PriorityQueue поэтому также очень важно правильная реализация метода compareTo
 * для наших элементов, так, чтобы во главе очереди оказывались элементы которые в первую очередь стоит выполнить.
 * <BR/>
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 31.08.2018 14:21
 */
public class HungryStudent {

    public static void main ( String[] args ) {

        int n = 4;

        DelayQueue<Kock> queue = new DelayQueue<>();

        // наполнили очередь Поварами.
        for (int i = 0; i < n; i++) {
            //queue.put(new Kock());
            queue.add(new Kock());
        }

        // ждемс
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!queue.isEmpty()) {
            new Thread(queue.poll()).start();
        }
    }
}

class Kock implements Runnable, Delayed {
    static public int count = 0;
    private TimeUnit time = TimeUnit.NANOSECONDS;
    private int id = count++;

    public void run() {
        System.out.println("Готова еда от Кок№" + id);
    }

    public long getDelay(TimeUnit unit) {
        return unit.convert(id * 100000000000L - System.nanoTime(), time);
    }

    public int compareTo(Delayed o) {
        if (this.getDelay(time) > o.getDelay(time))
                return 1;

        return 0;
    }

}
