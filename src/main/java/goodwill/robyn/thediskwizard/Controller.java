package goodwill.robyn.thediskwizard;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;

/**
 * Connects the UI to the FileManager class.
 * @see     FileManager
 * @since   v1.0
 */
public class Controller extends Application{
    FileManager fm = new FileManager();
    @FXML
    TextArea output = new TextArea();
    @FXML
    ComboBox<String> driveSelection = new ComboBox<>();
    @FXML
    TextField volumeLabel = new TextField();
    @FXML
    ComboBox<String> driveFileSysSelection = new ComboBox<>();
    @FXML
    CheckBox quickFormatBox = new CheckBox();

    /**
     * Runs when the GUI is created, acting in a similar way to a constructor.
     * @since   v1.0
     */
    @FXML
    public void initialize(){
        // Setting the file system selection items.
        driveFileSysSelection.getItems().addAll(
                "FAT", "FAT32", "exFAT", "NTFS", "UDF", "ReFS"
        );
    }


    /**
     * A method that updates the list of drives displayed in the driveSelection ComboBox.
     * @see     FileManager
     * @since   v1.0
     */
    @FXML
    protected void refreshDriveList(){
        driveSelection.getItems().clear();
        for (File i: fm.getDrivesAsFile()) {
            driveSelection.getItems().add(i.getPath());
        }
    }

    /**
     * Takes the data inputted into the GUI and starts the formatting process.
     * @see     FileManager
     * @see     FileManager#format(String, String, String, boolean, TextArea)
     * @since   v1.0
     */
    @FXML
    protected void formatButtonAction() {
        String driveSelect = driveSelection.getSelectionModel().getSelectedItem();
        String fileSys = driveFileSysSelection.getSelectionModel().getSelectedItem();
        String volume = volumeLabel.getText();
        boolean quickFormatSelection = quickFormatBox.isSelected();

        Thread formatThread = new Thread(() -> {
            try{
                fm.format(driveSelect, fileSys, volume, quickFormatSelection, output);
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        formatThread.start();
    }
}

