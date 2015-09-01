package org.debnil.mp3panda.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.debnil.mp3panda.meta.transformer.MetaTransformationUtil;
import org.debnil.mp3panda.meta.transformer.MetaTransformer;
import org.debnil.mp3panda.mp3.MP3File;
import org.debnil.mp3panda.util.AudioUtils;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * Created by debnil on 23-Aug-15.
 */
public class MainAppController implements Initializable {
    protected Map<String, MetaTransformer> transformationRules;

    @FXML
    private TextField txtFolder;

    @FXML
    private CheckBox chkSubDirectory;

    @FXML
    private TableView tblSongs;

    @FXML
    private TableView tblDetails;

    @FXML
    private ComboBox cmbTransform;

    @FXML
    protected void btnSelectDirectoryAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(txtFolder.getScene().getWindow());

        if (selectedDirectory != null) {
            txtFolder.setText(selectedDirectory.getAbsolutePath());

            loadSongs();
        }
    }

    protected void loadSongs() {
        File folder = new File(txtFolder.getText());
        boolean recursive = chkSubDirectory.isSelected();

        ObservableList<MP3File> list = FXCollections.observableArrayList();
        tblSongs.setItems(list);

        new Thread(() -> AudioUtils.populateFileList(folder, recursive, list)).start();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblSongs.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                    tblDetails.setItems(newVal != null ? FXCollections.observableArrayList(((MP3File) newVal).getMetadataFields()) : FXCollections.emptyObservableList());
                    applyTransformationRule();
                }
        );

        transformationRules = MetaTransformationUtil.loadTransformationRules();

        cmbTransform.setItems(FXCollections.observableArrayList(transformationRules.keySet()));

        cmbTransform.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> applyTransformationRule());
    }

    protected void applyTransformationRule() {
        MP3File selectedFile = (MP3File) tblSongs.getSelectionModel().getSelectedItem();
        String selectedRule = (String) cmbTransform.getSelectionModel().getSelectedItem();

        if (selectedFile != null && selectedRule != null) {
            MetaTransformer rule = transformationRules.get(selectedRule);
            rule.transform(selectedFile);
        }
    }
}
