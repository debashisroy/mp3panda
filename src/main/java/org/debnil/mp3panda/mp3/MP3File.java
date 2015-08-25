package org.debnil.mp3panda.mp3;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.debnil.mp3panda.util.AudioUtils;
import org.jaudiotagger.tag.FieldKey;

import java.io.File;
import java.util.Map;

/**
 * Created by debnil on 22-Aug-15.
 */
public class MP3File {
    private final SimpleStringProperty name;
    private final SimpleStringProperty directory;
    private final SimpleBooleanProperty checked;

    protected File file;
    protected Map<FieldKey, String> metadata;

    public MP3File(File file) {
        this.file = file;
        try {
            metadata = AudioUtils.load(file);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }

        name = new SimpleStringProperty(file.getName());
        directory = new SimpleStringProperty(file.getParent());
        checked = new SimpleBooleanProperty(false);
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
}
