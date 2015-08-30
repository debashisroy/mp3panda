package org.debnil.mp3panda.mp3;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Created by debnil on 27-Aug-15.
 */
public class AudioMetadataField {
    private final SimpleStringProperty field;
    private final SimpleStringProperty currentValue;
    private final SimpleStringProperty newValue;
    private final SimpleBooleanProperty error;

    public AudioMetadataField(String field, String value) {
        this.field = new SimpleStringProperty(field);
        this.currentValue = new SimpleStringProperty(value);
        this.newValue = new SimpleStringProperty(value);
        this.error = new SimpleBooleanProperty(false);
    }

    public String getField() {
        return field.get();
    }

    public String getCurrentValue() {
        return currentValue.get();
    }

    public String getNewValue() {
        return newValue.get();
    }

    public void setNewValue(String newValue) {
        this.newValue.set(newValue);
    }

    public void setError(boolean error) {
        this.error.set(error);
    }
}
