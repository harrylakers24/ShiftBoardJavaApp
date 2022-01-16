package ui;

import java.io.FileInputStream;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class SoundPlayer {
    public static void play(String soundFile)  {
        // open the sound file as a Java input stream
        try {
            InputStream in = new FileInputStream(soundFile);
            AudioStream audioStream = new AudioStream(in);
            AudioPlayer.player.start(audioStream);
        } catch (Exception e) {
            System.out.println("Error playing: " + soundFile);
        }
    }
}