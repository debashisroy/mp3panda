package org.debnil.mp3panda.mp3;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.debnil.mp3panda.util.AudioUtils;
import org.jaudiotagger.tag.FieldKey;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by debnil on 22-Aug-15.
 */
public class MP3File {
    public static final String FILE_NAME = "FILE_NAME";
    private final SimpleStringProperty name;
    private final SimpleStringProperty directory;
    private final SimpleBooleanProperty checked;

    protected File file;
    protected Map<String, AudioMetadataField> metadata = new HashMap<>();

    public MP3File(File file) {
        this.file = file;
        name = new SimpleStringProperty(file.getName());
        directory = new SimpleStringProperty(file.getParent());
        checked = new SimpleBooleanProperty(true);
        loadMetadata();
    }

    public String getName() {
        return name.get();
    }

    public String getDirectory() {
        return directory.get();
    }

    public SimpleBooleanProperty checkedProperty() {
        return this.checked;
    }

    public java.lang.Boolean getChecked() {
        return this.checkedProperty().get();
    }

    public void setChecked(final java.lang.Boolean checked) {
        this.checkedProperty().set(checked);
    }

    public Collection<AudioMetadataField> getMetadataFields() {
        return metadata.values();
    }

    public Map<String, AudioMetadataField> getMetadata() {
        return metadata;
    }

    private void loadMetadata() {
        try {
            metadata.put(FILE_NAME, new AudioMetadataField(FILE_NAME, file.getName()));
            Map<FieldKey, String> meta = AudioUtils.load(file);
            for (FieldKey key : meta.keySet()) {
                metadata.put(key.name(), new AudioMetadataField(key.name(), meta.get(key)));
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public void saveFile(File targetDir) {
        File target = new File(targetDir, metadata.get(FILE_NAME).getNewValue());

        Map<FieldKey, String> fieldMap = new HashMap<>();
        for (AudioMetadataField field : metadata.values()) {
            if (!field.getField().equals(FILE_NAME)) {
                fieldMap.put(FieldKey.valueOf(field.getField()), field.getNewValue());
            }
        }

        try {
            AudioUtils.update(file, target, fieldMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
