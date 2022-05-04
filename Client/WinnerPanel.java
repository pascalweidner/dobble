package Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WinnerPanel extends JPanel {
    private Game game;
    private String[] r;

    public WinnerPanel(int width, int height, Game game, String[] r) {
        setLayout(null);
        setBounds(0, 0, width, height);
        this.game = game;
        this.r = r;

        JButton backToLobby = new JButton("Back to Lobby");
        backToLobby.setBounds(10, 10, 150, 30);
        backToLobby.setBackground(Color.WHITE);
        backToLobby.setBorder(new LineBorder(Color.BLACK));
        backToLobby.setFocusPainted(false);
        backToLobby.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.backToLobby();
            }
        });
        add(backToLobby);

        JLabel label = new JLabel("By Pascal Weidner & Janick Braun");
        label.setBounds(getWidth() - 230, 10, 220, 30);
        add(label);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ImageIcon backgroundImage = new ImageIcon("images\\background.png");
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);

        ArrayList<String> names = new ArrayList<>();
        for(int i = 3; i < r.length; i++) {
            int position = 0;
            int latest = 0;
            for(int j = 3; j < r.length; j++) {
                if(Integer.parseInt(r[j]) > latest) {
                    latest = Integer.parseInt(r[j]);
                    position = j - 3;
                }
            }
            names.add(game.getUsers().get(position).getName());
        }
        g.setColor(Color.BLACK);
        Font font = new Font( "Segoe UI", Font.BOLD, 50);
        FontMetrics fontMetrics = getFontMetrics(font);
        g.setFont(font);
        int counter = 0;
        for(String name : names) {
            g.drawString(Integer.toString(counter + 1), counter * fontMetrics.stringWidth(Integer.toString(counter + 1)) + 50, getHeight() / 2 - 50);
            g.drawString(name, counter * fontMetrics.stringWidth(name), getHeight() / 2);
            counter++;
        }
    }
}
