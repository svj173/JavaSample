package svj.queue;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

/**
 * Попытка создать функционал, который бы ранвомерно раскидывал обработчики каких-то процессов (свой поток).
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.05.2017 15:57
 */
public class DelayQueueTest
{
    public static void main ( String[] args )
    {
        // Creates an instance of blocking queue using the DelayQueue.
        BlockingQueue queue = new DelayQueue ();

        // Предварительно Наполнить очередь

        // Создать процесс, который бы потсоянно  опрашивал очередь и создавал потоки
        //new DelayQueueProducer ( queue ).start ();

        // Starting DelayQueue Consumer to take the expired delayed objects from the queue
        //new DelayQueueConsumer ( "Consumer Thread-1", queue ).start ();
    }

}
