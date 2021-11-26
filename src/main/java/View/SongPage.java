package View;

import Serveur.AudioPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class SongPage {
    private JFrame myFrame;
    private JSlider mySlider;
    private JLabel songName;
    public JButton changeStateButton;
    private JButton backButton;
    private AudioPlayer player;
    private boolean IsPlaying;
    private Timer myTimer;
    private boolean IsSliderSizeCorrect = false;



    public SongPage(String name, InputStream is){
        myFrame = new JFrame();
        mySlider = new JSlider(0,36000,0);
        songName = new JLabel(name);
        changeStateButton = new JButton(new ImageIcon("D:\\pause.png"));
        backButton = new JButton("Back");

        //create an audioPlayer each time we select a song from the main page
        player = new AudioPlayer();
        try {
            player.setAudioInputStream(is);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
            unsupportedAudioFileException.printStackTrace();
        } catch (LineUnavailableException lineUnavailableException) {
            lineUnavailableException.printStackTrace();
        }
        player.play();
        // create a timer that will run every second
        myTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!IsSliderSizeCorrect){
                        mySlider.setMaximum((int)player.getSongLength());
                        IsSliderSizeCorrect = true;
                    System.out.println(((int)player.getSongLength()) + " longeur song");
                    }
                System.out.println((int) player.getCurrentFrame() + " position dans le song !");
                    mySlider.setValue((int)player.getCurrentFrame());
            }
        });

        //Start the thread timer to update the slider every second

        myFrame.setVisible(true);
        myFrame.setSize(500,500);
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(new BorderLayout());

        this.player = player;
        IsPlaying = true;

        mySlider.setOrientation(JScrollBar.HORIZONTAL);
        changeStateButton.addActionListener(e ->
                changeSongStatus()
        );
        backButton.addActionListener(e ->
               closeSongFrame());

        mySlider.setVisible(true);
        myFrame.add(songName,BorderLayout.NORTH);
        myFrame.add(mySlider,BorderLayout.SOUTH);
        myFrame.add(changeStateButton, BorderLayout.CENTER);
        myFrame.add(backButton, BorderLayout.EAST);

        myTimer.start();
    }

    public void changeSongStatus(){
        player.changeStatus();
        if(IsPlaying) {
            changeStateButton.setIcon(new ImageIcon("D:\\play.png"));
            IsPlaying = false;
        }else{
            changeStateButton.setIcon(new ImageIcon("D:\\pause.png"));
            IsPlaying = true;
        }


    }

    public void closeSongFrame(){
        myFrame.dispose();
        player.pause();
        myTimer.stop();
    }

}
