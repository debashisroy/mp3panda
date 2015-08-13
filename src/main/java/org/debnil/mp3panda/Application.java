package org.debnil.mp3panda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by debnil on 8/2/2015.
 */
public class Application extends Component {
    private JPanel directorySelectorPanel;
    private JTextField txtSelectedDirectory;
    private JButton btnSelectDirectory;
    private JCheckBox includeSubdirectoryCheckBox;
    private JTable tblSongs;
    private JPanel rootPanel;


    public Application() {

        btnSelectDirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser j = new JFileChooser();
                j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                Integer opt = j.showSaveDialog(btnSelectDirectory);
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }
}
