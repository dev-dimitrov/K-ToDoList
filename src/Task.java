import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class Task {
    public String title;
    public String description;
    public LocalDateTime creation;
    public static DateTimeFormatter f = DateTimeFormatter.ofPattern("y-MM-dd HH:mm:ss");
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
