package goodwill.robyn.thediskwizard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/fxml/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 725, 450);
        stage.setResizable(false);
        stage.setTitle("Disk Wizard");
        Image icon = new Image("file:src/main/resources/img/icon.png");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}