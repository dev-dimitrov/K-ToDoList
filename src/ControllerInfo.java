import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class ControllerInfo implements Initializable{
    @FXML
    private Button back2App;
    @FXML
    private Label textLabel;
    @FXML
    private ImageView imageLogo;

    private Stage stage;

    private Scene scene;

    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image i = new Image(getClass().getResourceAsStream("resources/logo.png"));
        imageLogo.setImage(i);
    }


    public void openApp(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/screen1.fxml"));
        root = loader.load();
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("resources/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void openLink(ActionEvent e){
        Hyperlink a = (Hyperlink) e.getSource();
        String link = a.getText().contains("Delete") ? "https://icons8.com/icon/67884/delete" : "https://icons8.com/icon/59817/info";
        ControllerToDo.linkOpener(link);
    }
}
