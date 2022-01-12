package Client.Controller;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class AudioPlayer {

    Clip clip;
    String status = "play";

    AudioInputStream audioInputStream;

    public AudioPlayer(InputStream is) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        //Create AudioInputStream object
        audioInputStream = AudioSystem.getAudioInputStream(is);

        //Create clip reference
        clip = AudioSystem.getClip();

        //Open audioInputStream to the clip
        clip.open(audioInputStream);
        clip.loop(0);
    }

    /**
     * Method to start the audio (clip)
     */
    public void play() {
        clip.start();
        status = "play";
    }

    /**
     * Method to pause the audio (clip)
     */
    public void pause() {
        if (status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
        clip.stop();
        status = "paused";
    }

    /**
     * Method to change the status of the audio (clip)
     */
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

    public long getCurrentFrame() {
        return clip.getMicrosecondPosition();
    }

    /**
     * Method to get the current volume of the clip, and will be used to be increased/decreased
     * @return
     */
    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    /**
     * Method to increase the audio (clip)
     */
    public void increaseVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(getVolume()+4);
    }

    /**
     * Method to diminue the audio (clip)
     */
    public void diminueVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(getVolume()-4);


    }

}