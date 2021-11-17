package Serveur;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AudioPlayer {

    Long currentFrame;
    Clip clip;

    String status = "play";

    AudioInputStream audioInputStream;
    static String filePath;

    public AudioPlayer() {
    }

    public void setAudioInputStream(InputStream is) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(is);

        clip = AudioSystem.getClip();

        clip.open(audioInputStream);
    }

    public void play() {
        //start the clip
        clip.start();

        status = "play";
    }

    // Method to pause the audio
    public void pause() {
        if (status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    public void changeStatus(){
        if(status.equals("paused")) {
            clip.start();

            status = "play";
        }else if(status.equals("play")){
            clip.stop();
            status = "paused";
        }
    }

    public long getSongLength(){
        return clip.getMicrosecondLength();
    }
    // Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}