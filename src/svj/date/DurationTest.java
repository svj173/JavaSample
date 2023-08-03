package svj.date;

import java.time.Duration;

/**
 * Для работы со временными диапазонами. Позволяет работать с очень огромными числами.
 * <BR/> Удобен для кешей наносекунд.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 08.02.2022 13:23
 */
public class DurationTest {

    public static void main(String[] args) {
        Duration d0 = Duration.ofNanos(0);
        Duration d1 = Duration.ofMillis(10);
        Duration d2 = Duration.ofDays(1);
        Duration d3 = Duration.ofHours(2);

        System.out.println("d0 = " + d0 + " : " + d0.toNanos());    // 0
        System.out.println("d1 = " + d1 + " : " + d1.toNanos());
        System.out.println("d2 = " + d2 + " : " + d2.toNanos());
        System.out.println("d3 = " + d3 + " : " + d3.toNanos());

        System.out.println("d0.d1 = " + d0.compareTo(d1));      // -10000000
        System.out.println("d3.d2 = " + d3.compareTo(d2));      // -1
        System.out.println("d2.d1 = " + d2.compareTo(d1));      // 1
        System.out.println("d0.d0 = " + d0.compareTo(d0));      // 0

        Duration minWorkNsec = Duration.ofNanos(Integer.MAX_VALUE);
        Duration d10 = Duration.ofNanos(120);
        int ic = minWorkNsec.compareTo(d10);

        System.out.println("minWorkNsec = " + minWorkNsec + " : " + minWorkNsec.toNanos());    // 0
        System.out.println("d10 = " + d10 + " : " + d10.toNanos());
        System.out.println("minWorkNsec.compareTo(d10) = " + ic);

        //if (ic > 0) minWorkNsec = d10;

    }
}
