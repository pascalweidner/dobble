package Client;

import Client.Game;

import javax.sound.sampled.Line;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    private boolean active = false;
    private Game game;
    private final int[] xPositions;
    private final int[] yPositions;
    private final int[] size;
    private ArrayList<JButton> buttons;

    public GamePanel(int width, int height, Game game) {
        setLayout(null);
        this.game = game;
        setBounds(0, 0, width, height);

        xPositions = new int[]{getWidth() / 2 - getWidth() / 5 / 2, getWidth() / 2 - getWidth() / 5 / 2 - getWidth() / 6 - 30, getWidth() / 2 + getWidth() / 5 / 2 + 30, getWidth() / 2 - getWidth() / 7 - getWidth() / 5 / 2 + 50, getWidth() / 2 + getWidth() / 5 / 2 - 50};
        yPositions = new int[]{getHeight() / 2 + 20, getHeight() / 2 -getWidth() / 5 + 70, getHeight() / 2 - getWidth() / 5 + 70, getHeight() / 2 - getWidth() / 5 - 80, getHeight() / 2 - getWidth() / 5 - 80};
        size = new int[]{getWidth() / 5, getWidth() / 6, getWidth() / 6, getWidth() / 7, getWidth() / 7};

        JButton leaveButton = new JButton("Leave Game");
        leaveButton.setBounds(10, 10, 150, 30);
        leaveButton.setBackground(Color.WHITE);
        leaveButton.setBorder(new LineBorder(Color.BLACK));
        leaveButton.setFocusPainted(false);
        leaveButton.addActionListener(e -> game.leaveLobby());
        add(leaveButton);

        buttons = new ArrayList<>();
        drawButtons(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;
        drawGame(graphics2D);
    }

    public void refreshButtons() {
        for(JButton button: buttons) {
            remove(button);
        }
        invalidate();
        validate();
        buttons = new ArrayList<>();
        drawButtons(false);
    }

    public void someoneMatchedCard() {
        repaint();
        refreshButtons();
        refreshButtons();
    }

    private void drawButtons(boolean first) {
        int counter = 0;
        for (Icons icon: game.getIcons()) {
            JButton button = new JButton(icon.toString());
            button.setBackground(Color.WHITE);
            button.setBorder(new LineBorder(Color.BLACK));
            button.setFocusPainted(false);
            button.addActionListener(e -> {
                game.matchCard(icon);
                repaint();
                refreshButtons();
                refreshButtons();
            });
            if(first) {
                if (counter == 0) {
                    button.setBounds(0, getHeight() - 139, 138, 30);
                }
                else if(counter < 4) {
                    button.setBounds(142 * (counter - 1), getHeight() - 104, 138, 30);
                }
                else if(counter == 14) {
                    button.setBounds(852, getHeight() - 139, 138, 30);
                }
                else if(counter >= 11) {
                    button.setBounds(568 + 142 * (counter - 11), getHeight() - 104, 138, 30);
                }
                else {
                    button.setBounds(142 * (counter - 4), getHeight() - 69, 138, 30);
                }
            }
            else {
                if (counter == 0) {
                    button.setBounds(0, getHeight() - 100, 138, 30);
                }
                else if(counter < 4) {
                    button.setBounds(142 * (counter - 1), getHeight() - 65, 138, 30);
                }
                else if(counter == 14) {
                    button.setBounds(852, getHeight() - 100, 138, 30);
                }
                else if(counter >= 11) {
                    button.setBounds(568 + 142 * (counter - 11), getHeight() - 65, 138, 30);
                }
                else {
                    button.setBounds(142 * (counter - 4), getHeight() - 30, 138, 30);
                }
            }
            buttons.add(button);
            add(button);
            counter ++;
        }
    }

    private void drawGame(Graphics2D g) {
        ImageIcon backgroundImage = new ImageIcon("D:\\Development\\Languages\\Java\\Dobble\\images\\background.png");
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, getWidth(), getHeight());

        game.getTopCard().draw(g, getWidth() / 2 - getWidth() / 5 / 2, getHeight() / 2 - getWidth() / 5 + 20, getWidth() / 5);

        int counter = 0;
        for (int i = game.getPosition(); i < game.getUsers().size(); i++) {
            game.getUsers().get(i).getTopCard().draw(g, xPositions[counter], yPositions[counter], size[counter]);
            counter ++;
        }
        for (int i = 0; i < game.getPosition(); i++) {
            game.getUsers().get(i).getTopCard().draw(g, xPositions[counter], yPositions[counter], size[counter]);
            counter++;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
