import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
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

    // Other helper methods
    public static BufferedImage scaleImage(BufferedImage original, float scaleFactor) {
        int targetWidth = Math.max(1, (int) (original.getWidth() * scaleFactor));
        int targetHeight = Math.max(1, (int) (original.getHeight() * scaleFactor));

        Image tmp = original.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
}