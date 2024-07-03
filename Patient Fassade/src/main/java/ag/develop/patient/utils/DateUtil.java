package ag.develop.patient.utils;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
public class DateUtil {
    /**
     * Transform date format.
     *
     * @param date formatiert als yyyy-mm-dd
     * @return date formatiert als dd.MM.yyyy, wenn die function erfolgreich war
     * @throws java.text.ParseException .
     */
    public String convertDate(String date) throws ParseException {
        //ISO date format
        SimpleDateFormat iso = new SimpleDateFormat("yyyy-MM-dd");
        //DE date format
        SimpleDateFormat de = new SimpleDateFormat("dd.MM.yyyy");
        //transformed date
        return de.format(iso.parse(date));
    }

}
