import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteLoader {
    public static BufferedImage loadImage(String fileName) {
        // Try looking for the file directly
        File file = new File(fileName);
        
        // If it doesn't exist, try prepending "assets/" if it isn't already there
        if (!file.exists()) {
            if (fileName.startsWith("assets/") || fileName.startsWith("assets\\")) {
                file = new File(fileName);
            } else {
                file = new File("assets" + File.separator + fileName);
            }
        }

        try {
            if (file.exists()) {
                return ImageIO.read(file);
            } else {
                // Let's print the absolute path so we can see exactly where it's looking if it fails
                System.out.println("Warning: Sprite not found -> " + file.getAbsolutePath());
                return null;
            }
        } catch (IOException e) {
            System.out.println("Error loading image: " + fileName);
            return null;
        }
    }

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