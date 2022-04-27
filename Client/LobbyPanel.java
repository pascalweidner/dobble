package Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class LobbyPanel extends JPanel {
    private final ImageIcon backgroundImage;
    private int type;
    private String lobbyCode;
    private final Game game;
    private final int[] xPositons;
    private final int[] yPositons;
    private JTextField textField;
    private JButton enterButton;

    public LobbyPanel(int width, int height, int type, GameFrame frame, Game game, String lobbyCode) {
        this.lobbyCode = lobbyCode;
        this.game = game;

        setBounds(0, 0, width, height);
        setLayout(null);
        backgroundImage = new ImageIcon("D:\\Development\\Languages\\Java\\Dobble\\images\\background.png");

        StartButton startButton = new StartButton("Start", width - 220, height - 95, 200, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.startGame();
                frame.startGame();
            }
        });
        if(type == 2 || type == 1) {
            add(startButton);
        }

        this.type = type;
        if(type == 1) {
            textField = new JTextField();
            textField.setBounds(getWidth() / 2 - 150, getHeight() / 2 - 15, 300, 30);
            textField.setBorder(new LineBorder(Color.BLACK));
            add(textField);
            enterButton = new JButton("Finish");
            enterButton.setBounds(getWidth() / 2 + 152, getHeight() / 2 - 15, 70, 30);
            enterButton.setBackground(Color.WHITE);
            enterButton.setBorder(new LineBorder(Color.BLACK));
            enterButton.addActionListener(e -> joinLobby());
            add(enterButton);
        }

        xPositons = new int[]{93, 123 + getWidth() / 4, 153 + (getWidth() / 4) * 2, 231, 261 + getWidth() / 4};
        yPositons = new int[]{0, 0, 0, getWidth() / 4, getWidth() / 4};
    }

    private void joinLobby() {
        Random random = new Random();
        game.joinLobby(textField.getText(), "Player " + random.nextInt(10)+1);
        lobbyCode = textField.getText();
        game.setLobbyCode(lobbyCode);
        remove(textField);
        remove(enterButton);
        invalidate();
        validate();
        type = 2;
        repaint();
    }

    public void someoneJoined() {
        repaint();
    }

    public void someoneLeaved() { repaint(); }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);

        g.setColor(Color.WHITE);

        //Top layer of free slots
        Font font = new Font("Segoe UI", Font.BOLD, 25);
        FontMetrics fontMetrics = getFontMetrics(font);
        g.setFont(font);
        for(int i = 0; i < 5; i++) {
            g.setColor(Color.WHITE);
            g.fillOval(xPositons[i], yPositons[i], getWidth() / 4, getWidth() / 4);
            if(game.getUsers() != null && i < game.getUsers().size()) {
                g.setColor(Color.BLACK);
                g.drawString(game.getUsers().get(i).getName(), xPositons[i] + getWidth() / 4 / 2 - fontMetrics.stringWidth(game.getUsers().get(i).getName()) / 2, yPositons[i] + getWidth() / 4 / 2);
            }
        }

        Font f = new Font("Segoe UI", Font.BOLD, 25);
        g.setColor(Color.BLACK);
        g.setFont(f);
        g.drawString("Lobby Code: " + lobbyCode, 10, getHeight() - 10);

        if(type == 1) {
            g.setColor(new Color(0, 0, 0, 130));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
