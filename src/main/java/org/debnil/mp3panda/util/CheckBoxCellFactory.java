package org.debnil.mp3panda.util;

import javafx.scene.control.TableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;
import org.debnil.mp3panda.mp3.MP3File;

/**
 * Created by debnil on 25-Aug-15.
 */
public class CheckBoxCellFactory implements Callback {
    @Override
    public TableCell call(Object param) {
        CheckBoxTableCell<MP3File, Boolean> checkBoxCell = new CheckBoxTableCell();
        return checkBoxCell;
    }
}