package src;

import java.io.File;
public class BombCard extends Card{
    private static File imagePathFile = ImageCache.getImageFile("CardSprites", "bomb", "png");

    public BombCard(GameManager game){
        super(imagePathFile, game);
    }
    
}
