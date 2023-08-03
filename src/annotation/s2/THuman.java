package annotation.s2;


/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 26.05.2012 17:52:49
 */
/**
* Пример класса к которому применяются аннотации для управления генерируемыми методами hashCode, equals, compareTo
*/
public class THuman extends TGenericRecord {
    /**
     * Каждое из полей будет помечено тремя характеристиками:
     * priority - порядок поля, влияет на то в каком порядке поля
     * будут выводиться на экран в методе toString
     * также на то в каком порядке поля будут сравниваться между собой
     * это важно для метода compareTo и, возможно, вычисления хэш-кода)
     * второй параметр multiplier служит для задания величины числа множителя метода hashCode (значение по-умолчанию 17)
     * третий параметр kind управляет тем, будут ли печаться в методе
     * toString поля на экран или будут учавствовать в сравнениях полей
     * поля не помеченные аннотацией GenericField не учавствуют ни в одном из трех методов
     */
    @GenericField(priority = 0)
    int id;
    @GenericField(priority = 1, kind = GenericField.USEPOLICY.BOTH_PRINT_AND_EQUALS)
    String fio;

    @GenericField(priority = -1, kind = GenericField.USEPOLICY.ONLY_EQUALS)
    Sex sex;

    @GenericField(priority = 2, kind = GenericField.USEPOLICY.ONLY_PRINT)
    double money;

    public THuman(int id, String fio, Sex sex, double money) {
        this.id = id;
        this.fio = fio;
        this.sex = sex;
        this.money = money;
    }
}
