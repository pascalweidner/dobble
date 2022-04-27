package Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

public class GameButton extends JButton {

    public GameButton(String text, int x, int y, int width, int height) {
        super(text);
        setBounds(x, y, width, height);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setBorder(new LineBorder(Color.BLACK));
        setFocusPainted(false);
    }
}
