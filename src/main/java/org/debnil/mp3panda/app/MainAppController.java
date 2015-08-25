package org.debnil.mp3panda.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.debnil.mp3panda.mp3.MP3File;
import org.debnil.mp3panda.util.AudioUtils;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by debnil on 23-Aug-15.
 */
public class MainAppController implements Initializable{
    @FXML
    private TextField txtFolder;

    @FXML
    private CheckBox chkSubDirectory;

    @FXML
    private TableView tblSongs;

    @FXML
    protected void btnSelectDirectoryAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(txtFolder.getScene().getWindow());

        if (selectedDirectory != null) {
            txtFolder.setText(selectedDirectory.getAbsolutePath());

            loadSongs();
        }

    }

    protected void loadSongs(){
        File folder = new File(txtFolder.getText());
        boolean recursive = chkSubDirectory.isSelected();

        List<MP3File> files = AudioUtils.populateFileList(folder, recursive);

        tblSongs.setItems(FXCollections.observableArrayList(files));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        tblSongs.setItems(songList);
    }
}
