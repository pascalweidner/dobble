package Client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ListenThread extends Thread{
    private Socket socket;
    private DataInputStream response;
    private volatile boolean exit = false;
    private final Game game;

    public ListenThread(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }

    @Override
    public void run() {
        while(!exit) {
            try {
                response = new DataInputStream(socket.getInputStream());
                String r = response.readUTF();
                String[] order = r.split(" ");
                if(order[0].equals("New") && order[1].equals("Player")) joinLobby(order);
                else if(order[0].equals("Player") && order[1].equals("Leaves")) leaveLobby(order);
                else if(order[0].equals("Match") && order[1].equals("Card")) matchCard(order);
                else if(order[0].equals("Start") && order[1].equals("Game")) startGame(r);
            } catch (IOException e) {
            }
        }
    }

    public void cancel() {
        exit = true;
    }

    private void joinLobby(String[] order) {
        game.someoneJoined(order[3]);
    }

    private void matchCard(String[] order) {
        game.someoneMatchedCard(Integer.parseInt(order[2]));
    }

    private void leaveLobby(String[] order) {
        game.someoneLeaved(Integer.parseInt(order[2]));
    }

    private void startGame(String order) {
        game.gameHasBeenStarted(order);
    }
}
