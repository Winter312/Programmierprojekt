package jan.game.source.Audio;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.FloatControl;

public class AudioManager {

    private static List<AudioClip> musicList = new ArrayList<>();
        
    /**
     * Fügt AudioClip hinzu
     * @param clip
     */
    public static void addMusicClip(AudioClip clip) {
        
        musicList.add(clip);
    }

    /**
     * Setzt Lautstärke
     * @param volume
     */
    public static void setVolume(double volume) {
        
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        
        for (AudioClip audioClip : musicList) {
            
            FloatControl gainControl = (FloatControl) audioClip.getClip().getControl(FloatControl.Type.MASTER_GAIN);        
            gainControl.setValue(20f * (float) Math.log10(volume));
        }
        
    }
    
    /**
     * Stoppt alle AudioClips
     * @throws IOException
     */
    public static void end() throws IOException {
        
        for (AudioClip audioClip : musicList) {
            audioClip.stop();
        }
    }
    
    
}
