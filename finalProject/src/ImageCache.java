package finalProject.src;

import javax.swing.*;

import java.awt.Image;
import java.util.HashMap;
import java.util.ArrayList;

//helper class to preload and retrieve images so the game runs without freezing
public class ImageCache {
    
    // Hashmap, basically like a python dictionary. Its key, value pair and is
    // really fast
    private static HashMap<String, ImageIcon> imageCache = new HashMap<>();
    private static ArrayList<String> imagePathArray;

    // this method retrieves an image from the cache or loads it if not present
    public static ImageIcon getImage(String imagePath) {
        // Check if the image is already cached
        if (!imageCache.containsKey(imagePath)) {// this shouldn't be needed but just in case
            ImageIcon imageIcon = new ImageIcon(ImageCache.class.getResource(imagePath));
            imageCache.put(imagePath, imageIcon);
        }
        return imageCache.get(imagePath);
    }

    // this method preloads sprites so that there is not a freeze when loading into a
    // game
    public static void preloadImages() {
        addFileNamesToArrayList();
        ImageIcon resizedImageIcon = null;
        for (String path : imagePathArray) {
            if (!imageCache.containsKey(path)) {// if imagePath does not already exist in the hashmap
                ImageIcon imageIcon = new ImageIcon(ImageCache.class.getResource(path));// turn the png FilePath into an
                                                                                        // ImageIcon Obj
                if (path.equals("..\\CardSprites\\star.png")) {
                    resizedImageIcon = resizeImageIcon(imageIcon, 200, 200);
                    imageCache.put(path, resizedImageIcon);
                } else {
                    imageCache.put(path, imageIcon); // put that imageIcon in hashmap with index being String filePath
                }
            }

        }
    }

    // resizes images as needed, so far only the star.png needs resized
    private static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }

    private static void addFileNamesToArrayList() {
        imagePathArray = new ArrayList<>();
        imagePathArray.add("..\\CardSprites\\star.png");
        imagePathArray.add("..\\CardSprites\\apple.jpg");
        imagePathArray.add("..\\CardSprites\\duck.jpg");
        imagePathArray.add("..\\CardSprites\\frog.png");
        imagePathArray.add("..\\CardSprites\\heart.jpg");
        imagePathArray.add("..\\CardSprites\\musicNote.png");
        imagePathArray.add("..\\CardSprites\\smiley.png");
        imagePathArray.add("..\\CardSprites\\turtle.jpg");

    }

    // returns fileName of a sprite at specified index
    public static String getImagePathArrayElement(int index) {
        return imagePathArray.get(index);
    }

}