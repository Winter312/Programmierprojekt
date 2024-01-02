package ezgin.src.main.enums;


/**
 * Enum für den Tutorial-Status.
 */
public enum TutorialState {
    NONE(0, null),
    GAME_BEGIN(0, "Teste die Steuerung!"),
    FIRST_CHEST(0, "Ein verborgener Schatz\nwartet auf dich! Nähere dich\nder geheimnisvollen Truhe,\num ihre Geheimnisse zu enthüllen."),
    FIRST_CHECKPOINT(0, "Du hast einen Checkpoint\ngefunden! Begib dich in dessen\nMitte, um ihn für deinen\nFortschritt zu aktivieren."),
    FIRST_HIT(0, "Du hast einen geschliffenen Dolch\nentdeckt! Nimm ihn in die Hand,\num seine scharfe Klinge und ver-\nborgenen Fähigkeiten zu nutzen."),
    FIRST_PART(0, "Du hast die Truhe übersehen!\nUm diesen Pfad zu beschreiten,\nmusst du zuerst die Truhe\nöffnen."),
    SECOND_PART(0, "Du bist noch nicht bereit weiter\nzugehen! Beende zuerst den\nbisherigen Teil des Tutorials."),
    FIRST_STELA(0, "Die Stele leuchtet auf, ein\nZeichen ihrer Aktivierung!\nNähere dich, um die geheimen\nInschriften zu entschlüsseln."),
    FIRST_ENEMY(0, "Vorsicht, ein Feind lauert in der\nNähe! Nutze deine Waffe, um ihn\nzu bezwingen. Nähere dich mit\nBedacht, er wird sich verteidigen!"),
    FIRST_LEVEL_END(0, "Du hast alle CheckPoints\naktiviert! Du bist nun bereit, die\nReise zu starten. Vergiss aber\nnicht, die letzte Stele zu lesen!"),
    FIRST_ENEMY_KILL(0, "Großartig, er wurde bezwungen!\nEs hat ein paar Lebenspunkte\ngekostet, doch keine Sorge,\nsie erholen sich bald wieder!");

    private static TutorialState currentTutorialState = NONE;
    private int done;
    private String comment;

    TutorialState(int done, String comment) {
        setDone(done);
        setComment(comment);
    }

    /**
     * setzt alle TutorialStates auf 0 und setzt den aktuellen TutorialState auf NONE
     */
    public static void reset() {
        for (TutorialState tutorialState : values()) {
            tutorialState.setDone(0);
        }
        setCurrentTutorialState(NONE);
    }


    // GETTER AND SETTER


    public static TutorialState getCurrentTutorialState() {
        return currentTutorialState;
    }

    public static void setCurrentTutorialState(TutorialState currentState) {
        TutorialState.currentTutorialState = currentState;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
