package view;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.*;

public class SoundFX {

    /**
     * play a specific sound according to the event
     * @param event
     */
    public void playSound(String event){

        try {
            InputStream in = this.getClass().getResourceAsStream("/sound/surprise.wav");

            switch(event){
                case "loginValid":
                    in = this.getClass().getResourceAsStream("/sound/login.wav");
                    break;
                case "loginInvalid":
                    in = this.getClass().getResourceAsStream("/sound/invalidlogin.wav");
                    break;
                case "newMsg":
                    in = this.getClass().getResourceAsStream("/sound/bird.wav");
                    break;
                case "Offline":
                    in = this.getClass().getResourceAsStream("/sound/goodbye.wav");
                    break;
                case "Online":
                    in = this.getClass().getResourceAsStream("/sound/surprise2.wav");
                    break;
                case "logout":
                    in = this.getClass().getResourceAsStream("/sound/logout.wav");
                    break;
                case "newUser":
                    in = this.getClass().getResourceAsStream("/sound/hello.wav");
                    break;
                default:
                    //in = this.getClass().getResourceAsStream("/kakaotalk.mp3");
                    break;
            }

            AudioStream audioInputStream = new AudioStream(in);
            AudioPlayer.player.start(audioInputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
