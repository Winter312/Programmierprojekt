package CodeInvaders_Game.GameDisplay;

import javax.swing.*;
import java.awt.*;

public class Applet extends JApplet {

    private Display display = new Display();

    // Initialisiert das Applet und fügt das Display-Objekt hinzu.
    public void init(){
        setLayout(new BorderLayout());
        add(display);
    }

    // Startet das Display, wenn das Applet gestartet wird.
    public void start(){
        display.start();
    }

    // Stoppt das Display, wenn das Applet gestoppt wird.
    public void stop(){
        display.stop();
    }
}
