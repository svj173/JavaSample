package annotation.s2;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 17:27:24
 */

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Comparator;

/**
* Базовый класс для всех классов желающий получить способность к сравнению, преобразованию в строку
*/
public class TGenericRecord implements Comparable
{
    /**
     * Внутренний кэш результатов вычисления Reflection-информации
     */
    static final private HashMap<Class<? extends TGenericRecord>, DiscoveryCache> cache = new HashMap<Class<? extends TGenericRecord>, DiscoveryCache>();

             /**
     * Этот и все последующие методы устроены схожим образом:
     * 1 шаг, проверка того что в кэше есть правила извлеченная информация об аннотированных полях
     * 2 шаг, собственно вычисление запрошенной операции
     * Если в ходе выполнения операции произошла ошибка, то будет выброшено Runtime-исключение
     * @return
     */
    public int hashCode() {
        final Class<? extends TGenericRecord> aClass = getClass();
        DiscoveryCache cachedRule = (DiscoveryCache) cache.get(aClass);
        if (cachedRule == null) {
            cachedRule = discoveryClass(aClass);
            cache.put(aClass, cachedRule);
        }
        try {
            return cachedRule.callHashCode(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof TGenericRecord)) return false;

        final Class<? extends TGenericRecord> aClass = getClass();
        DiscoveryCache cachedRule = cache.get(aClass);
        if (cachedRule == null) {
            cachedRule = discoveryClass(aClass);
            cache.put(aClass, cachedRule);
        }
        try {
            return cachedRule.callEquals(this, obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        final Class<? extends TGenericRecord> aClass = getClass();
        DiscoveryCache cachedRule = cache.get(aClass);
        if (cachedRule == null) {
            cachedRule = discoveryClass(aClass);
            cache.put(aClass, cachedRule);
        }
        try {
            return cachedRule.callToString(this);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public int compareTo(Object o) {
        final Class<? extends TGenericRecord> aClass = getClass();
        DiscoveryCache cachedRule = cache.get(aClass);
        if (cachedRule == null) {
            cachedRule = discoveryClass(aClass);
            cache.put(aClass, cachedRule);
        }
        try {
            return cachedRule.callCompareTo(this, o);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

                                /**
     * Служебный метод выполняющий поиск в классе всех полей, определение среди них тех,
     * кто маркирован аннотацией GenericField
     *
     * @param aClass - Класс который нужно просканировать
     * @return
     */
    private DiscoveryCache discoveryClass(Class<? extends TGenericRecord> aClass) {
        DiscoveryCache c = new DiscoveryCache();
        rec_discoveryClass(aClass, c);
        Collections.sort(c.fields, new Comparator()
        {
            public int compare ( CachedField o1, CachedField o2) {
                return ((Integer) o1.anno.priority()).compareTo(o2.anno.priority());
            }

            @Override
            public int compare ( Object o1, Object o2 )
            {
                return compare ( (CachedField) o1, (CachedField) o2);
            }
        });
        return c;
    }

    /**
     * Вспомогательная функция вызываемая из метода {@see #discoveryClass} и
     * выполняющая рекурсивный спуск по иерархии наследования класса,
     * поиск в нем всех полей помеченный данной аннотацией и сохранение информации в кэш
     *
     * @param aClass класс подлежащий сканированию
     * @param c      кэш куда будут помещены все найденные поля и их маркеры
     */
    private void rec_discoveryClass(Class aClass, DiscoveryCache c) {
        if (aClass == null) return;
        if (aClass.isInterface()) return;
        if (aClass.isEnum()) return;
        final Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            final GenericField anoField = declaredField.getAnnotation(GenericField.class);
            if (anoField == null) continue;
            declaredField.setAccessible(true);
            c.fields.add(new CachedField(declaredField, anoField));
        }
    }


                         /**
     * Служебный класс представляющий собой кэш результатов сканирования определенных
     * типов данных (классов) на поля и их маркеры
     */
    private class DiscoveryCache {
        /**
         * Список пар: поле-аннотация
         */
        ArrayList<CachedField> fields = new ArrayList<CachedField>();

        /**
         * Метод выполняющий расчет hashCode на основании правил из кэша
         * @param tGenericRecord объект для которого нужно рассчитать hashCode
         * @return
         * @throws IllegalAccessException
         */
        public int callHashCode(TGenericRecord tGenericRecord) throws IllegalAccessException {
            int summ = 0;
            for (CachedField f : fields) {
                final GenericField.USEPOLICY usepolicy = f.anno.kind();
                if (usepolicy == GenericField.USEPOLICY.ONLY_PRINT) continue;
                final Object o = f.field.get(tGenericRecord);
                summ += o.hashCode() * f.anno.multiplier();
            }
            return summ;
        }

        /**
         * Метод выполняющий попытку сравнить два объекта на основании их маркерных правил
         * @param objA Первый объект для сравнения
         * @param objB Второй объект для сравнения
         * @return
         * @throws IllegalAccessException
         */
        public boolean callEquals(TGenericRecord objA, Object objB) throws IllegalAccessException {
            for (CachedField f : fields) {
                final GenericField.USEPOLICY usepolicy = f.anno.kind();
                if (usepolicy == GenericField.USEPOLICY.ONLY_PRINT) continue;
                final Object a = f.field.get(objA);
                final Object b = f.field.get(objB);
                if (!a.equals(b)) return false;
            }
            return true;
        }

                            /**
         * Преобразование объекта в строку
         * @param tGenericRecord
         * @return
         * @throws IllegalAccessException
         */
        public String callToString(TGenericRecord tGenericRecord) throws IllegalAccessException {
            StringBuilder s = new StringBuilder();
            s.append("<").append(tGenericRecord.getClass().getName()).append(" ");
            final int listSize = fields.size() - 1;
            for (int i = 0; i <= listSize; i++) {
                final CachedField f = fields.get(i);
                final GenericField.USEPOLICY usepolicy = f.anno.kind();
                if (usepolicy == GenericField.USEPOLICY.ONLY_EQUALS) continue;
                final Object a = f.field.get(tGenericRecord);
                s.append(f.field.getName()).append("=").append(a).append(i == listSize ? "" : "; ");
            }
            s.append(">");
            return s.toString();
        }

        /**
         * Сравнение объектов как поддерживающих интерфейс Comparable
         * @param objA первый объект для сравнения
         * @param objB второй объект для сравнения
         * @return
         * @throws IllegalAccessException
         */
        public int callCompareTo(TGenericRecord objA, Object objB) throws IllegalAccessException {
            for (CachedField f : fields) {
                final GenericField.USEPOLICY usepolicy = f.anno.kind();
                if (usepolicy == GenericField.USEPOLICY.ONLY_PRINT) continue;
                final Object a = f.field.get(objA);
                final Object b = f.field.get(objB);
                if (a instanceof Comparable) {
                    @SuppressWarnings(value = "unchecked")
                    final int cmpResult = ((Comparable) a).compareTo(b);
                    if (cmpResult != 0) return cmpResult;
                }
            }
            return 0;
        }
    }

    /**
     * Очень простой класс, состоящий из двух полей: Field и Аннотация привязанная к полю класса
     */
    private class CachedField {
        Field field;
        GenericField anno;

        private CachedField(Field field, GenericField anno) {
            this.field = field;
            this.anno = anno;
        }
    }
}
