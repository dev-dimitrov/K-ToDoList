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

    private ArrayList<ArrayList<Task>> tasks; // 0 index is for saving to do tasks and 1 done tasks

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
                // manage here the selected task (This is not implying to check the checkbox)
                System.out.println("Current selected: "+selected);
                System.out.println("Previous selected: "+previous);

                if(selected != null){
                    selectedTask = selected; // save the selected Task in a class var.
                    titleLabel.setText(selected.title);
                    descTextArea.setText(selected.description);
                    creationLabel.setText("Created on: "+selected.getCreationDate());
                    toggleItems(true);
                }
            }
        });
    }

    // Catches the input form the TextField
    public void getInputTask(ActionEvent e){
        String input = inputTask.getText();
        if(input.isBlank()){
            showStatus("Please type a title", "#b80f04");
        }
        else{
            statusLabel.setVisible(false);
            inputTask.clear();
            addTask(input);
        }

    }

    // Adds a task to the list
    public void addTask(String task){
        Task t = new Task(task,"No desc", LocalDateTime.now());

        // Adding the task in
        if(todoShowing){
            tasks.get(0).add(t);
        }
        else{
            tasks.get(1).add(t);
        }
        // Adding to the on-screen list
        taskList.getItems().add(t);
        taskList.getSelectionModel().select(t);
    }

    // A quick way to show or hide all items that represent every task
    public void toggleItems(boolean t){
        titleLabel.setVisible(t);
        creationLabel.setVisible(t);
        descTextArea.setVisible(t);

        if(!todoShowing){ // if the done list is showing, don't show the save button
            saveButton.setVisible(false);
        }
        else{
            saveButton.setVisible(t);
        }
        saveButton.setVisible(t);
        markAsDoneButton.setVisible(t);
        statusLabel.setVisible(false);

    }

    // Changes the look and behavior of the markAsDoneButton depending if tis showing done or to do list
    // true is for the done list and false is for the to do list
    public void toggleLayout(boolean t){
        descTextArea.setDisable(!t);
        if(t){
            markAsDoneButton.setText("Mark as done");
            markAsDoneButton.setOnAction(e -> markAsDone(e));
        }
        else{
            markAsDoneButton.setText("Mark as To do");
            markAsDoneButton.setOnAction(e -> markAsTodo(e));
        }
    }

    // Saves all the tasks from a specified group (to do or done)

    public void saveTasks(ActionEvent e) {
        // Updates the description of the selected task
        selectedTask.description = descTextArea.getText();
        int status = -1;
        try(ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("tasks.bin"))){
            // Make it ArrayList first bc it is complicated to save an ObservableList.
            List<Task> l = new ArrayList<>(taskList.getItems());

            if(todoShowing){ // If the list of to do is showing, saves
                tasks.get(0).clear();
                tasks.get(0).addAll(l);
            }
            else{
                tasks.get(1).clear();
                tasks.get(1).addAll(l);
            }

            o.writeObject(tasks); // Writes the entire Arraylist w both
            status = 0;
        }
        catch (IOException ex) {
            ex.printStackTrace();
            showStatus("Saving failed","#b80f04");
        }
        if(status == 0){
            showStatus("Changes  saved!","#04a429");
        }

    }


    public void loadTasks(String fileName){
        try(ObjectInputStream o = new ObjectInputStream(new FileInputStream("tasks.bin"))){
            ArrayList<ArrayList<Task>> l = (ArrayList<ArrayList<Task>>)o.readObject();
            tasks = l;

            // Depending of what it is showing, add one or other list to the Listview
            if(todoShowing){
                taskList.getItems().addAll(l.get(0));
            }
            else{
                taskList.getItems().addAll(l.get(1));
            }
        }
        catch(IOException | ClassNotFoundException ex){
            // If the load is failed, prepare the List of lists
            tasks = new ArrayList<>();
            tasks.add(new ArrayList<>());
            tasks.add(new ArrayList<>());
            ex.printStackTrace();
        }
    }

    // Shows a message with an hexColor
    public void showStatus(String msg, String hexColor){
        statusLabel.setVisible(false);
        statusLabel.setText(msg);
        statusLabel.setStyle("-fx-text-fill: "+hexColor);
        statusLabel.setVisible(true);
    }

    public void switchTasksList(ActionEvent e){
        toggleItems(false); // Hide everything

        toggleButton.setText(todoShowing ? "Show done" : "Show to do");
        // Remove all the items for the ListView
        taskList.getItems().clear();

        if(todoShowing){
            taskList.getItems().addAll(tasks.get(1));
            toggleButton.setText("Show to do");
            inputTask.setDisable(true);
            todoShowing = false;
            toggleLayout(false);
        }
        else{
            taskList.getItems().addAll(tasks.get(0));
            toggleButton.setText("Show done");
            inputTask.setDisable(false);
            todoShowing = true;
            toggleLayout(true);
        }
    }

    /*Why Im saving the selectedTask in an aux variable instead of just using it to remove and add in lists:
    * When you remove an item from tasklist, the changed() method triggers and the selectedtask var is reasigned to another task,
    * Without the aux var It will remove and add different tasks that the actual selected*/
    public void markAsDone(ActionEvent e){
        Task aux = selectedTask;
        taskList.getItems().remove(aux);
        tasks.get(0).remove(aux);
        tasks.get(1).add(aux);


        if(taskList.getItems().isEmpty()){
            toggleItems(false);
            selectedTask = null;
        }
        showStatus("Moved to done list!","#04a429");
    }

    
    public void markAsTodo(ActionEvent e){
        Task aux = selectedTask;
        taskList.getItems().remove(aux);
        tasks.get(1).remove(aux);
        tasks.get(0).add(aux);
        if(taskList.getItems().isEmpty()){
            toggleItems(false);
            selectedTask = null;
        }
        showStatus("Moved to do list!","#04a429");

    }
}
