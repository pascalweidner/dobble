package Client;

import javax.swing.*;
import java.util.Random;

public class GameFrame extends JFrame {
    private StartPanel startPanel;
    private LobbyPanel lobby;
    private GamePanel gamePanel;
    private WinnerPanel winnerPanel;
    private Game game;

    private int width = 1000;
    private int height = 600;

    public GameFrame() {
        game = new Game(this);

        setSize(width, height);
        startPanel = new StartPanel(width, height, this);
        add(startPanel);
        setIconImage(new ImageIcon("images/icon2.png").getImage());

        setResizable(false);
        setTitle("Dobble");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public void joinLobby() {
        lobby = new LobbyPanel(width, height, 1, this, game, "");
        getContentPane().remove(startPanel);
        add(lobby);
        invalidate();
        validate();
    }

    public void backToLobby() {
        startPanel = new StartPanel(width, height, this);
        getContentPane().remove(winnerPanel);
        add(startPanel);
        invalidate();
        validate();
    }

    public void createLobby() {
        Random random = new Random();
        String lobbyCode = game.createLobby("Player " + random.nextInt(10)+1);
        lobby = new LobbyPanel(width, height, 2, this, game, lobbyCode);
        getContentPane().remove(startPanel);
        add(lobby);
        invalidate();
        validate();
    }

    public void startGame() {
        gamePanel = new GamePanel(width, height, game);
        getContentPane().remove(lobby);
        add(gamePanel);
        invalidate();
        validate();
    }

    public void error() {
        startPanel = new StartPanel(width, height, this);
        getContentPane().remove(lobby);
        add(startPanel);
        invalidate();
        validate();
    }

    public void playerWins(String[] r) {
        winnerPanel = new WinnerPanel(width, height, game, r);
        getContentPane().remove(gamePanel);
        add(winnerPanel);
        invalidate();
        validate();
    }

    public void leaveLobby() {
        startPanel = new StartPanel(width, height, this);
        getContentPane().remove(gamePanel);
        add(startPanel);
        invalidate();
        validate();
    }

    public void gameHasBeenStarted() {
        gamePanel = new GamePanel(width, height, game);
        getContentPane().remove(lobby);
        add(gamePanel);
        invalidate();
        validate();
    }

    public void someoneJoined() {
        lobby.someoneJoined();
    }

    public void someoneLeaved() {lobby.someoneLeaved();}

    public void someoneMatchedCard() {
        gamePanel.someoneMatchedCard();
    }
}
