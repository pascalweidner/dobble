package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.SecureRandom;

public class Request extends Thread{
    private Socket client;
    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom rnd = new SecureRandom();

    public Request(Socket client) {
        super();

        this.client = client;
    }

    @Override
    public void run() {
        try {
            DataInputStream input = new DataInputStream(client.getInputStream());
            String order = input.readUTF();
            if(order.startsWith("Create Lobby")) {
                createLobby(order);
            }
            else {
                String order2 = order.substring(0, 10);
                switch (order2) {
                    case "Join Lobby" :
                        joinLobby(order);
                        break;
                    case "Match Card" :
                        matchCard(order);
                        break;
                    case "Quit Lobby" :
                        leaveLobby(order);
                        break;
                    case "Start Game" :
                        startGame(order);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    private void createLobby(String order) {
        String lobbyCode = randomString(8);
        String name = order.substring(13);
        if(!Server.lobbys.containsKey(lobbyCode)) {
            Server.lobbys.put(lobbyCode, new Lobby(new User(client, name)));
            try {
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                output.writeUTF(lobbyCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                output.writeUTF("Error");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void joinLobby(String order) {
        String lobbyCode = order.substring(11, 19);
        String name = order.substring(20);

        if(Server.lobbys.containsKey(lobbyCode)) {
            if(!Server.lobbys.get(lobbyCode).isRunning()) {
                Lobby lobby = Server.lobbys.get(lobbyCode);
                if(lobby.getUsers().size() <= 5) {
                    int position = lobby.joinLobby(new User(client, name));

                    try {
                        for (User user : lobby.getUsers()) {
                            if (user.getClient().equals(client)) {
                                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                                output.writeUTF(position + lobby.getUserNames(lobbyCode));
                            } else {
                                DataOutputStream output = new DataOutputStream(user.getClient().getOutputStream());
                                output.writeUTF("New Player " + position + " " + name);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        DataOutputStream output = new DataOutputStream(client.getOutputStream());
                        output.writeUTF("Error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                try {
                    DataOutputStream output = new DataOutputStream(client.getOutputStream());
                    output.writeUTF("Error");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        else {
            try {
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                output.writeUTF("Error");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void leaveLobby(String order) {
        String lobbyCode = order.substring(11, 19);
        String position = order.substring(20, 21);
        if(Server.lobbys.containsKey(lobbyCode)) {
            Socket socket = Server.lobbys.get(lobbyCode).getUsers().get(Integer.parseInt(position)).getClient();
            boolean success = Server.lobbys.get(lobbyCode).leaveLobby(client, Integer.parseInt(position));
            if (success) Server.lobbys.remove(lobbyCode);
            else {
                try {
                    for(User user : Server.lobbys.get(lobbyCode).getUsers()) {
                        DataOutputStream output = new DataOutputStream(user.getClient().getOutputStream());
                        output.writeUTF("Player Leaves " + position);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                output.writeUTF("Success");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                DataOutputStream output = new DataOutputStream(client.getOutputStream());
                output.writeUTF("Error");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void matchCard(String order) {
        String lobbyCode = order.substring(11, 19);
        String position = order.substring(20, 21);
        String icon = order.substring(22);

        Lobby lobby = Server.lobbys.get(lobbyCode);
        if(Server.lobbys.containsKey(lobbyCode)) {
            boolean success = false;
            boolean winner = false;
            if(lobby.isRunning()) {
                success = lobby.matchCard(Integer.parseInt(position), Integer.parseInt(icon));
                lobby.changeClient(client, Integer.parseInt(position));
                winner = lobby.getWinner();
            }
            try {
                DataOutputStream output = null;
                if(!winner) {
                    output = new DataOutputStream(client.getOutputStream());
                    output.writeUTF(Boolean.toString(success));
                }
                if(winner) {

                    for(User user : Server.lobbys.get(lobbyCode).getUsers()) {
                        output = new DataOutputStream(user.getClient().getOutputStream());
                        output.writeUTF("Player Wins " + position + lobby.getPlayerCount());
                    }
                }
                else if(success) {
                    if (lobby.isRunning()) {
                        for(User user : Server.lobbys.get(lobbyCode).getUsers()) {
                            output = new DataOutputStream(user.getClient().getOutputStream());
                            output.writeUTF("Match Card " + position);
                        }
                    }
                    else {
                        try {
                            DataOutputStream o = new DataOutputStream(client.getOutputStream());
                            o.writeUTF("Error");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                else {
                    try {
                        DataOutputStream o = new DataOutputStream(client.getOutputStream());
                        o.writeUTF("Error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                DataOutputStream o = new DataOutputStream(client.getOutputStream());
                o.writeUTF("Error");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void startGame(String order) {
        String lobbyCode = order.substring(11, 19);
        String position = order.substring(20, 21);

        Lobby lobby = Server.lobbys.get(lobbyCode);
        if(Server.lobbys.containsKey(lobbyCode)) {
            if(!lobby.isRunning()) {
                lobby.startGame();
                lobby.changeClient(client, Integer.parseInt(position));
                try {
                    for(User user : Server.lobbys.get(lobbyCode).getUsers()) {
                        DataOutputStream output = new DataOutputStream(user.getClient().getOutputStream());
                        output.writeUTF("Start Game " + user.getTopCard().getId() + " " + lobby.getTopCard().getId() + " " + lobby.getCardOrder());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    DataOutputStream o = new DataOutputStream(client.getOutputStream());
                    o.writeUTF("Error");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        else {
            try {
                DataOutputStream o = new DataOutputStream(client.getOutputStream());
                o.writeUTF("Error");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
