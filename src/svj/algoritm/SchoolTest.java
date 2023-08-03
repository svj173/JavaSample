package svj.algoritm;

/**
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 11.05.2021 14:44
 */
public class SchoolTest
{

    /**
     * Y = 4x - 3x  при х меньше-равно -4
     * Y = x + 2 при -4 меньше х меньше-равно 7
     * Y = -8x  при х больше 7
     *
     *
     */
    private void test2 () {

        int y = 0;
        // Цикл по заданным значениям
        // Пояснение к яве
        // - звездочка - это символ умножения
        // - && - это 'Логическое И'
        for (int x=-9; x<=15; x++) {
            if (x <= -4)  {
                // вычисляем первое уравнение
                y = 4*x - 3*x;
            }

            if ( (-4 < x) && (x <= 7))  {
                // вычисляем второе уравнение
                y = x + 2;
            }

            if (x > 7) {
                // вычисляем третье уравнение
                y = -8*x;
            }

            // Печтаеам результат
            System.out.println("x = " + x + "    y = " + y);
        }

    }

}
