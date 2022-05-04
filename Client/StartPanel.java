package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartPanel extends JPanel {
    private final GameButton newGame;
    private final GameButton joinGame;
    private final ImageIcon backgroundImage;



    public StartPanel(int width, int height, GameFrame frame) {
        setSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setLayout(null);

        backgroundImage = new ImageIcon("images\\background.png");

        newGame = new GameButton("Erstelle ein neues Spiel", width / 2 - 250, height - 100, 200, 30);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.createLobby();
            }
        });
        add(newGame);

        joinGame = new GameButton("Trete einem Spiel bei", width / 2 + 50, height - 100, 200, 30);
        joinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.joinLobby();
            }
        });
        add(joinGame);

        JLabel label = new JLabel("By Pascal Weidner & Janick Braun");
        label.setBounds(getWidth() - 230, 10, 220, 30);
        add(label);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
    }
}
