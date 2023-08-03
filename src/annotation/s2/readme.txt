Аннотации могут быть применены, например, к декларациям классов, полей, методов, ну и конечно же аннотаций :).
Для описания новой аннотации используется ключевое слово @interface. Вот банальный пример аннотации:
public @interface Description {
  String title();

  int version() default 1;

  String text();
}

И пример ее использования:
@Description(title="title", version=2, text="text")
public class Clazz { /* */ }

Сразу хочу обратить ваше внимание - в качестве типов у элементов аннотации могут использоваться только примитивные типы, перечисления и класс String.

Если у аннотации нет элементов, ее называют маркером (marker annotation type). В этом случае при использовании аннотации круглые скобки можно не писать.

В случае, когда аннтоация указывается для другой аннотации, первую называют мета-аннотацией (meta-annotation type).





Сегодня я покажу небольшой пример как аннотации могут облегчить жизнь.
При создании приложения работающего с данными и использующего ORM (или просто объекты которые будут
храниться в коллекциях, сортироваться, сравниваться) необходимо написание трех методов:

hashCode
equals
compareTo

Например, класс User будет выглядеть так:

Собственная реализация hashCode, equals, toString, compareTo практически идентична и не не отличается
для классов User, Cat, Animal (знай только меняй значения полей и все).
Вот тут кроется проблема: при интенсивном рефакторинге можно добавить новое поле в класс, но забыть
использовать его для остальных методов (надо сказать, что аннотации наиболее часто используются для
решения подобной проблемы размазывания логики по нескольким файлам или методам класса).

Я создам новую аннотацию, позволяющую метить некоторые из полей класса маркером "учавствует в сравнении",
"нужно вывести значение этого поля внутри метода toString". Единственное слабое место, что автоматически
сгенерировать для класса новые методы не возможно (если не использовать CGLIB или JavaProxy и при
этом перекроить правила создания объектов User, Cat ...). Хотя при создании аннотации можно указать
кому она будет видна (только компилятору и после генерации class-файла подлежать удалению, или же,
информация о метках будет доступна и на стадии выполнения программы, так что к ней можно добраться с помощью java reflection).

Увы но решение будет не чистое,а потребует создания некоторого утилитного класса. От которого и должны
будут наследоваться классы User, Cat ... Методы toString, equals, compareTo, hashCode будут перекрыты
в этом утилитном классе, и при обращении к ним будут сканировать объект, искать в нем поля помеченные
специальной аннотацией и выполнять логику сравнения или представления содержимого класса как строки с
учетом установленных для полей класса маркеров:

GenericField

Теперь я привожу код служебного класса от которого должны наследоваться все ваши классы-домены. Внутри этого класса создан статический кэш, так что при многократном вызове методов toString, hashCode, ... не будет каждый раз заново вычисляться информация об путях интроспекции и том какие атрибуты помечены маркерами.

TGenericRecord

Теперь пример использования. Сначала класс с данными и маркерным метками:

THuman

И завершаю пример следующим тестом:

TesterAni

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import testi.catsandusers.Sex;

import java.util.Arrays;
import java.util.Collection;

/**
* Небольшой тест промаркированного класса
*/
@RunWith(Parameterized.class)
public class TesterAni {

   @Parameterized.Parameters
   public static Collection humanMaker() {
      return Arrays.asList(new Object[][]{
      {
         true, 0,
         new THuman(1, "vasyano", Sex.MALE, 100.0), new THuman(1, "vasyano", Sex.MALE, 100.0)
      },
      {
         false, +1,
         new THuman(1, "vasyano", Sex.FEMALE, 100.0), new THuman(1, "vasyano", Sex.MALE, 100.0)
      },
      {
         false, -1,
         new THuman(1, "Avasyano", Sex.MALE, 100.0), new THuman(1, "Bvasyano", Sex.MALE, 100.0)
      },
      {
         true, 0,
         new THuman(1, "vasyano", Sex.MALE, 100.0), new THuman(1, "vasyano", Sex.MALE, 200.0)
      },

      });
   }


   boolean ifEqual;
   int compareResult;
   THuman humanA, humanB;

   public TesterAni(boolean ifEqual, int compareResult, THuman humanA, THuman humanB) {
      this.ifEqual = ifEqual;
      this.compareResult = compareResult;
      this.humanA = humanA;
      this.humanB = humanB;
   }

   @Test
   public void compareHumans() {
      assertEquals("HumanA equals HumanB", humanA.equals(humanB), ifEqual);
      assertEquals("HumanA compareTo HumanB", humanA.compareTo(humanB), compareResult);
   }
}



Или вот еще пример использования более простой:
THuman vasyano = new THuman(10, "Vasyano", Sex.MALE, Math.random()*2000);
THuman petyano = new THuman(10, "Petyanio", Sex.FEMALE, Math.random()*2000);

System.out.println("petyano = " + petyano);
System.out.println("vasyano = " + vasyano);

System.out.println("vasyano hashCode = " + vasyano.hashCode() );
System.out.println("petyano hashCode = " + petyano.hashCode() );

System.out.println("vasyano eq petyano = " + vasyano.equals(petyano) );a
System.out.println("vasyano compareTo petyano = " + vasyano.compareTo(petyano) );


А вот так будет выглядеть результат выполнения кода:

petyano = 10; fio=Petyanio; money=1455.1612655356755>
vasyano = 10; fio=Vasyano; money=176.3383329687862>
vasyano hashCode = -1906494401
petyano hashCode = 1119803717
vasyano eq petyano = false
vasyano compareTo petyano = -1

