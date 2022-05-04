package Client;

import java.awt.*;

public class Card {
    private final Image image;
    private final Icons[] icons;
    private final int id;

    public Card(Image image, Icons[] icons, int id) {
        this.image = image;
        this.icons = icons;
        this.id = id;
    }

    public Icons[] getIcons() {
        return icons;
    }

    public void draw(Graphics2D g, int x, int y, int width) {
        g.drawImage(image, x, y, width, width, null);
    }
}
