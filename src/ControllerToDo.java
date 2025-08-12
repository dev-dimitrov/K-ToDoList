import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

// Most of this is provisional, the idea is to try to make a ListView of Checkbox instead of String

public class ControllerToDo  implements Initializable {
    @FXML
    private TextField inputTask;

    @FXML
    private ListView<CheckBox> taskList;

    // @FXML
    // private List<String> sample = List.of("Wash the dishes", "Take out the trash", "Water the plants", "Vacuum the floor", "Walk the dog", "Clean the bathroom", "Fold the clothes", "Sweep the floor", "Feed the cat", "Organize the desk");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Al empezar: "+taskList.getStyle());
        taskList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CheckBox>() {
            @Override
            public void changed(ObservableValue<? extends CheckBox> observableValue, CheckBox s, CheckBox t1) {
                // manage here the selected task (This is not implying to check the checkbox)
            }
        });
    }

    public void getInputTask(ActionEvent e){
        String input = inputTask.getText();
        inputTask.clear();
        addTask(input);
    }


    public void addTask(String task){
        CheckBox b = new CheckBox();
        b.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        b.setPrefWidth(170);
        b.setAlignment(Pos.CENTER_RIGHT);
        b.setText(task);
        taskList.getItems().add(b);
        System.out.println("Al meter un checkbox: "+taskList.getStyle());
    }
}
