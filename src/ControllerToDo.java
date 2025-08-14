import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

// Most of this is provisional, the idea is to try to make a ListView of Checkbox instead of String

public class ControllerToDo  implements Initializable {
    @FXML
    private TextField inputTask;

    @FXML
    private ListView<Task> taskList;

    @FXML
    private Label titleLabel;

    @FXML
    private Label creationLabel;

    @FXML
    private TextArea descTextArea;

    @FXML
    private Button saveButton;

    // @FXML
    // private List<String> sample = List.of("Wash the dishes", "Take out the trash", "Water the plants", "Vacuum the floor", "Walk the dog", "Clean the bathroom", "Fold the clothes", "Sweep the floor", "Feed the cat", "Organize the desk");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleItems(false);
        taskList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task previous, Task selected) {
                // manage here the selected task (This is not implying to check the checkbox)
                titleLabel.setText(selected.title);
                descTextArea.setText(selected.description);
                creationLabel.setText("Created on: "+selected.getCreationDate());
                toggleItems(true);
            }
        });
    }

    public void getInputTask(ActionEvent e){
        String input = inputTask.getText();
        inputTask.clear();
        addTask(input);
    }


    public void addTask(String task){
        Task t = new Task(task,"No desc", LocalDateTime.now());
        taskList.getItems().add(t);
    }

    public void toggleItems(boolean t){
        titleLabel.setVisible(t);
        creationLabel.setVisible(t);
        descTextArea.setVisible(t);
        saveButton.setVisible(t);
    }
}
