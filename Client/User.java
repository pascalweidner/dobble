package Client;

public class User {
    private Card topCard;
    private String name;

    public User(String name) {
        this.name = name;
    }

    public Card getTopCard() {
        return topCard;
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }

    public String getName() {
        return name;
    }
}
