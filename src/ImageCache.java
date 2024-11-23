package src;

import javax.swing.*;

import java.awt.Image;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;

//helper class to preload and retrieve images so the game runs without freezing
public class ImageCache {

    // we create a hashmap here bc its a really quick data structure
    private static HashMap<File, ImageIcon> imageCache = new HashMap<>();
    private static ArrayList<File> imagePathArray;

    // this method retrieves an image from the cache or loads it if not present
    public static ImageIcon getImageIcon(File imagePath) {
        // Check if the image is already cached
        if (!imageCache.containsKey(imagePath)) {// this shouldn't be needed but just in case
            ImageIcon imageIcon = new ImageIcon(ImageCache.class.getResource(imagePath.toString()));
            imageCache.put(imagePath, imageIcon);
        }
        return imageCache.get(imagePath);
    }

    // this method preloads sprites so that there is not a freeze when loading into
    // a
    // game
    public static void preloadImages() {
        addFileNamesToArrayList();
        ImageIcon resizedImageIcon = null; 

        for (File path : imagePathArray) {
            if (!imageCache.containsKey(path)) {// if imagePath does not already exist in the hashmap
                System.out.println(path.toString());
                ImageIcon imageIcon = new ImageIcon(ImageCache.class.getResource(path.toString()));// turn the png
                    resizedImageIcon = resizeImageIcon(imageIcon, 200, 200);//going to need resized images for different game modes, due to the changes in Card size
                    imageCache.put(path, resizedImageIcon);
  
            }

        }
    }

    // resizes images as needed, so far only the star.png needs resized
    private static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image newImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImage);
    }

    //we add all image files to list so that they can be inserted into the hashmap later.
    //the created arrayList is also used to grab the different Files when creating Cards
    private static void addFileNamesToArrayList() {
        imagePathArray = new ArrayList<>();
        imagePathArray.add(new File("../resources/CardSprites/apple.jpg"));
        imagePathArray.add(new File("../resources/CardSprites/duck.jpg"));
        imagePathArray.add(new File("../resources/CardSprites/frog.png"));
        imagePathArray.add(new File("../resources/CardSprites/star.png"));
        imagePathArray.add(new File("../resources/CardSprites/heart.jpg"));
        imagePathArray.add(new File("../resources/CardSprites/musicNote.png"));
        imagePathArray.add(new File("../resources/CardSprites/smiley.png"));
        imagePathArray.add(new File("../resources/CardSprites/turtle.jpg"));
        imagePathArray.add(new File("../resources/CardSprites/cow.jpg"));
        imagePathArray.add(new File("../resources/CardSprites/soccerBall.png"));
        imagePathArray.add(new File("../resources/CardSprites/back.jpg"));
        imagePathArray.add(new File("../resources/Other/trees.jpg"));
        imagePathArray.add(new File("../resources/CardSprites/bomb.png"));

    }

    public static File getImageFile(int index) {
        return imagePathArray.get(index);
    }

    //we use this method to grab a certin File based on its name and path
    public static File getImageFile(String dir, String name, String fileType) {//TODO: this is not working : need to probab
        String targetFilePath = String.format("../resources/%s/%s.%s", dir, name, fileType);
        //System.out.println(targetFilePath);
        return new File(targetFilePath);
        //return getImageIcon(new File(targetFilePath));
    }

}