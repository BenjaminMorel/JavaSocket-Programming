package Client.View;

import Client.Controller.AudioPlayer;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

public class SongPage {

    //Icon element
    private ImageIcon play_Image, pause_Image, next_Image, previous_Image, music_Image;
    private JLabel label_Image,songName;
    private JPanel song_Panel, btns_Panel;

    //GUI elements
    private JFrame myFrame;
    private JButton changeStateButton, next_Button, previous_Button, backButton, volume_Up, volume_Down;
    private JSlider time_Slider;

    //Song playing
    private AudioPlayer player;

    //Data element
    private boolean IsPlaying, IsSliderSizeCorrect = false;
    private Timer myTimer;

    public SongPage(InputStream is, String songName) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        setAllGUIPart(songName);
        IsPlaying = true;

        //Add events to all buttons
        backButton.addActionListener(e -> {
            myFrame.dispose();
            player.pause();
            player = null;
        });

        //Button to change from play to pause and pause to play
        changeStateButton.addActionListener(e -> changeSongStatus());
        volume_Down.addActionListener(e -> diminueVolume());
        volume_Up.addActionListener(e -> increaseVolume());

        myTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!IsSliderSizeCorrect){
                    time_Slider.setMaximum((int)player.getSongLength());
                    IsSliderSizeCorrect = true;
                    System.out.println(((int)player.getSongLength()) + " longeur song");
                }
                time_Slider.setValue((int)player.getCurrentFrame());
            }
        });

        player = new AudioPlayer(is);

        myTimer.start();
        player.play();

    }

    public void setAllGUIPart(String name){

        //Creation of the UI Objects
        myFrame = new JFrame();
        time_Slider = new JSlider(0,0,0);
        songName = new JLabel(name);
        song_Panel = new JPanel();
        btns_Panel = new JPanel();

        myFrame = new JFrame();
        myFrame.setVisible(true);
        myFrame.setSize(500,500);
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creation of all the icons for the graphic parts
        play_Image = new ImageIcon(ClassLoader.getSystemResource("Images/play.png"));
        pause_Image = new ImageIcon(ClassLoader.getSystemResource("Images/pause.png"));
        next_Image = new ImageIcon(ClassLoader.getSystemResource("Images/next.png"));
        previous_Image = new ImageIcon(ClassLoader.getSystemResource("Images/previous.png"));
        music_Image = new ImageIcon(ClassLoader.getSystemResource("Images/music.jpg"));
        label_Image = new JLabel(music_Image);

        //Creation of JButtons to interact with the page
        changeStateButton = new JButton(pause_Image);
        next_Button = new JButton(next_Image);
        previous_Button = new JButton(previous_Image);
        backButton = new JButton("Back");
        volume_Up = new JButton("+");
        volume_Down = new JButton("-");

        changeButtonShape(changeStateButton);
        changeButtonShape(next_Button);
        changeButtonShape(previous_Button);

        time_Slider.setOrientation(JScrollBar.HORIZONTAL);
        time_Slider.setVisible(true);

        //Setup of the layout
        myFrame.setLayout(new FlowLayout());

        //Setup of the song Panel
        song_Panel.add(label_Image);
        song_Panel.setPreferredSize(new Dimension(500, 300));
        myFrame.add(song_Panel);
        myFrame.add(songName,BorderLayout.NORTH);
        myFrame.add(time_Slider,BorderLayout.SOUTH);

        //Setup of the btns_Panel
        previous_Button.setSize(new Dimension(40, 40));
        changeStateButton.setSize(new Dimension(40, 40));
        next_Button.setSize(new Dimension(40, 40));

        btns_Panel.add(previous_Button, BorderLayout.WEST);
        btns_Panel.add(changeStateButton, BorderLayout.CENTER);
        btns_Panel.add(next_Button, BorderLayout.EAST);
        btns_Panel.setPreferredSize(new Dimension(500, 80));
        myFrame.add(btns_Panel);

        myFrame.add(volume_Down);
        myFrame.add(volume_Up);

        myFrame.add(backButton);
    }

    public void changeSongStatus(){
        player.changeStatus();
        if (IsPlaying) {
            changeStateButton.setIcon(play_Image);
            IsPlaying = false;
        } else{
            changeStateButton.setIcon(pause_Image);
            IsPlaying = true;
        }
    }

    public void changeButtonShape(JButton button) {
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    }

    public void increaseVolume() {
        System.out.println(player.getVolume());
        player.increaseVolume();
    }

    public void diminueVolume() {
        System.out.println(player.getVolume());
        player.diminueVolume();
    }

    public JButton getPrevious_Button(){
        return previous_Button;
    }

    public JButton getNext_Button(){
        return next_Button;
    }

    public AudioPlayer getPlayer(){
        return player;
    }

    public JFrame getMyFrame(){
        return myFrame;
    }

}
