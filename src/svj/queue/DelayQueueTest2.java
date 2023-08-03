package svj.queue;

import tools.Convert;
import tools.DumpTools;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 31.08.2018 14:58
 */
public class DelayQueueTest2 extends Thread {

    /**
     * Номер цикла опроса. Для отладок.
     */
    public static volatile long stepNumber = 0;

    //public static final int pingPeriodSec = 60;
    public static final int pingPeriodSec = 360;

    //private final int objectSize = 6;
    private final int objectSize = 1300;

    /**
     * Очередь элементов опроса устройств. Содержит в себе ИД и время запуска. Т.е. с каждым циклом очередь
     * увеличивается на кол-во устройств.
     */
    private final DelayQueue<DelayedElement> queue = new DelayQueue<>();

    //private FileOutputStream fos;
    private PrintStream fos;


    public DelayQueueTest2() {

        try {
            //fos = new FileOutputStream ("/home/svj/tmp/delayQueueTest2_log.txt");
            fos = new PrintStream ("/home/svj/tmp/delayQueueTest2_log.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fos = null;
        }

        // вычисляем шаг в мсек
        int subPeriod = pingPeriodSec - (pingPeriodSec / 10);
        if (subPeriod == pingPeriodSec)
            subPeriod = pingPeriodSec - 1;
        int step = (subPeriod * 1000) / objectSize;  // в мсек

        //System.out.println(
        log (
                "objectSize = " + objectSize + "; pingPeriodSec = " + pingPeriodSec + "; subPeriod = " + subPeriod +
                        "; stepMsec = " + step);

        long currentTimeMsec = System.currentTimeMillis() + 60000;   // небольшой сдвиг, чтоыб не сразу все заработало
        long time;

        for (int ic = 1; ic <= objectSize; ic++) {
            time = currentTimeMsec + (step * ic);
            log("- " + ic + ") " + Convert.getEuroDateAsStr(new Date(time)));
            queue.add(new DelayedElement(ic, time));
        }

        log (DumpTools.printCollection(queue));
    }


    /**
     * Бесконечный опрос очереди устройств и генерация Пинг-запросов - в отдельных потоках.
     */
    @Override
    public void run() {
        int ic;  // счетчик устройств.
        DelayedElement tick = null;

        log("[PING] Start PING process.");

        ic = 1;
        while ( stepNumber < 5 ) {
        //while (!interrupted()) {
        //for ( int i=0; i<5; i++ )  {

            try {
                tick = queue.take();   // Взять с удалением из очереди, иначе он будет брать все время первый обьект.
                //LogWriter.l.debug ( "AvailableDevicePingV2.HandleThread : Get queue.taken = %s", tick );
                log ( Convert.getEuroDateAsStr(new Date())+"\t[PING_PROCESS_"+stepNumber+"]: element ="
                        + " " +tick );

                if (tick == null) {
                    // Очередь пуста. Ждем 10 секунд (навскидку)
                    TimeUnit.SECONDS.sleep (1000);
                    log ("[PING_PROCESS_"+stepNumber+"]: queue is Empty. Wait 1 sec." );
                } else {
                    // Процесс...
                    // Продлеваем время - в любом случае
                    tick.setDelay();
                    queue.add(tick);
                }
            } catch (Exception iex) {
                // Какая-то серьезная ошибка - отстанавливаем работу потока.
                log ("[PING_PROCESS_" + stepNumber + "] Error: tick = " +
                                tick );
                iex.printStackTrace();
                log ( iex );
                break;
            }

            // Конец цикла по обработке одного устройства.
            ic++;
            if (ic > objectSize) {
                ic = 1;
                stepNumber++;
                log ("[PING_PROCESS_"+stepNumber+"] : total queue = "+
                        DumpTools.printCollection(queue));
            }

        }
        if ( fos != null )
        {
            fos.flush();
            fos.close();
        }
        log ("[PING_PROCESS_"+stepNumber+"] Stoping All PING process." );
    }

    private void log(Exception iex) {
        if ( fos == null )
            iex.printStackTrace();
        else
            iex.printStackTrace(fos);
    }

    private void log ( String msg )
    {
        msg = Convert.getEuroDateAsStr(new Date())+"\t"+msg+"\n";
        if ( fos == null )
            System.out.print ( msg );
        else
            fos.print(msg);
    }

    class DelayedElement implements Delayed {
        private long duration;
        private final int objId;

        /**
         * @param objId    ИД устройства для опроса доступности
         * @param timeMsec Дата следующего старта опроса.
         */
        public DelayedElement(int objId, long timeMsec) {
            this.objId = objId;
            this.duration = timeMsec;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long diff = duration - System.currentTimeMillis();
            return unit.convert(diff, TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed object) {
            if (object == null)
                return 1;
            else
                return (int) (this.duration - ((DelayedElement) object).getDuration());
        }

        public long getDuration() {
            return duration;
        }

        public void setDelay() {
            duration = System.currentTimeMillis() + (DelayQueueTest2.pingPeriodSec * 1000);
        }

        public int getObjId() {
            return objId;
        }

        public String toString() {
            StringBuilder result;
            long current;

            current = System.currentTimeMillis();
            result = new StringBuilder();

            result.append("[ DelayedElement : id = ");
            result.append(getObjId());
            result.append("; duration, msec = ");
            result.append(Convert.getEuroDateAsStr(new Date(getDuration())));
            result.append("; currentTime, msec = ");
            result.append(Convert.getEuroDateAsStr(new Date(current)));
            result.append("; delta, msec = ");
            result.append(getDuration() - current);

            result.append(" ]");

            return result.toString();
        }


    }

    public static void main(String[] args) {
        DelayQueueTest2 manager = new DelayQueueTest2();
        manager.start();
    }
}