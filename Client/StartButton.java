package Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class StartButton extends JButton {
    public StartButton(String text, int x, int y, int width, int height) {
        super(text);
        setBounds(x, y, width, height);
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setBorder(new LineBorder(Color.BLACK));
        setFocusPainted(false);
    }
}
