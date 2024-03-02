package org.example.pages.components;

import static com.codeborne.selenide.Selenide.$;

public class CalendarComponent {
    public void setDate(String day, String month, int year) {
        day =Integer.parseInt(day) > 9 ? day : "0" + day;
        $(".react-datepicker__month-select").selectOption(month);
        $(".react-datepicker__year-select").selectOption(Integer.toString(year));
        $(".react-datepicker__day--0" + day+":not(.react-datepicker__day--outside-month)").click();
    }
}
