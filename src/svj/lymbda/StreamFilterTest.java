package svj.lymbda;

import java.util.*;

/**
 * <BR/>
 */
public class StreamFilterTest {

    /*

    - 1). Document{{_id=b7745d61-59ae-461a-bf22-c1bad841a9a6, title=MQTT устройства с прямым подключением}}
	- 2). Document{{_id=a0dd4095-1079-4dbc-b01f-5b26e7231c86, title=Устройства с прямым подключением}}
	- 3). Document{{_id=f7aefd5a-b10b-47fb-a54e-c0aa1ab9abfe, title=RG-35-WZ #1}}


            OptionalInt max = controllerRepository.getControllerIdsAndTitles(houseId).stream()
                    .mapToInt(ctl -> getDefaultOrder(ctl.getString("title"), ctl.getString("_id")))
                    .max();


            // Пробегаем по титлам контроллеров. Ищем наш. Выделяем индекс, если есть. Учитываем самый максимальный.
            int maxIndex = 0;
            for (Document doc: ctlList) {
                String ctlTitle = doc.getString("title");
                if (ctlTitle.startsWith(newTitle)) {
                    // Выделяем индекс
                    int index = getTitleIndex(ctlTitle);
                    if (index > maxIndex) maxIndex = index;
                }
            }
     */


    public static void main ( String[] args ) {

        StreamFilterTest handler = new StreamFilterTest();
        handler.handle("RG-35-WZ");

    }

    private void handle(String newTitle) {

        //
        Collection<Map<String,String>> ctlList = new ArrayList<>();

        Map<String,String> ctl;

        ctl = new HashMap<>();
        ctl.put("title", "MQTT устройства с прямым подключением");
        ctlList.add(ctl);

        ctl = new HashMap<>();
        ctl.put("title", "Prib #1");
        ctlList.add(ctl);

        ctl = new HashMap<>();
        ctl.put("title", "Prib #10");
        ctlList.add(ctl);

        ctl = new HashMap<>();
        ctl.put("title", "RG-35-WZ #2");
        ctlList.add(ctl);

        ctl = new HashMap<>();
        ctl.put("title", "RG-35-WZ #1");
        ctlList.add(ctl);


        int maxIndex = 0;
        for (Map<String,String> doc: ctlList) {
            String ctlTitle = doc.get("title");
            if (ctlTitle.startsWith(newTitle)) {
                // Выделяем индекс
                int index = getTitleIndex(ctlTitle);
                if (index > maxIndex) maxIndex = index;
            }
        }
        System.out.println ("-1). max = " + maxIndex);


        OptionalInt max = ctlList.stream()
                .filter(doc -> doc.get("title").startsWith(newTitle))
                .mapToInt(doc -> getTitleIndex(doc.get("title")))
                .max();

        System.out.println ("-2). max = " + max);

        /*
citiesStream.filter(s->s.length()==6).forEach(s->System.out.println(s));

collection.stream().filter("a1"::equals).count()

         */

    }

    private int getTitleIndex(String title) {
        int result = 0;
        int index = title.indexOf('#');
        if (index > 0) {
            try {
                result = Integer.parseInt(title.substring(index + 1));
            } catch (Exception e) {
                // есть символ '#', но за ним - не число.
                System.out.println("getTitleIndex error after #. title = " + title);
            }
        }
        return result;
    }

}
