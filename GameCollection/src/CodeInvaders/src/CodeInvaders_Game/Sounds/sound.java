package CodeInvaders_Game.Sounds;

import javax.sound.sampled.*;
import java.net.URL;

public class sound implements LineListener {
    private Clip soundClip;

    /**
     * Lädt einen Audio-Clip aus der angegebenen Datei.
     * Die Methode versucht, eine Audio-Ressource aus dem angegebenen Pfad zu laden und initialisiert den soundClip mit diesem Audio.
     * @param path
     */
    public sound(String path){
        try {
            URL url = getClass().getResource(path);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            AudioFormat format = audioInputStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            soundClip = (Clip) AudioSystem.getLine(info);
            soundClip.open(audioInputStream);
            soundClip.addLineListener(this);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Wird aufgerufen, wenn ein Line-Event (wie das Stoppen des Clips) auftritt.
     * Setzt die Position des Audio-Clips zurück, wenn er gestoppt wird, so dass er bei der nächsten Wiedergabe von vorne beginnt.
     * @param event a line event that describes the change
     */
    @Override
    public void update(LineEvent event) {
        if (event.getType().equals(LineEvent.Type.STOP)){
            soundClip.setFramePosition(1);
        }
    }
    /**
     * Startet die Wiedergabe des Audio-Clips.
     */
    public void play(){
        soundClip.start();
    }

    /**
     * Stoppt die Wiedergabe des Audio-Clips und setzt seine Position zurück, damit er bei der nächsten Wiedergabe wieder von vorne beginnt.
     */
    public void stop(){
        soundClip.stop();
        soundClip.setFramePosition(1);
    }

    /**
     * Überprüft, ob der Audio-Clip gerade abgespielt wird.
     * @return
     */
    public boolean isPlaying() {
        return soundClip.isRunning();
    }
}
