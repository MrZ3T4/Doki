package dev.z3t4.doki.Utils;

public class DateManager {

    private final String space = " ";
    private final String del = " del ";
    private final String de = " de ";
    private final String alas = " a las ";

    public String convertToNiceDate(String date){
        String deleteTimeZone = date.replace("GMT", "").replace("+0000", "");

        String day = deleteTimeZone.substring(0,3);
        String dayNumber = deleteTimeZone.substring(5,7);
        String month = deleteTimeZone.substring(8,11);
        String year = deleteTimeZone.substring(12,16);
        String hours = deleteTimeZone.substring(17);

        String niceDay = getNiceDay(day);
        String niceMonth = getNiceMonth(month);

        return niceDay + space + dayNumber + de + niceMonth + del + year + alas + hours.toLowerCase();
    }

    private String getNiceDay(String badDay) {

        String day = "";

        switch (badDay){
            case "Mon":
                day = "Lunes";
                break;
            case "Thu":
                day = "Martes";
                break;
            case "Wed":
                day = "Miércoles";
                break;
            case "Tue":
                day = "Jueves";
                break;
            case "Fri":
                day = "Viernes";
                break;
            case "Sat":
                day = "Sabado";
                break;
            case "Sun":
                day = "Domingo";
                break;

        }
        return day;
    }

    private String getNiceMonth(String badMonth){

        String month = "";

        switch (badMonth){
            case "Jan":
                month = "Enero";
                break;
            case "Feb":
                month = "Febrero";
                break;
            case "Mar":
                month = "Marzo";
                break;
            case "Apr":
                month = "Abril";
                break;
            case "May":
                month = "Mayo";
                break;
            case "Jun":
                month = "Junio";
                break;
            case "Jul":
                month = "Julio";
                break;
            case "Aug":
                month = "Agosto";
                break;
            case "Sep":
                month = "Septiembre";
                break;
            case "Oct":
                month = "Octubre";
                break;
            case "Nov":
                month = "Noviembre";
                break;
            case "Dec":
                month = "Diciembre";
                break;

        }
        return month;
    }

}
