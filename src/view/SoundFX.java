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

            //URL soundName = this.getClass().getResource("/anti_taunt10.wav");
            File soundName = new File("C:\\Users\\camil\\OneDrive\\Documents\\Projet_COO_POO\\out\\production\\Projet_COO_POO\\anti_taunt10.wav");

           // InputStream in = new FileInputStream("C:\\Users\\camil\\OneDrive\\Documents\\Projet_COO_POO\\out\\production\\Projet_COO_POO\\anti_taunt10.wav");
            InputStream in = this.getClass().getResourceAsStream("/anti_taunt10.wav");
            System.out.println(soundName);
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

            //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("C:/Users/camil/OneDrive/Documents/Projet_COO_POO/out/production/Projet_COO_POO/anti_taunt10.wav"));
            //AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundName);
            AudioStream audioInputStream = new AudioStream(in);

            System.out.println("3");

            AudioPlayer.player.start(audioInputStream);

            /*
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            */

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
