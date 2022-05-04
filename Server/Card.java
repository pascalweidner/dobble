package Server;

import java.util.Arrays;

public class Card {
    private final Icons[] icons;
    private final int id;

    public Card(Icons[] icons, int id) {
        this.id = id;
        this.icons = icons;
    }

    public boolean findIcon(Icons icon) {
        return Arrays.asList(icons).contains(icon);
    }

    public int getId() {
        return id;
    }
}
