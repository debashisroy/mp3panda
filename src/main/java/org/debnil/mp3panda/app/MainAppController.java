package org.debnil.mp3panda.app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import java.io.File;


/**
 * Created by debnil on 23-Aug-15.
 */
public class MainAppController {
    @FXML
    private TextField txtFolder;


    @FXML
    protected void btnSelectDirectoryAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(txtFolder.getScene().getWindow());

        if (selectedDirectory != null) {
            txtFolder.setText(selectedDirectory.getAbsolutePath());
        }
    }
}
