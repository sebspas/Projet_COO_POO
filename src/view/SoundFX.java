package view;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.*;

public class SoundFX {

    public void playSound(String event){

        try {

            InputStream in = this.getClass().getResourceAsStream("/success.wav");
            System.out.println("1");

            /*
            switch(event){
                case "loginValid":
                    soundName = this.getClass().getResource("glados/chellgladoswakeup01.wav");
                    break;
                case "loginInvalid":
                    soundName = this.getClass().getResource("/glados/anti_taunt10.wav");
                    break;
                case "newMsg":
                    break;
                default:
                    soundName = this.getClass().getResource("/kakaotalk.mp3");
                    break;
            }
            */

            System.out.println("2");


            AudioStream audioInputStream = new AudioStream(in);

            System.out.println("3");

            AudioPlayer.player.start(audioInputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
