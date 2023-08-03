package svj.thread;


import exception.SvjException;
import tools.Convert;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Назначение - обработывать входящие объекты в своем отдельном потоке. Причем входящие объекты складываются в очередь. (Последовательная обработка).
 * Является единственным обработчиком (в отличие от пула одинаковых обработчиков).
 * <BR> В бесконечном цикле ждет появления объекта. Как только объект появился,
 * он складывается в очередь, а обработчик будится.
 * <BR> Обработчик  в цикле тянет объекты из очереди (вдруг пока обрабатывал первый
 * объект, появились еще несколько?). Как только очередь опустошается - обработчик засыпает.
 * <BR> Варианты засыпания:
 * <BR> 1) Засыпает на какое-то заданное время - для использования таймеров на ответы-запросы - для проверки линии связи, например.
 * <BR> 2) Либо бесконечно - до появления объекта.
 * <BR>
 * <BR> Скопирован для параллельных тестов.
 * <BR>
 * <BR/> User: svj
 * <BR/> Date: 01.09.2010 11:33:11
 */
public abstract class ObjectHandler<T> extends Thread
{
    /* Флаг поднятости отдельного процесса-потока */
    private boolean started = false;

    /* Буфер объектов на обработку. Очередь применяет FIFO (первым прибыл - первым убыл) */
    private LinkedBlockingQueue<T> queue = new LinkedBlockingQueue<T>();

    /* Пауза ожидания появления объекта на обработку - в мсек.
        0 - бесконечная. выборка делается через take
        иначе - через poll, чтоб иметь возможность остановиться через это время */
    private long delay = 0;

    /* Обязательная пауза XXXX ms перед получением новых объектов в обработку
    устанавливается когда нам точно надо запускать обработку не чаще чем в ХХXX ms
     */
    private long takeSleep = 0;

    /* Собственно выделенный объект отдельного потока. (через this проще?) */
    private Thread thread = null;

    /* счетчик обработанных задач. */
    private long handleCounter = 0;

    /* Дата старта обработчика - для статистики и анализа - эффективен или нет (удалить?) */
    private Date startDate = new Date();
    private Date lastRunDate = new Date();

    /* Собственное имя обработчика. name - занят под имя потока. */
    private String title = "";
    private String subTitle = "";

    /* Максимально допустимая граница для входного буфера данных. При переполнении - в лог кидается ошибка. */
    private int maxSize = Integer.MAX_VALUE;

    /* Флаг, работоспособен ли обработчик или нет (например, из-за неправильно заданных исходных параметров) */
    private boolean enableHandler = true;

    /* Максимальный объем выборки из входного буфера данных.
     * Нужно, например AlertStoreHandler'у, чтоб получать не более N объектов и быстро отдавать их наверх трапами
     * Если Integer.MAX_VALUE - выбирать все что есть */
    private int maxPickSize = Integer.MAX_VALUE;

    private long allCounter = 0;
    private long errorCounter = 0;
    private long totalWork = 0;
    private long minWork = 0;
    private long avrWork = 0;
    private long maxWork = 0;

    /**
     * Дополнительная строка, куда можно сохранить OID трапа при длительной обработке
     * или строку с меткой времени для какой-то критичной ситуации
     */
    private String infoString = null;

    //private Logger logger = null;

    private Object object;


//===========================V===== abstract ======V=========================================

    /**
     * Обработать полученный объект.     --- protected - ВСЕ кроме setProperties
     *
     * @param objects Массив обьктов, взятые из стека запросов.
     * @throws SvjException err
     */
    protected abstract void handle(List<T> objects) throws SvjException;

    /**
     * Обработать ситуацию истечения Таймаута. Т.е. никакого объекта получено не было.
     */
    protected abstract void handleEmpty();

    /**
     * Прежде чем запустить эту нить, ждать когда поднимется какой-то сторонний важный
     * процесс. Например, стартует сервер.
     */
    protected abstract void waitRunning();

    /**
     * Обработать ошибку, возникшую во время выполнения метода handle.
     *
     * @param exc объект ошибки
     */
    protected abstract void handleError(Exception exc);

    /* Занесение внешних параметров (например, при инициализации из xml файла).
     * SvjException - если ошибка или отсутствие какого-то важного параметра.
     * Параметры берутся из тэга properties. Имя тэга - ключ для props, Значение - значение для props. */
    public abstract void setProperties(Properties props) throws SvjException;

//===========================^===== abstract ======^=========================================

    public String getInfo()
    {
        StringBuilder result;
        long total, t1;

        total = System.currentTimeMillis() - startDate.getTime();

        result = new StringBuilder(512);
        result.append("\nProcess : "); // Процесс
        result.append(getTitle());
        if (getSubTitle() != null && getSubTitle().length() > 0) {
            result.append(" - ");
            result.append(getSubTitle());
        }
        result.append(" (");
        result.append(getClass().getSimpleName());
        result.append(")");

        result.append("\n\tIs started\t\t: "); // Флаг запуска
        result.append(isStarted());
        result.append("\n\tStart date\t\t: "); // Дата запуска
        result.append(Convert.getRussianDateTime(getStartDate()));
        result.append("\n\tLast iteration date\t: "); // Дата последней итерации
        result.append( Convert.getRussianDateTime ( getLastRunDate () ));
        result.append("\n\tCurrent queue size\t: "); // Текущий буфер
        result.append(queue.size());
        result.append("\n\tMax queue size\t\t: "); // Макс размер буфера
        result.append(maxSize);
        result.append("\n\tTotal requests\t\t: "); // Всего запросов
        result.append(allCounter);
        result.append("\n\tProcessed requests\t: "); // Обработано запросов
        result.append(getHandleCounter());
        result.append("\n\tErrors\t\t\t: "); // Ошибок
        result.append(errorCounter);
        result.append("\n\tTotal time, ms\t\t: "); // Общее время
        result.append(total);
        result.append("\n\tWork time, ms\t\t: "); // Рабочее время
        result.append(totalWork);
        result.append("\n\tMin time, ms\t\t: "); // Мин время
        result.append(minWork);

        t1 = 0;
        if (allCounter > 0) t1 = totalWork / allCounter;
        result.append("\n\tAverage time, ms\t: ");  // Среднее время
        result.append(t1);

        result.append("\n\tMax time, ms\t\t: "); // Макс время
        result.append(maxWork);

        if (getInfoString() != null){
            result.append("\n\tInformation (max)\t: ");
            result.append(getInfoString());
        }
        
        result.append("\n\tMax picking size\t: "); // Макс размер выборки
        result.append(maxPickSize);
        result.append("\n\tMax waiting time, ms\t: "); // Макс время ожидания данных
        result.append(delay);
        result.append("\n\tMandatory pause, ms\t: "); // Обязательная пауза
        result.append(takeSleep);

        /*
        // 100 / ( totalWork / total )
        procent = 0;
        if ( total > 0 )
        {
            t1 = totalWork / total;
            if ( t1 > 0 )  procent = 100 / t1;
        }
        result.append ( "\n\tПроцент простоя\t\t: " );
        result.append ( procent );
        */

        result.append(getAdditionalInfo());

        return result.toString();
    }

    /* Какая-то дополнительная информация о работе данного Обработчика. */
    public String getAdditionalInfo() {
        return "";
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public long getDelay() {
        return delay;
    }

    public void publish ( T object )
    {
        //В рамках Bug #22977 Спрятать логи ObjectHandler'а, удаляем лишние логи
        //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): Start. Name = ", getName(), "; request size = 1"));
        //LogWriter.l.debug ( "(%s) Start publish single object (name:%s) : started = %b; object = %s", getTitle(), getName(), isStarted(), object );

        if (object != null)
        {
            // Процессор находится в ожидании - Положить объект в буфер на обработку и встряхнуть Процессор.
            // смотрим размер буфера. Если больше заданного - не заносим в буфер. Сообщаем о переполнении буфера.
            if (queue.size() > maxSize)
            {
                //LogWriter.l.error ( "(%s): Queue is full. Max size = %d", getTitle(), maxSize );
                //LogWriter.l.error ( " ("+title+"): Queue is full. Max size = " + maxSize );
                setInfoString ( " ("+title+"): Queue is full. Max size = " + maxSize );
                System.out.println ( " (" + title + "): Queue is full. Max size = " + maxSize );
            }
            else
            {
                queue.add(object);
                // Будить поток не надо, он проснется сам в операции take
            }
        }
        //else
        //{
            //LogWriter.l.debug ( "(%s): Finish (object==null)", getTitle() );
        //}
        //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): Finish (one object mode)"));
    }

    public void publish(Collection<T> list) {
        //В рамках Bug #22977 Спрятать логи ObjectHandler'а, удаляем лишние логи
        //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): Start. Name = ", getName()));

        if ((list != null) && (!list.isEmpty())) {
            //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): request size = ", list.size()));
            // Процессор находится в ожидании - Положить объект в буфер на обработку и встряхнуть Процессор.
            // смотрим размер буфера. Если больше заданного - не заносим в буфер. Сообщаем о переполнении буфера.
            int size = queue.size();
            //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): queue.size = ", size, ", added size = ", list.size()));
            if (size > maxSize) {
                //LogWriter.l.error( Convert.concatObj(" (", getTitle(), "): Queue is full. Max size = ", maxSize));
                //LogWriter.l.error ( " ("+title+"): Queue is full. Max size = " + maxSize );
                setInfoString ( " ("+title+"): Queue is full. Max size = " + maxSize );
            } else {
                queue.addAll(list);
                // Будить поток не надо, он проснется сам в операции take
            }
        }
        //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): Finish (list mode)"));
    }


    public void startHandler()
    {
        //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): Start."));

        if (!started) {
            thread = new Thread(this, getTitle());
            started = true;
            thread.start();
            startDate = new Date();
            startInit ();
        } else {
            printMsg ( " (", getTitle (), "): Process already started." );
        }
        printMsg ( " (", getTitle(), "): Finish. started = ", started );
    }

    protected void printMsg ( Object... objs )
    {
        StringBuilder result;

        result = new StringBuilder ( 128 );
        result.append ( Convert.getRussianDateTime ( new Date() ) );
        result.append ( ' ' );
        result.append ( Thread.currentThread().getName () );
        //result.append ( " Msg : " );
        result.append ( '\t' );
        result.append ( Convert.concatObj ( objs ) );
        result.append ( "; Sender = " );
        result.append ( this.getClass().getCanonicalName () );
        result.append ( ':' );
        result.append ( this.getClass().hashCode () );
        result.append ( "; isAlive = " );
        result.append ( isAlive() );
        result.append ( "; size = " );
        result.append ( queue.size() );
        result.append ( "; started = " );
        result.append ( started );

        System.out.println ( result );
    }


    /**
     * Метод дергается при поднятии обработчика.
     * Переписывается - для сброса каких-то индивидуальных параметров.
     */
    public void startInit ()   { }

    public void stopHandler() {
        //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): Stop."));
        started = false;
        try {
            if (thread != null) thread.interrupt();
        } catch (Exception e) {
            //LogWriter.l.error ( "Error.", e );
            setInfoString ( "stopHandler Error: " + e);
        }
        //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): Finish."));
    }

    //В рамках Bug #22977 Спрятать логи ObjectHandler'а, удаляем лишние логи
    @Override
    public void run()
    {
        List<T> objects;
        T first;
        long sDate, allT;
        int requestSize;  // размер входящих запросов - для коректного подсчета счетчика 'Все запросы'
        Date startTake, endTake;

        //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): Start run() method."));

        // Ждать когда поднимется какой-то важный процесс. Например, стартует сервер.
        waitRunning();
        //LogWriter.l.debug(Convert.concatObj("thread '", this, "' is running. Started = ", started));

        while (started)
        {
            // allCounter++; - avp : нельзя сразу накидывать +1, т.к. он всегда больше обработанных и всегда равен 1, даже если ни разу не выполнялся

            requestSize = 1;
            sDate = 0;
            lastRunDate = new Date();
            try
            {
                // перед очередным витком цикла - стартовая пауза - чтобы ЦП сервера отдохнул
                // - иначе при непрерывном поступленнии алертов ЦП будет работать без отдыха.
                //thread.wait ( 1000 );  // ??? - IllegalStateMonitor

                //thread.wait ( 1000 );  // ??? - IllegalStateMonitor
                // Синхронизуем все изменения над  queue
                startTake = new Date();
                try
                {
                    if (delay==0) {
                        /* Обязатальная пауза (пусть себе копится) в вытаскивании данных */
                        if (takeSleep>0) TimeUnit.MILLISECONDS.sleep(takeSleep);
                        /* Блокирует если очередь пуста, обработка остановится */
                        first = queue.take();
                    } else {
                        /* Такой режим нужен например для работы FileSaveHandler'а, чтоб не ждать до бесконечности!
                        * Возвратит null, если очередь пуста*/
                        first = queue.poll(delay, TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException inExc) {
                    //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): InterruptedException.")); // работа обработчика остановлена!
                    setInfoString ( Convert.concatObj(" (", getTitle(), "): InterruptedException.") );
                    started=false;
                    break;
                }
                endTake = new Date();
                allT = endTake.getTime()-startTake.getTime();
                // Либо нас разбудили, либо очередь запросов НЕ пустая
                //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): Woken up after ",allT," ms. Stack size = ", queue.size()));
                objects = createObject(first);

                // Проснулись
                sDate = System.currentTimeMillis();

                if (!started) break; // Разбудили потому что конец работы. Выход из цикла.

                if ( (objects == null) || objects.isEmpty() )
                {
                    // Разбудили потому что истекло время ожидания.
                    handleEmpty();
                }
                else
                {
                    requestSize = objects.size();
                    allCounter += requestSize;
                    //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): Get new Objects. handleCounter = ", handleCounter));
                    System.out.println ( Convert.concatObj ( "--- (", getTitle(),"): isEnableHandler = ", isEnableHandler() ) );
                    // Обработать
                    if ( isEnableHandler() )
                    {
                        //LogWriter.l.debug(" (", getTitle(), "): handle objects = ", objects);
                        handle ( objects );
                        handleCounter = handleCounter + requestSize;
                    }
                    else
                    {
                        //LogWriter.l.debug(Convert.concatObj(" (", getTitle(), "): enableHandler = false. Don't work."));
                        setInfoString ( Convert.concatObj(" (", getTitle(), "): enableHandler = false. Don't work.") );
                    }
                }

            } catch ( Exception exc ) {
                errorCounter++;
                handleError(exc);
                //LogWriter.l.error( Convert.concatObj ( " (", getTitle(), "): run step error"), exc);
                setInfoString ( Convert.concatObj ( " (", getTitle(), "): run step error") );
            }
            //allCounter = allCounter + requestSize - 1; // -1 -- т.к. прибавили в самом начале
            processWorkTime(sDate, Convert.getRussianDateTime(lastRunDate));
        }
        //long workTime = (new Date()).getTime() - startDate.getTime();
        //LogWriter.l.info(Convert.concatObj(" (", getTitle(), "): Finish: TIME. thread '", this, "' terminated. Work time = ", workTime, ", handleCounter = ", handleCounter));
    }

    private void processWorkTime ( long workTime, String info )
    {
        workTime = System.currentTimeMillis() - workTime;

        totalWork = totalWork + workTime;
        if (minWork == 0)
            minWork = workTime;
        else if (minWork > workTime)
            minWork = workTime;
        if (maxWork < workTime){
            maxWork = workTime;
            infoString = info;
        }
    }

    /*  Из стека формируем массив объектов */
    private List<T> createObject(T first) {

        if (first==null) return null;

        // очередь разблокируется и вытащит первый элемент, надо его добавить сразу
        List<T> result = new ArrayList<T>();
        result.add(first);
        // и далить все остальные
        queue.drainTo(result, maxPickSize-1);
        return result;
    }

    public boolean isStarted() {
        return started;
    }

    public void resetStarted() {
        started = false;
    }

    /**
     * Прервать текущую обработку, но сам процесс оставить рабочим. Т.е. подтолкнули подвисший процесс.
     */
    public void reset() {
        try {
            if (thread != null) thread.interrupt();
        } catch (Exception e) {
            //LogWriter.l.error("Error %s", e);
            setInfoString ( "reset error : "+e );
        }
    }

    public long getHandleCounter() {
        return handleCounter;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getLastRunDate() {
        return lastRunDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isEnableHandler() {
        return enableHandler;
    }

    public void setEnableHandler(boolean enableHandler) {
        this.enableHandler = enableHandler;
    }

    public void incError() {
        errorCounter++;
    }

    /**
     * Установить верхний предел выборки из очереди
     *
     * @param maxPickSize
     */
    public void setMaxPickSize(int maxPickSize) {
        this.maxPickSize = maxPickSize;
    }

    public void setTakeSleep(long takeSleep) {
        this.takeSleep = takeSleep;
    }

    public int getQueueSize()
    {
        return queue.size();
    }

    public String getInfoString() {
        return infoString;
    }

    public void setInfoString(String infoString) {
        this.infoString = infoString;
    }

    public Object getObject ()
    {
        return object;
    }

    public void setObject ( Object object )
    {
        this.object = object;
    }

}
