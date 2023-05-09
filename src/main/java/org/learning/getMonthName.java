package org.learning;

import java.time.Year;
import java.time.YearMonth;

public class getMonthName {
    public static void main(String[] args) {
       String lastMonthName = YearMonth.now().minusMonths(1).getMonth().toString();
       String year = Year.now().toString();
        System.out.println(lastMonthName + ","+ year );
    }
}
