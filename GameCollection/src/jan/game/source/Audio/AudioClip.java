package jan.game.source.Audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import jan.game.source.Main;

public class AudioClip {
       
    private Clip clip;
    private AudioInputStream sound;
    
    
    /**
     * Gibt den Clip züruck
     * @return Clip
     */
    public Clip getClip() {
        
        return clip;
    }
    
    /**
     * Holt ein Clip aus einer Datei
     * @param soundFileName
     */
    public void setFile(String soundFileName) {
        try {
            sound = AudioSystem.getAudioInputStream(Main.class.getResource(soundFileName));
            clip = AudioSystem.getClip();
            clip.open(sound);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
        }
    }
    
    /**
     * Spielt den Clip
     */
    public void play() {
        clip.start();
    }
    
    /**
     * Stoppt den Clip
     */
    public void stop() throws IOException {
        sound.close();
        clip.close();
        clip.stop();
    }

}
