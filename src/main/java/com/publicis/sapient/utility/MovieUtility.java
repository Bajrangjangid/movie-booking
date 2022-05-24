package com.publicis.sapient.utility;

import java.time.LocalDate;

public class MovieUtility {

    public static LocalDate convertStringToLocalDate(String date) {
        //convert String to LocalDate
        LocalDate localDate = LocalDate.parse(date);
        return localDate;

    }
}
