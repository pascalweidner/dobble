package Client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private Card[] cards;
    private Socket socket;
    private int position;
    private ArrayList<User> users;
    private String lobbyCode = "Error";
    private ListenThread listen;
    private final GameFrame frame;
    private int topCard;

    public Game(GameFrame frame) {
        this.frame = frame;

        cards = new Card[55];
        try {
            loadGame();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadGame() throws IOException {

        final BufferedImage source = ImageIO.read(new File("D:\\Development\\Languages\\Java\\Dobble\\images\\dobble2.png"));

        int counter = 0;
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 11; x++) {
                Image image = source.getSubimage(9 + 23 * x + x * 247, 9 + 23 * y + y * 247, 247, 247);

                Icons[] icons;

                switch(counter) {
                    case 0 -> icons = new Icons[]{Icons.MOON, Icons.WATCH, Icons.PERSON, Icons.EYE, Icons.GHOST, Icons.TREE, Icons.CAR, Icons.STOP};
                    case 1 -> icons = new Icons[]{Icons.DINOSAUR, Icons.SCISSOR, Icons.COLOR, Icons.PEN, Icons.DOG, Icons.KEY, Icons.SPIDER_WEB, Icons.STOP};
                    case 2 -> icons = new Icons[]{Icons.DOLPHIN, Icons.CANDLE, Icons.ZEBRA, Icons.EXCLAMATION_MARK, Icons.CAT, Icons.HORSE, Icons.CACTUS, Icons.STOP};
                    case 3 -> icons = new Icons[]{Icons.STOP, Icons.WATER, Icons.SKULL, Icons.LEAF, Icons.FIRE, Icons.TURTLE, Icons.LIPS, Icons.BOMB};
                    case 4 -> icons = new Icons[]{Icons.LOCK, Icons.BEETLE, Icons.CHEESE, Icons.QUESTION_MARK, Icons.HEART, Icons.STOP, Icons.SUN, Icons.ICE};
                    case 5 -> icons = new Icons[]{Icons.IGLOO, Icons.ICE_CUBE, Icons.CARROT, Icons.HAND, Icons.LIGHT_BULB, Icons.CLEF, Icons.BIRD, Icons.STOP};
                    case 6 -> icons = new Icons[]{Icons.YIN_AND_YANG, Icons.BOTTLE, Icons.DRAGON, Icons.APPLE, Icons.STOP, Icons.SHAMROCK, Icons.FLOWER, Icons.HAMMER};
                    case 7 -> icons = new Icons[]{Icons.TURTLE, Icons.ICE_CUBE, Icons.EXCLAMATION_MARK, Icons.CHEESE, Icons.SHAMROCK, Icons.TARGET, Icons.PEN, Icons.TREE};
                    case 8 -> icons = new Icons[]{Icons.SUN, Icons.IGLOO, Icons.SKULL, Icons.FLOWER, Icons.TARGET, Icons.GHOST, Icons.SPIDER_WEB, Icons.ZEBRA};
                    case 9 -> icons = new Icons[]{Icons.BEETLE, Icons.CARROT, Icons.TARGET, Icons.CAT, Icons.PERSON, Icons.SCISSOR, Icons.BOTTLE, Icons.LIPS};
                    case 10 -> icons = new Icons[]{Icons.BOMB, Icons.YIN_AND_YANG, Icons.CAR, Icons.HORSE, Icons.TARGET, Icons.ICE, Icons.DINOSAUR, Icons.LIGHT_BULB};
                    case 11 -> icons = new Icons[]{Icons.MOON, Icons.HAMMER, Icons.COLOR, Icons.CACTUS, Icons.LOCK, Icons.WATER, Icons.BIRD, Icons.TARGET};
                    case 12 -> icons = new Icons[]{Icons.LEAF, Icons.DOLPHIN, Icons.EYE, Icons.TARGET, Icons.HAND, Icons.DRAGON, Icons.KEY, Icons.HEART};
                    case 13 -> icons = new Icons[]{Icons.FIRE, Icons.WATCH, Icons.CANDLE, Icons.CLEF, Icons.TARGET, Icons.APPLE, Icons.DOG, Icons.QUESTION_MARK};
                    case 14 -> icons = new Icons[]{Icons.YIN_AND_YANG, Icons.LIGHTNING, Icons.COLOR, Icons.TREE, Icons.LEAF, Icons.ZEBRA, Icons.CLEF, Icons.BEETLE};
                    case 15 -> icons = new Icons[]{Icons.LIGHTNING, Icons.SHAMROCK, Icons.QUESTION_MARK, Icons.MOON, Icons.DOLPHIN, Icons.SPIDER_WEB, Icons.LIPS, Icons.LIGHT_BULB};
                    case 16 -> icons = new Icons[]{Icons.LIGHTNING, Icons.FIRE, Icons.CAT, Icons.GHOST, Icons.KEY, Icons.ICE, Icons.ICE_CUBE, Icons.HAMMER};
                    case 17 -> icons = new Icons[]{Icons.CHEESE, Icons.SCISSOR, Icons.LIGHTNING, Icons.BOMB, Icons.CANDLE, Icons.EYE, Icons.FLOWER, Icons.BIRD};
                    case 18 -> icons = new Icons[]{Icons.DOG, Icons.PERSON, Icons.LIGHTNING, Icons.TURTLE, Icons.HORSE, Icons.DRAGON, Icons.IGLOO, Icons.LOCK};
                    case 19 -> icons = new Icons[]{Icons.WATER, Icons.EXCLAMATION_MARK, Icons.LIGHTNING, Icons.DINOSAUR, Icons.WATCH, Icons.HAND, Icons.SUN, Icons.BOTTLE};
                    case 20 -> icons = new Icons[]{Icons.LIGHTNING, Icons.APPLE, Icons.CACTUS, Icons.CARROT, Icons.SKULL, Icons.HEART, Icons.PEN, Icons.CAR};
                    case 21 -> icons = new Icons[]{Icons.DINOSAUR, Icons.IGLOO, Icons.HAMMER, Icons.CLOWN, Icons.HEART, Icons.TREE, Icons.LIPS, Icons.CANDLE};
                    case 22 -> icons = new Icons[]{Icons.ICE, Icons.BOTTLE, Icons.TURTLE, Icons.EYE, Icons.SPIDER_WEB, Icons.CACTUS, Icons.CLOWN, Icons.CLEF};
                    case 23 -> icons = new Icons[]{Icons.SUN, Icons.SHAMROCK, Icons.LEAF, Icons.CAT, Icons.DOG, Icons.CAR, Icons.CLOWN, Icons.BIRD};
                    case 24 -> icons = new Icons[]{Icons.DRAGON, Icons.CARROT, Icons.COLOR, Icons.QUESTION_MARK, Icons.EXCLAMATION_MARK, Icons.CLOWN, Icons.BOMB, Icons.GHOST};
                    case 25 -> icons = new Icons[]{Icons.SCISSOR, Icons.CLOWN, Icons.LOCK, Icons.WATCH, Icons.YIN_AND_YANG, Icons.ICE_CUBE, Icons.DOLPHIN, Icons.SKULL};
                    case 26 -> icons = new Icons[]{Icons.MOON, Icons.FLOWER, Icons.PEN, Icons.HAND, Icons.HORSE, Icons.BEETLE, Icons.FIRE, Icons.CLOWN};
                    case 27 -> icons = new Icons[]{Icons.CHEESE, Icons.CLOWN, Icons.WATER, Icons.KEY, Icons.ZEBRA, Icons.APPLE, Icons.PERSON, Icons.LIGHT_BULB};
                    case 28 -> icons = new Icons[]{Icons.DOLPHIN, Icons.FLOWER, Icons.DOG, Icons.WATER, Icons.TREE, Icons.ICE, Icons.CARROT, Icons.SUN_GLASSES};
                    case 29 -> icons = new Icons[]{Icons.YIN_AND_YANG, Icons.PERSON, Icons.EXCLAMATION_MARK, Icons.SUN_GLASSES, Icons.HEART, Icons.SPIDER_WEB, Icons.FIRE, Icons.BIRD};
                    case 30 -> icons = new Icons[]{Icons.CHEESE, Icons.SUN_GLASSES, Icons.CLEF, Icons.DINOSAUR, Icons.DRAGON, Icons.MOON, Icons.SKULL, Icons.CAT};
                    case 31 -> icons = new Icons[]{Icons.BEETLE, Icons.SHAMROCK, Icons.SUN_GLASSES, Icons.CACTUS, Icons.KEY, Icons.WATCH, Icons.IGLOO, Icons.BOMB};
                    case 32 -> icons = new Icons[]{Icons.LEAF, Icons.PEN, Icons.SUN_GLASSES, Icons.GHOST, Icons.BOTTLE, Icons.CANDLE, Icons.LIGHT_BULB, Icons.LOCK};
                    case 33 -> icons = new Icons[]{Icons.SCISSOR, Icons.QUESTION_MARK, Icons.HAND, Icons.CAR, Icons.TURTLE, Icons.ZEBRA, Icons.HAMMER, Icons.SUN_GLASSES};
                    case 34 -> icons = new Icons[]{Icons.ICE_CUBE, Icons.COLOR, Icons.EYE, Icons.SUN_GLASSES, Icons.HORSE, Icons.APPLE, Icons.LIPS, Icons.SUN};
                    case 35 -> icons = new Icons[]{Icons.SPIDER, Icons.QUESTION_MARK, Icons.BIRD, Icons.TREE, Icons.HORSE, Icons.KEY, Icons.BOTTLE, Icons.SKULL};
                    case 36 -> icons = new Icons[]{Icons.DRAGON, Icons.SPIDER, Icons.CAR, Icons.WATER, Icons.SPIDER_WEB, Icons.CANDLE, Icons.ICE_CUBE, Icons.BEETLE};
                    case 37 -> icons = new Icons[]{Icons.COLOR, Icons.HEART, Icons.WATCH, Icons.LIGHT_BULB, Icons.TURTLE, Icons.SPIDER, Icons.CAT, Icons.FLOWER};
                    case 38 -> icons = new Icons[]{Icons.DOLPHIN, Icons.PERSON, Icons.PEN, Icons.SUN, Icons.CLEF, Icons.BOMB, Icons.SPIDER, Icons.HAMMER};
                    case 39 -> icons = new Icons[]{Icons.SPIDER, Icons.SHAMROCK, Icons.EYE, Icons.ZEBRA, Icons.LOCK, Icons.DINOSAUR, Icons.CARROT, Icons.FIRE};
                    case 40 -> icons = new Icons[]{Icons.SPIDER, Icons.CHEESE, Icons.CACTUS, Icons.DOG, Icons.GHOST, Icons.LIPS, Icons.YIN_AND_YANG, Icons.HAND};
                    case 41 -> icons = new Icons[]{Icons.SCISSOR, Icons.SPIDER, Icons.EXCLAMATION_MARK, Icons.APPLE, Icons.ICE, Icons.IGLOO, Icons.MOON, Icons.LEAF};
                    case 42 -> icons = new Icons[]{Icons.FIRE, Icons.SCISSOR, Icons.CACTUS, Icons.TREE, Icons.SUN, Icons.LIGHT_BULB, Icons.DRAGON, Icons.ANCHOR};
                    case 43 -> icons = new Icons[]{Icons.CHEESE, Icons.HORSE, Icons.SPIDER_WEB, Icons.WATCH, Icons.ANCHOR, Icons.LEAF, Icons.HAMMER, Icons.CARROT};
                    case 44 -> icons = new Icons[]{Icons.YIN_AND_YANG, Icons.CAT, Icons.EYE, Icons.QUESTION_MARK, Icons.PEN, Icons.WATER, Icons.IGLOO, Icons.ANCHOR};
                    case 45 -> icons = new Icons[]{Icons.ANCHOR, Icons.MOON, Icons.HEART, Icons.DOG, Icons.ZEBRA, Icons.BOTTLE, Icons.ICE_CUBE, Icons.BOMB};
                    case 46 -> icons = new Icons[]{Icons.CLEF, Icons.FLOWER, Icons.EXCLAMATION_MARK, Icons.CAR, Icons.KEY, Icons.LOCK, Icons.LIPS, Icons.ANCHOR};
                    case 47 -> icons = new Icons[]{Icons.SKULL, Icons.COLOR, Icons.HAND, Icons.ICE, Icons.CANDLE, Icons.ANCHOR, Icons.SHAMROCK, Icons.PERSON};
                    case 48 -> icons = new Icons[]{Icons.TURTLE, Icons.DOLPHIN, Icons.BIRD, Icons.APPLE, Icons.GHOST, Icons.DINOSAUR, Icons.BEETLE, Icons.ANCHOR};
                    case 49 -> icons = new Icons[]{Icons.SPIDER, Icons.SNOWMAN, Icons.CLOWN, Icons.TARGET, Icons.SUN_GLASSES, Icons.STOP, Icons.ANCHOR, Icons.LIGHTNING};
                    case 50 -> icons = new Icons[]{Icons.SNOWMAN, Icons.WATCH, Icons.PEN, Icons.BIRD, Icons.ZEBRA, Icons.LIPS, Icons.ICE, Icons.DRAGON};
                    case 51 -> icons = new Icons[]{Icons.SNOWMAN, Icons.HAND, Icons.TREE, Icons.APPLE, Icons.LOCK, Icons.BOMB, Icons.CAT, Icons.SPIDER_WEB};
                    case 52 -> icons = new Icons[]{Icons.MOON, Icons.SNOWMAN, Icons.KEY, Icons.SUN, Icons.CANDLE, Icons.TURTLE, Icons.YIN_AND_YANG, Icons.CARROT};
                    case 53 -> icons = new Icons[]{Icons.SCISSOR, Icons.SNOWMAN, Icons.HEART, Icons.WATER, Icons.GHOST, Icons.HORSE, Icons.CLEF, Icons.SHAMROCK};
                    case 54 -> icons = new Icons[]{Icons.CHEESE, Icons.CAR, Icons.BOTTLE, Icons.IGLOO, Icons.FIRE, Icons.DOLPHIN, Icons.COLOR, Icons.SNOWMAN};
                    default -> throw new IllegalStateException("Unexpected value: " + counter);
                }

                cards[counter] = new Card(image, icons, counter);

                counter++;
            }
        }
    }

    public String createLobby(String name) {
        users = new ArrayList<>();
        users.add(new User(name));

        try {
            socket = new Socket("localhost", 8000);
            DataOutputStream request = new DataOutputStream(socket.getOutputStream());
            request.writeUTF("Create Lobby " + name);
            DataInputStream response = new DataInputStream(socket.getInputStream());
            lobbyCode = response.readUTF();
            if(lobbyCode.equals("Error")) leaveLobby();
            listen = new ListenThread(socket, this);
            listen.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lobbyCode;
    }

    public void setLobbyCode(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public void startGame() {
        listen.cancel();
        try {
            socket = new Socket("localhost", 8000);
            DataOutputStream request = new DataOutputStream(socket.getOutputStream());
            request.writeUTF("Start Game " + lobbyCode + " " + position);
            DataInputStream response = new DataInputStream(socket.getInputStream());
            String r = response.readUTF();
            if(r.equals("Error")) {
                frame.error();
                return;
            }
            String[] order = r.split("  ");
            String[] data = order[0].split(" ");
            String[] array = order[1].split(" ");
            ArrayList<Card> cache = new ArrayList<>();
            for(String a : array) {
                cache.add(cards[Integer.parseInt(a)]);
            }
            cards = cache.toArray(new Card[0]);

            for (int i = 0; i < users.size(); i++) {
                users.get(i).setTopCard(cards[cards.length - 1 - i]);
            }

            topCard = cards.length - 1 - users.size();

            listen = new ListenThread(socket, this);
            listen.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gameHasBeenStarted(String r) {
        String[] order = r.split("  ");
        String[] array = order[1].split(" ");
        ArrayList<Card> cache = new ArrayList<>();
        for(String a : array) {
            cache.add(cards[Integer.parseInt(a)]);
        }
        cards = cache.toArray(new Card[0]);

        for (int i = 0; i < users.size(); i++) {
            users.get(i).setTopCard(cards[cards.length - 1 - i]);
        }

        topCard = cards.length - 1 - users.size();
        frame.gameHasBeenStarted();
    }

    public void joinLobby(String lobbyCode, String name) {
        users = new ArrayList<>();

        try {
            socket = new Socket("localhost", 8000);
            DataOutputStream request = new DataOutputStream(socket.getOutputStream());
            request.writeUTF("Join Lobby " + lobbyCode + " " + name);
            DataInputStream response = new DataInputStream(socket.getInputStream());
            String r = response.readUTF();
            if(r.equals("Error")) {
                frame.error();
                return;
            };
            String[] order = r.split(" ");
            position = Integer.parseInt(order[0]);
            for(int i = 1; i < order.length; i++) {
                users.add(new User(order[i]));
            }
            listen = new ListenThread(socket, this);
            listen.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToLobby() {
        frame.backToLobby();
    }

    public void matchCard(Icons icon) {
        listen.cancel();
        boolean equal = false;
        for(Icons i: cards[topCard].getIcons()) {
            if(icon.equals(i)) {
                equal = true;
                break;
            }
        }

        if(equal) {
            try {
                socket = new Socket("localhost", 8000);
                DataOutputStream request = new DataOutputStream(socket.getOutputStream());
                request.writeUTF("Match Card " + lobbyCode + " " + position + " " + icon.ordinal());
                DataInputStream response = new DataInputStream(socket.getInputStream());
                String r2 = response.readUTF();
                if(r2.equals("Error")) {
                    frame.leaveLobby();
                    return;
                }
                String[] r = r2.split(" ");
                if(r[0].equals("Player") && r[1].equals("Wins")) {
                    users.get(Integer.parseInt(r[2])).setTopCard(cards[topCard]);
                    topCard--;
                    frame.playerWins(r);
                    return;
                }
                if (Boolean.parseBoolean(r2)) {
                    users.get(position).setTopCard(cards[topCard]);
                    topCard--;
                }
                listen = new ListenThread(socket, this);
                listen.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void leaveLobby() {
        listen.cancel();
        listen = null;
        try {
            socket = new Socket("localhost", 8000);
            DataOutputStream request = new DataOutputStream(socket.getOutputStream());
            request.writeUTF("Quit Lobby " + lobbyCode + " " + position);
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.leaveLobby();
    }

    public void someoneJoined(String newUser) {
        users.add(new User(newUser));
        frame.someoneJoined();
    }

    public void someoneLeaved(int position) {
        users.remove(position);
        frame.someoneLeaved();
    }

    public void someoneMatchedCard(int position) {
        if(this.position != position) {
            users.get(position).setTopCard(cards[topCard]);
            topCard--;
            frame.someoneMatchedCard();
        }
    }

    public Card getTopCard() {
        return cards[topCard];
    }

    public Card[] getCards() {
        return cards;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public int getPosition() {
        return position;
    }

    public ArrayList<Icons> getIcons() {
         ArrayList<Icons> icons = new ArrayList<>();
         for(Icons icon: users.get(position).getTopCard().getIcons()) {
             if(!icons.contains(icon)) {
                 icons.add(icon);
             }
         }
         for(Icons icon: cards[topCard].getIcons()) {
             if(!icons.contains(icon)) {
                 icons.add(icon);
             }
         }
        Collections.shuffle(icons);

         return icons;
    }
}
