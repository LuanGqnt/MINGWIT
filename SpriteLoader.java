import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteLoader {
    public static BufferedImage loadImage(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                return ImageIO.read(file);
            } else {
                System.out.println("Warning: Sprite not found -> " + fileName);
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error loading image: " + fileName);
            return null;
        }
    }
}