package org.debnil.mp3panda;

import com.google.common.io.Files;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by debnil on 8/2/2015.
 */
public class Application extends Component {


    private JPanel directorySelectorPanel;
    private JTextField txtSelectedDirectory;
    private JButton btnSelectDirectory;
    private JCheckBox chkIncludeSubdirectory;
    private JTable tblSongs;
    private JPanel rootPanel;


    public Application() {
        btnSelectDirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser(txtSelectedDirectory.getText());
                j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                Integer opt = j.showOpenDialog(btnSelectDirectory);

                if (opt == JFileChooser.APPROVE_OPTION) {
                    File file = j.getSelectedFile();
                    txtSelectedDirectory.setText(file.getAbsolutePath());
//                    populateFileList();
                }
            }
        });

//        chkIncludeSubdirectory.addActionListener(event -> populateFileList());
    }


    public JPanel getRootPanel() {
        return rootPanel;
    }
}
