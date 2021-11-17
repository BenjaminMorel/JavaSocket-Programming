package View;

import Serveur.AudioPlayer;
import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;

public class NewMainPage {

    private JFrame myFrame;
    private JButton quitButton;
    private ArrayList<JButton> allSong = new ArrayList<>();
    private boolean continuePlaying = true;
    private SongPage mySongPage;
    private AudioPlayer player;

    public NewMainPage(String title, PrintWriter pout, int sizeGridLayout,ArrayList<NewSong> allSong,AudioPlayer player){
        myFrame = new JFrame();
        myFrame.setTitle(title);
        myFrame.setVisible(true);
        myFrame.setSize(500,500);
        myFrame.setLocationRelativeTo(null);
        myFrame.setLayout(new GridLayout(sizeGridLayout,1));
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        for(int i = 0; i < sizeGridLayout-1; i++){
            String songTitle = allSong.get(i).getSongTitle();
            JButton myJButton = new JButton(songTitle);
            myJButton.addActionListener(e -> {
                pout.println(songTitle);
                mySongPage = new SongPage(songTitle,player);
            });

            myFrame.add(myJButton);
        }

        quitButton = new JButton("quit");
        quitButton.setBackground(new Color(10,150,10));

        myFrame.add(quitButton);

        quitButton.addActionListener(e -> {
            myFrame.dispose();
            pout.println("quit");
        });
    }

    public boolean GetContinuePlaying(){
        return continuePlaying;
    }

}
