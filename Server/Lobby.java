package Server;

import java.awt.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class Lobby {
    private ArrayList<User> users;
    private Card[] cards;
    private int topCard;
    private boolean running;
    private long lastTime;

    public Lobby(User creator) {
        users = new ArrayList<>();
        users.add(creator);
        loadLobby();
        setLastTime();
    }

    private void loadLobby() {
        cards = new Card[55];

        for (int i = 0; i < 55; i++) {
            Icons[] icons;

            switch(i) {
                case 0 -> icons = new Icons[]{Icons.MOON, Icons.WATCH, Icons.PERSON, Icons.EYE, Icons.GHOST, Icons.TREE, Icons.CAR, Icons.STOP_SIGN};
                case 1 -> icons = new Icons[]{Icons.DINOSAUR, Icons.SCISSOR, Icons.COLOR, Icons.PEN, Icons.DOG, Icons.KEY, Icons.SPIDER_WEB, Icons.STOP_SIGN};
                case 2 -> icons = new Icons[]{Icons.DOLPHIN, Icons.CANDLE, Icons.ZEBRA, Icons.EXCLAMATION_MARK, Icons.CAT, Icons.HORSE, Icons.CACTUS, Icons.STOP_SIGN};
                case 3 -> icons = new Icons[]{Icons.STOP_SIGN, Icons.WATER, Icons.SKULL, Icons.LEAF, Icons.FIRE, Icons.ART, Icons.LIPS, Icons.BOMB};
                case 4 -> icons = new Icons[]{Icons.LOCK, Icons.BEETLE, Icons.CHEESE, Icons.QUESTION_MARK, Icons.HEART, Icons.STOP_SIGN, Icons.SUN, Icons.ICE};
                case 5 -> icons = new Icons[]{Icons.IGLOO, Icons.ICE_CUBE, Icons.CARROT, Icons.HAND, Icons.LIGHTNING_BULB, Icons.CLEF, Icons.OK, Icons.STOP_SIGN};
                case 6 -> icons = new Icons[]{Icons.YIN_AND_YANG, Icons.BOTTLE, Icons.DRAGON, Icons.APPLE, Icons.STOP_SIGN, Icons.SHAMROCK, Icons.FRIED_EGG, Icons.STOP};
                case 7 -> icons = new Icons[]{Icons.ART, Icons.ICE_CUBE, Icons.EXCLAMATION_MARK, Icons.CHEESE, Icons.SHAMROCK, Icons.TARGET, Icons.PEN, Icons.TREE};
                case 8 -> icons = new Icons[]{Icons.SUN, Icons.IGLOO, Icons.SKULL, Icons.FRIED_EGG, Icons.TARGET, Icons.GHOST, Icons.SPIDER_WEB, Icons.ZEBRA};
                case 9 -> icons = new Icons[]{Icons.BEETLE, Icons.CARROT, Icons.TARGET, Icons.CAT, Icons.PERSON, Icons.SCISSOR, Icons.BOTTLE, Icons.LIPS};
                case 10 -> icons = new Icons[]{Icons.BOMB, Icons.YIN_AND_YANG, Icons.CAR, Icons.HORSE, Icons.TARGET, Icons.ICE, Icons.DINOSAUR, Icons.LIGHTNING_BULB};
                case 11 -> icons = new Icons[]{Icons.MOON, Icons.STOP, Icons.COLOR, Icons.CACTUS, Icons.LOCK, Icons.WATER, Icons.OK, Icons.TARGET};
                case 12 -> icons = new Icons[]{Icons.LEAF, Icons.DOLPHIN, Icons.EYE, Icons.TARGET, Icons.HAND, Icons.DRAGON, Icons.KEY, Icons.HEART};
                case 13 -> icons = new Icons[]{Icons.FIRE, Icons.WATCH, Icons.CANDLE, Icons.CLEF, Icons.TARGET, Icons.APPLE, Icons.DOG, Icons.QUESTION_MARK};
                case 14 -> icons = new Icons[]{Icons.YIN_AND_YANG, Icons.LIGHTNING, Icons.COLOR, Icons.TREE, Icons.LEAF, Icons.ZEBRA, Icons.CLEF, Icons.BEETLE};
                case 15 -> icons = new Icons[]{Icons.LIGHTNING, Icons.SHAMROCK, Icons.QUESTION_MARK, Icons.MOON, Icons.DOLPHIN, Icons.SPIDER_WEB, Icons.LIPS, Icons.LIGHTNING_BULB};
                case 16 -> icons = new Icons[]{Icons.LIGHTNING, Icons.FIRE, Icons.CAT, Icons.GHOST, Icons.KEY, Icons.ICE, Icons.ICE_CUBE, Icons.STOP};
                case 17 -> icons = new Icons[]{Icons.CHEESE, Icons.SCISSOR, Icons.LIGHTNING, Icons.BOMB, Icons.CANDLE, Icons.EYE, Icons.FRIED_EGG, Icons.OK};
                case 18 -> icons = new Icons[]{Icons.DOG, Icons.PERSON, Icons.LIGHTNING, Icons.ART, Icons.HORSE, Icons.DRAGON, Icons.IGLOO, Icons.LOCK};
                case 19 -> icons = new Icons[]{Icons.WATER, Icons.EXCLAMATION_MARK, Icons.LIGHTNING, Icons.DINOSAUR, Icons.WATCH, Icons.HAND, Icons.SUN, Icons.BOTTLE};
                case 20 -> icons = new Icons[]{Icons.LIGHTNING, Icons.APPLE, Icons.CACTUS, Icons.CARROT, Icons.SKULL, Icons.HEART, Icons.PEN, Icons.CAR};
                case 21 -> icons = new Icons[]{Icons.DINOSAUR, Icons.IGLOO, Icons.STOP, Icons.CLOWN, Icons.HEART, Icons.TREE, Icons.LIPS, Icons.CANDLE};
                case 22 -> icons = new Icons[]{Icons.ICE, Icons.BOTTLE, Icons.ART, Icons.EYE, Icons.SPIDER_WEB, Icons.CACTUS, Icons.CLOWN, Icons.CLEF};
                case 23 -> icons = new Icons[]{Icons.SUN, Icons.SHAMROCK, Icons.LEAF, Icons.CAT, Icons.DOG, Icons.CAR, Icons.CLOWN, Icons.OK};
                case 24 -> icons = new Icons[]{Icons.DRAGON, Icons.CARROT, Icons.COLOR, Icons.QUESTION_MARK, Icons.EXCLAMATION_MARK, Icons.CLOWN, Icons.BOMB, Icons.GHOST};
                case 25 -> icons = new Icons[]{Icons.SCISSOR, Icons.CLOWN, Icons.LOCK, Icons.WATCH, Icons.YIN_AND_YANG, Icons.ICE_CUBE, Icons.DOLPHIN, Icons.SKULL};
                case 26 -> icons = new Icons[]{Icons.MOON, Icons.FRIED_EGG, Icons.PEN, Icons.HAND, Icons.HORSE, Icons.BEETLE, Icons.FIRE, Icons.CLOWN};
                case 27 -> icons = new Icons[]{Icons.CHEESE, Icons.CLOWN, Icons.WATER, Icons.KEY, Icons.ZEBRA, Icons.APPLE, Icons.PERSON, Icons.LIGHTNING_BULB};
                case 28 -> icons = new Icons[]{Icons.DOLPHIN, Icons.FRIED_EGG, Icons.DOG, Icons.WATER, Icons.TREE, Icons.ICE, Icons.CARROT, Icons.SUN_GLASSES};
                case 29 -> icons = new Icons[]{Icons.YIN_AND_YANG, Icons.PERSON, Icons.EXCLAMATION_MARK, Icons.SUN_GLASSES, Icons.HEART, Icons.SPIDER_WEB, Icons.FIRE, Icons.OK};
                case 30 -> icons = new Icons[]{Icons.CHEESE, Icons.SUN_GLASSES, Icons.CLEF, Icons.DINOSAUR, Icons.DRAGON, Icons.MOON, Icons.SKULL, Icons.CAT};
                case 31 -> icons = new Icons[]{Icons.BEETLE, Icons.SHAMROCK, Icons.SUN_GLASSES, Icons.CACTUS, Icons.KEY, Icons.WATCH, Icons.IGLOO, Icons.BOMB};
                case 32 -> icons = new Icons[]{Icons.LEAF, Icons.PEN, Icons.SUN_GLASSES, Icons.GHOST, Icons.BOTTLE, Icons.CANDLE, Icons.LIGHTNING_BULB, Icons.LOCK};
                case 33 -> icons = new Icons[]{Icons.SCISSOR, Icons.QUESTION_MARK, Icons.HAND, Icons.CAR, Icons.ART, Icons.ZEBRA, Icons.STOP, Icons.SUN_GLASSES};
                case 34 -> icons = new Icons[]{Icons.ICE_CUBE, Icons.COLOR, Icons.EYE, Icons.SUN_GLASSES, Icons.HORSE, Icons.APPLE, Icons.LIPS, Icons.SUN};
                case 35 -> icons = new Icons[]{Icons.SPIDER, Icons.QUESTION_MARK, Icons.OK, Icons.TREE, Icons.HORSE, Icons.KEY, Icons.BOTTLE, Icons.SKULL};
                case 36 -> icons = new Icons[]{Icons.DRAGON, Icons.SPIDER, Icons.CAR, Icons.WATER, Icons.SPIDER_WEB, Icons.CANDLE, Icons.ICE_CUBE, Icons.BEETLE};
                case 37 -> icons = new Icons[]{Icons.COLOR, Icons.HEART, Icons.WATCH, Icons.LIGHTNING_BULB, Icons.ART, Icons.SPIDER, Icons.CAT, Icons.FRIED_EGG};
                case 38 -> icons = new Icons[]{Icons.DOLPHIN, Icons.PERSON, Icons.PEN, Icons.SUN, Icons.CLEF, Icons.BOMB, Icons.SPIDER, Icons.STOP};
                case 39 -> icons = new Icons[]{Icons.SPIDER, Icons.SHAMROCK, Icons.EYE, Icons.ZEBRA, Icons.LOCK, Icons.DINOSAUR, Icons.CARROT, Icons.FIRE};
                case 40 -> icons = new Icons[]{Icons.SPIDER, Icons.CHEESE, Icons.CACTUS, Icons.DOG, Icons.GHOST, Icons.LIPS, Icons.YIN_AND_YANG, Icons.HAND};
                case 41 -> icons = new Icons[]{Icons.SCISSOR, Icons.SPIDER, Icons.EXCLAMATION_MARK, Icons.APPLE, Icons.ICE, Icons.IGLOO, Icons.MOON, Icons.LEAF};
                case 42 -> icons = new Icons[]{Icons.FIRE, Icons.SCISSOR, Icons.CACTUS, Icons.TREE, Icons.SUN, Icons.LIGHTNING_BULB, Icons.DRAGON, Icons.ANCHOR};
                case 43 -> icons = new Icons[]{Icons.CHEESE, Icons.HORSE, Icons.SPIDER_WEB, Icons.WATCH, Icons.ANCHOR, Icons.LEAF, Icons.STOP, Icons.CARROT};
                case 44 -> icons = new Icons[]{Icons.YIN_AND_YANG, Icons.CAT, Icons.EYE, Icons.QUESTION_MARK, Icons.PEN, Icons.WATER, Icons.IGLOO, Icons.ANCHOR};
                case 45 -> icons = new Icons[]{Icons.ANCHOR, Icons.MOON, Icons.HEART, Icons.DOG, Icons.ZEBRA, Icons.BOTTLE, Icons.ICE_CUBE, Icons.BOMB};
                case 46 -> icons = new Icons[]{Icons.CLEF, Icons.FRIED_EGG, Icons.EXCLAMATION_MARK, Icons.CAR, Icons.KEY, Icons.LOCK, Icons.LIPS, Icons.ANCHOR};
                case 47 -> icons = new Icons[]{Icons.SKULL, Icons.COLOR, Icons.HAND, Icons.ICE, Icons.CANDLE, Icons.ANCHOR, Icons.SHAMROCK, Icons.PERSON};
                case 48 -> icons = new Icons[]{Icons.ART, Icons.DOLPHIN, Icons.OK, Icons.APPLE, Icons.GHOST, Icons.DINOSAUR, Icons.BEETLE, Icons.ANCHOR};
                case 49 -> icons = new Icons[]{Icons.SPIDER, Icons.SNOWMAN, Icons.CLOWN, Icons.TARGET, Icons.SUN_GLASSES, Icons.STOP_SIGN, Icons.ANCHOR, Icons.LIGHTNING};
                case 50 -> icons = new Icons[]{Icons.SNOWMAN, Icons.WATCH, Icons.PEN, Icons.OK, Icons.ZEBRA, Icons.LIPS, Icons.ICE, Icons.DRAGON};
                case 51 -> icons = new Icons[]{Icons.SNOWMAN, Icons.HAND, Icons.TREE, Icons.APPLE, Icons.LOCK, Icons.BOMB, Icons.CAT, Icons.SPIDER_WEB};
                case 52 -> icons = new Icons[]{Icons.MOON, Icons.SNOWMAN, Icons.KEY, Icons.SUN, Icons.CANDLE, Icons.ART, Icons.YIN_AND_YANG, Icons.CARROT};
                case 53 -> icons = new Icons[]{Icons.SCISSOR, Icons.SNOWMAN, Icons.HEART, Icons.WATER, Icons.GHOST, Icons.HORSE, Icons.CLEF, Icons.SHAMROCK};
                case 54 -> icons = new Icons[]{Icons.CHEESE, Icons.CAR, Icons.BOTTLE, Icons.IGLOO, Icons.FIRE, Icons.DOLPHIN, Icons.COLOR, Icons.SNOWMAN};
                default -> throw new IllegalStateException("Unexpected value: " + i);
            }

            cards[i] = new Card(icons, i);
        }
    }

    public int joinLobby(User user) {
        setLastTime();
        users.add(user);
        return users.size() - 1;
    }

    public boolean leaveLobby(Socket client, int position) {
        setLastTime();
        System.out.println(users.size());
        users.get(position).closeClient();
        users.remove(position);
        System.out.println(users.size());
        return users.size() == 0;
    }

    public void startGame() {
        setLastTime();
        ArrayList<Card> solution = new ArrayList<>();
        Collections.addAll(solution, cards);
        Collections.shuffle(solution);
        cards = solution.toArray(new Card[0]);
        topCard = 54;
        for(User user : users) {
            user.setTopCard(cards[topCard]);
            user.setCardCount(1);
            topCard--;
        }
        running = true;
    }

    public String getCardOrder() {
        StringBuilder builder = new StringBuilder();
        for (Card card: cards) {
            builder.append(" ").append(card.getId());
        }
        return builder.toString();
    }

    public Card getTopCard() {
        return cards[topCard];
    }

    public boolean getWinner() {
        setLastTime();
        if(running) {
            return topCard == 0;
        }
        return false;
    }

    public String getPlayerCount() {
        StringBuilder builder = new StringBuilder();
        for(User user : users) {
            builder.append(" ").append(user.getCardCount());
        }
        return builder.toString();
    }

    public boolean matchCard(int position, int icon) {
        setLastTime();
        if(running) {
            if(cards[topCard].findIcon(Icons.values()[icon]) && users.get(position).getTopCard().findIcon(Icons.values()[icon])) {
                users.get(position).setTopCard(cards[topCard]);
                users.get(position).setCardCount(users.get(position).getCardCount() + 1);
                topCard--;
                return true;
            }
        }

        return false;
    }

    public void addUser(User user) {
        if(users.size() <= 4) {
            users.add(user);
        }
    }

    public void changeClient(Socket client, int position) {
        users.get(position).setClient(client);
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public String getUserNames(String lobbyCode) {
        StringBuilder userString = new StringBuilder();
        for(User user : Server.lobbys.get(lobbyCode).getUsers()) {
            userString.append(" ").append(user.getName());
        }
        return userString.toString();
    }

    public boolean isRunning() {
        return running;
    }

    public void setLastTime() {
        lastTime = System.currentTimeMillis();
    }

    public long getTime() {
        return lastTime;
    }
}
