package fr.studiochardon.view;

import fr.studiochardon.service.ImageMaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;


public class MainView extends JFrame {

    private String soloPicturesFolderPath;
    private String groupePicturesFolderPath;
    private String backgroundPicturesFolderPath;
    private String targetFolder;


    public MainView() throws HeadlessException {
        this.setLayout(new GridLayout(10, 10));

        this.add(createFileChooser());
        this.add(createGroupPictureChooser());
        this.add(createBackgroundChooser());
        this.add(createTargetFolderChooser());
        this.add(createStartButton());

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 300);
        this.setVisible(true);
    }


    private JButton createGroupPictureChooser() {
        return new JButton(new AbstractAction("définir le fond") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int answer = chooser.showOpenDialog(MainView.this);
                if (answer == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    groupePicturesFolderPath = file.getAbsolutePath();
                }
            }
        });
    }

    private JButton createFileChooser() {
        return new JButton(new AbstractAction("définir dossier classe") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int answer = chooser.showOpenDialog(MainView.this);
                if (answer == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    soloPicturesFolderPath = file.getAbsolutePath();
                }
            }
        });
    }

    private JButton createBackgroundChooser() {
        return new JButton(new AbstractAction("définir photo de groupe") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int answer = chooser.showOpenDialog(MainView.this);
                if (answer == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    backgroundPicturesFolderPath = file.getAbsolutePath();
                }
            }
        });
    }

    private JButton createTargetFolderChooser() {
        return new JButton(new AbstractAction("définir dossier de destination") {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int answer = chooser.showOpenDialog(MainView.this);
                if (answer == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    targetFolder = file.getAbsolutePath();
                }
            }
        });
    }

    private JButton createStartButton() {
        return new JButton(new AbstractAction("lets go") {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println(targetFolder);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            new ImageMaker(soloPicturesFolderPath, groupePicturesFolderPath, backgroundPicturesFolderPath, targetFolder).createImages();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
            }
        });
    }
}
