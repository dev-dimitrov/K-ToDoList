import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

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

    @FXML
    private Button markAsDoneButton;

    @FXML
    private Task selectedTask;

    @FXML
    private Label statusLabel;
    
    @FXML
    private Button toggleButton;

    private ArrayList<ArrayList<Task>> tasks; // 0 index is saving to do tasks and 1 done tasks

    private boolean todoShowing;

    // @FXML
    // private List<String> sample = List.of("Wash the dishes", "Take out the trash", "Water the plants", "Vacuum the floor", "Walk the dog", "Clean the bathroom", "Fold the clothes", "Sweep the floor", "Feed the cat", "Organize the desk");
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        todoShowing = true;
        toggleItems(false);
        loadTasks("tasks.bin");
        taskList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Task>() {
            @Override
            public void changed(ObservableValue<? extends Task> observableValue, Task previous, Task selected) {
                System.out.println("showing todo? "+todoShowing);
                // manage here the selected task (This is not implying to check the checkbox)
                if(selected != null){
                    selectedTask = selected;
                    titleLabel.setText(selected.title);
                    descTextArea.setText(selected.description);
                    creationLabel.setText("Created on: "+selected.getCreationDate());
                    toggleItems(true);
                }
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
        if(todoShowing){
            tasks.get(0).add(t);
        }
        else{
            tasks.get(1).add(t);
        }
        taskList.getItems().add(t);
    }

    public void toggleItems(boolean t){
        titleLabel.setVisible(t);
        creationLabel.setVisible(t);
        descTextArea.setVisible(t);
        saveButton.setVisible(t);
        markAsDoneButton.setVisible(t);
        statusLabel.setVisible(false);
    }

    public void saveTasks(ActionEvent e) {
        selectedTask.description = descTextArea.getText();
        try(ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("tasks.bin"))){
            // Make it ArrayList first bc it is complicated to save an ObservableList.
            List<Task> l = new ArrayList<>(taskList.getItems());
            if(todoShowing){
                tasks.get(0).clear();
                tasks.get(0).addAll(l);
            }
            else{
                tasks.get(1).clear();
                tasks.get(1).addAll(l);
            }
            o.writeObject(tasks);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            showStatus("Saving failed","#b80f04");
        }
        showStatus("Changes  saved!","#04a429");
    }

    public void loadTasks(String fileName){
        try(ObjectInputStream o = new ObjectInputStream(new FileInputStream("tasks.bin"))){
            ArrayList<ArrayList<Task>> l = (ArrayList<ArrayList<Task>>)o.readObject();
            tasks = l;

            if(todoShowing){
                taskList.getItems().addAll(l.get(0));
            }
            else{
                taskList.getItems().addAll(l.get(1));
            }
        }
        catch(IOException | ClassNotFoundException ex){
            tasks = new ArrayList<>();
            tasks.add(new ArrayList<>());
            tasks.add(new ArrayList<>());
            ex.printStackTrace();
        }
    }

    public void showStatus(String msg, String hexColor){
        statusLabel.setVisible(false);
        statusLabel.setText(msg);
        statusLabel.setStyle("-fx-text-fill: "+hexColor);
        statusLabel.setVisible(true);
    }

    public void switchTasksList(ActionEvent e){
        toggleItems(false);
        String currentMode = toggleButton.getText();
        toggleButton.setText(currentMode.equals("Show done") ? "Show to do" : "Show done");
        taskList.getItems().clear();
        if(currentMode.equals("Show to do")){
            taskList.getItems().addAll(tasks.get(0));
            toggleButton.setText("Show done");
            inputTask.setDisable(false);
            todoShowing = true;
        }
        else{
            taskList.getItems().addAll(tasks.get(1));
            toggleButton.setText("Show to do");
            inputTask.setDisable(true);
            todoShowing = false;

        }
    }
}
