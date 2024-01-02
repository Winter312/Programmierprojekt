package CodeInvaders_Game.Timer;

public class timer {
    private long prevTimer;

    /**
     * Setzt den prevTimer auf die aktuelle Systemzeit beim Erstellen einer Instanz der Klasse.
     */
    public timer(){
        setPrevTimer(System.currentTimeMillis());
    }

    /**
     * Gibt den Wert von prevTimer zurück.
     * @return
     */
    public long getPrevTimer(){
        return prevTimer;
    }

    /**
     * Setzt prevTimer auf einen gegebenen Wert.
     * @param currentTimer
     */
    public void setPrevTimer(long currentTimer){
        this.prevTimer = currentTimer;
    }

    /**
     * Setzt prevTimer auf die aktuelle Systemzeit zurück.
     */
    public void resetTimer(){
        prevTimer = System.currentTimeMillis();
    }

    /**
     * Überprüft, ob die angegebene Zeit (in Millisekunden) seit dem letzten Reset des Timers vergangen ist.
     * Setzt den Timer zurück und gibt true zurück, wenn die Zeit verstrichen ist.
     * @param timer
     * @return
     */
    public boolean timerEvent(int timer){
        if (System.currentTimeMillis() - getPrevTimer() > timer){
            resetTimer();
            return true;
        }
        return false;
    }

    /**
     * Ähnlich wie timerEvent, überprüft aber nur, ob die angegebene Zeit vergangen ist, ohne den Timer zurückzusetzen.
     * @param timer
     * @return
     */
    public boolean IfTimerReady(int timer){
        if (System.currentTimeMillis() - getPrevTimer() > timer){
            return true;
        }
        return false;
    }
}
