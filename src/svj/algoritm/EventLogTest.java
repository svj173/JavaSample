package svj.algoritm;

import java.util.*;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 27.04.2021 12:30
 */
public class EventLogTest {

    private static class EventLog {

        public EventLog(String id) {
            this.id = id;
        }

        private final String id;

        public String getId() {
            return id;
        }

        public String toString() {
            return id;
        }
    }


    public static void main(String[] args) {
        String[] list1 = new String[] {"2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7",
                "2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7|1", "2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7|2",
                "2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7_SECTOR1", "2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7_SECTOR1|1"};

        String[] list2 = new String[] {"2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7",
                "2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7_SECTOR1", "2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7_SECTOR1|1"};

        EventLogTest manager = new EventLogTest();

        manager.processTest ("2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7", list1);
        manager.processTest ("2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7", list2);
    }

    private void processTest(String idLog, String[] list) {

        List<EventLog> eventLogs = new ArrayList<>();
        for (String id : list )  {
            eventLogs.add(new EventLog(id));
        }

        System.out.println("idLog = " + idLog);
        System.out.println("eventLogs = " + eventLogs);

        EventLog result = getRealLog(idLog, eventLogs);
        System.out.println("result = " + result);
        System.out.println("----------------------------------\n");


    }

    private EventLog getRealLog(String idLog, List<EventLog> eventLogs) {
        if (idLog.length() == 47) {
            System.out.println("-- 47");
            // Запись без секции. А в списке могут быть и с секциями, и продолжения при переполнении.
            // todo Учитываем, что продолжений может и не быть!
            String id = idLog + "|";
            System.out.println("-- id = " + id);
            System.out.println("-- eventLogs.size() = " + eventLogs.size());
            // Рекурcия от конца. Т.к. переполнения отсортированы по возрастанию последней цифры.
            EventLog log, resultLog = null;
            for (int i=eventLogs.size() - 1; i >= 0; i--) {
                System.out.println("----- i = " + i);
                log = eventLogs.get(i);
                System.out.println("----- log = " + log);
                if (log.getId().equals(idLog)) {
                    resultLog = log;
                }
                System.out.println("----- resultLog = " + resultLog);
                if (log.getId().contains(id)) {
                //if (id.contains(log.getId())) {
                    System.out.println("--- return = " + log);
                    return log;
                }
            }
            // Не нашли. Значит нет продолжений - берем имеющуюся.
            System.out.println("-- Not find: resultLog = " + resultLog);
            return resultLog;
        } else {
            System.out.println("-- none 47" + eventLogs.size());
            // Требуется запись для ИД, включающего в себя Секцию
            // - Берем последнюю, т.к. других записей здесь нет.
            return eventLogs.get(eventLogs.size() - 1);
        }
    }

    /*
    for 2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7
    1)
2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7
2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7|1
2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7|2
2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7_SECTOR1
2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7_SECTOR1|1

    2)
2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7
2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7_SECTOR1
2021-04-23_88c33cf3-e426-47e7-82f4-83adf00051b7_SECTOR1|1

     */


}
