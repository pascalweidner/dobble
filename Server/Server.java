package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

public class Server {
    public static Hashtable<String, Lobby> lobbys;
    private ServerSocket server;

    public Server(int port) {
        lobbys = new Hashtable<>();

        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        CheckServer check = new CheckServer();
        check.start();

        run();
    }

    public void run() {
        while(true) {
            try {
                System.out.println("Waiting for client");
                Socket client = server.accept();
                Request request = new Request(client);
                request.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
