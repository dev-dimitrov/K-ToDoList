import java.time.LocalDateTime;

public class Task {
    private String title;
    private String description;
    private LocalDateTime creation;

    public Task(String t, String d, LocalDateTime ldt){
        title = t;
        description = d;
        creation = ldt;
    }

    @Override
    public String toString(){
        return title;
    }
}
