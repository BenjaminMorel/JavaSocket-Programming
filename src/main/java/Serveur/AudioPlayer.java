package Serveur;

import jdk.jfr.SettingControl;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class AudioPlayer {

    Clip clip;

    String status = "play";

    AudioInputStream audioInputStream;
    static String filePath;

    public AudioPlayer(InputStream is) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        // create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(is);
        //AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());


        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(0);
    }
    public void play() {
        //start the clip
        clip.start();
//        this.currentFrame = this.clip.getMicrosecondPosition();
        status = "play";
    }

    // Method to pause the audio
    public void pause() {
        if (status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
       // this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    public void changeStatus(){
        if(status.equals("paused")) {
            clip.start();
            status = "play";

        } else if(status.equals("play")){
            clip.stop();
            status = "paused";
        }
    }

    public long getSongLength(){
        return clip.getMicrosecondLength();
    }

    //Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public long getCurrentFrame() {
        return clip.getMicrosecondPosition();
    }

    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public void increaseVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        gainControl.setValue(20f * (float) Math.log10(volume));
        gainControl.setValue(getVolume()+10);
    }

    public void diminueVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//        gainControl.setValue(20f * (float) Math.log10(volume));
        gainControl.setValue(getVolume()-10);
    }

}