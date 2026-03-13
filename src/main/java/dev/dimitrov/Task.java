package dev.dimitrov;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task implements Serializable {
    public String title;
    public String description;
    public LocalDateTime creation;
    public static DateTimeFormatter f = DateTimeFormatter.ofPattern(Constants.DATE_PATTERN);
    public Task(String t, String d, LocalDateTime ldt){
        title = t;
        description = d;
        creation = ldt;
    }

    @Override
    public String toString(){
        return title;
    }

    public String getCreationDate(){
        return creation.format(f);
    }

}
