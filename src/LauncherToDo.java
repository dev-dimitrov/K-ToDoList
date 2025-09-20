import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
// import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class LauncherToDo extends Application {
    
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("resources/screen1.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("resources/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setResizable(false);
            System.out.println("Running, running!!");

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
