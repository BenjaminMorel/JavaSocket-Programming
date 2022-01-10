package ClientPackage.View;

import ClientPackage.SpotifyController.AudioPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class SongPage_BAK extends JFrame {
    private JFrame myFrame;
    private JSlider time_Slider, volume_Slider;
    private JLabel songName, label_Image;
    private JPanel song_Panel, btns_Panel;
    private JButton changeStateButton, next_Button, previous_Button, backButton;
    private JButton volume_Up, volume_Down;
    private ImageIcon play_Image, pause_Image, next_Image, previous_Image, music_Image;
    private AudioPlayer player;
    private boolean IsPlaying;
    private Timer myTimer;
    private boolean IsSliderSizeCorrect = false;

    public SongPage_BAK(String name, Socket mySocket) throws IOException {
        //Creation of the UI Objects
        myFrame = new JFrame();
        time_Slider = new JSlider(0,0,0);
        volume_Slider = new JSlider(0,10,5);
        songName = new JLabel(name);
        song_Panel = new JPanel();
        btns_Panel = new JPanel();
//        myFrame.setUndecorated(true);

        //Adding Image Icons
        play_Image = new ImageIcon("src/main/java/ClientPackage.Images/play.png");
        pause_Image = new ImageIcon("src/main/java/ClientPackage.Images/pause.png");
        next_Image = new ImageIcon("src/main/java/ClientPackage.Images/next.png");
        previous_Image = new ImageIcon("src/main/java/ClientPackage.Images/previous.png");
        music_Image = new ImageIcon("src/main/java/ClientPackage.Images/music.jpg");
        label_Image = new JLabel(music_Image);

        //Creation of the buttons
        changeStateButton = new JButton(pause_Image);
        next_Button = new JButton(next_Image);
        previous_Button = new JButton(previous_Image);
        backButton = new JButton("Back");

        volume_Up = new JButton("+");
        volume_Down = new JButton("-");

        changeButtonShape(changeStateButton);
        changeButtonShape(next_Button);
        changeButtonShape(previous_Button);

        //Creation of an audioPlayer each time we select a song from the main page
        System.out.println(mySocket.getReceiveBufferSize());
     //   InputStream is = new BufferedInputStream(mySocket.getInputStream());
   //     player = new AudioPlayer();
   //     try {
   //         player.setAudioInputStream(mySocket);
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
//            unsupportedAudioFileException.printStackTrace();
//        } catch (LineUnavailableException lineUnavailableException) {
//            lineUnavailableException.printStackTrace();
//        }


        //Create a timer that will run every second
        myTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!IsSliderSizeCorrect){
                        time_Slider.setMaximum((int)player.getSongLength());
                        IsSliderSizeCorrect = true;
                    System.out.println(((int)player.getSongLength()) + " longeur song");
                    }
//                System.out.println((int) player.getCurrentFrame() + " position dans le song !");
                    time_Slider.setValue((int)player.getCurrentFrame());
            }
        });

        //Start the thread timer to update the slider every second
        myFrame.setVisible(true);
        myFrame.setSize(500,500);
        myFrame.setLocationRelativeTo(null);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        IsPlaying = true;

        time_Slider.setOrientation(JScrollBar.HORIZONTAL);
        time_Slider.setVisible(true);
        changeStateButton.addActionListener(e -> changeSongStatus());
        backButton.addActionListener(e -> closeSongFrame());

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

        //Setup of the volume slider
        myFrame.add(volume_Down);
        myFrame.add(volume_Slider);
        myFrame.add(volume_Up);

        volume_Down.addActionListener(e -> diminueVolume());
        volume_Up.addActionListener(e -> increaseVolume());

        myFrame.add(backButton);

        myTimer.start();
        player.play();
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

    public void closeSongFrame(){
        myFrame.dispose();
        player.pause();
        myTimer.stop();
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
}
