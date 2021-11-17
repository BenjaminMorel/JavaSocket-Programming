package View;

import Serveur.AudioPlayer;

import javax.swing.*;
import java.awt.*;

public class SongPage {
    private JFrame myFrame;
    private JScrollBar myScrollBar;
    private JLabel songName;
    public JButton changeStateButton;

    public SongPage(String name,AudioPlayer player){
        myFrame = new JFrame();
        myScrollBar = new JScrollBar();
        songName = new JLabel(name);
        changeStateButton = new JButton("Pause");

        myFrame.setVisible(true);
        myFrame.setSize(500,500);
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(new BorderLayout());

        myScrollBar.setOrientation(JScrollBar.HORIZONTAL);
        changeStateButton.addActionListener(e -> player.changeStatus());


        myScrollBar.setVisible(true);
        myScrollBar.setUnitIncrement(1);
        myFrame.add(songName,BorderLayout.NORTH);
        myFrame.add(myScrollBar,BorderLayout.SOUTH);
        myFrame.add(changeStateButton, BorderLayout.CENTER);

    }
}
