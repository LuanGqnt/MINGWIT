import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteLoader {
    public static BufferedImage loadImage(String fileName) {
        File file = new File(fileName);
        
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

    // Animations
    public static SpriteAnimation loadAnimation(
        String path,
        int frameWidth,
        int frameHeight,
        int frameCount,
        int frameDurationMs,
        boolean loop,
        int targetWidth,
        int targetHeight,
        Component targetComponent
    ) {
        try {
            BufferedImage sheet = ImageIO.read(new File(path));
            if (sheet == null || frameCount <= 0) {
                return null;
            }

            int sheetWidth = sheet.getWidth();
            int sheetHeight = sheet.getHeight();
            double cellWidth = (frameWidth > 0) ? frameWidth : ((double) sheetWidth / frameCount);
            double cellHeight = (frameHeight > 0) ? frameHeight : sheetHeight;

            int cols = Math.max(1, (int) Math.floor(sheetWidth / cellWidth));
            int finalW = (targetWidth > 0) ? targetWidth : (int) Math.round(cellWidth);
            int finalH = (targetHeight > 0) ? targetHeight : (int) Math.round(cellHeight);

            BufferedImage[] frames = new BufferedImage[frameCount];
            for (int i = 0; i < frameCount; i++) {
                int col = i % cols;
                int row = i / cols;

                int sx = (int) Math.round(col * cellWidth);
                int nextSx = (int) Math.round((col + 1) * cellWidth);
                int sy = (int) Math.round(row * cellHeight);
                int nextSy = (int) Math.round((row + 1) * cellHeight);

                int w = Math.max(1, Math.min(nextSx - sx, sheetWidth - sx));
                int h = Math.max(1, Math.min(nextSy - sy, sheetHeight - sy));

                if (sx >= sheetWidth || sy >= sheetHeight) {
                    System.out.println("Frame " + i + " exceeds sheet bounds for '" + path + "'");
                    return null;
                }

                BufferedImage rawFrame = sheet.getSubimage(sx, sy, w, h);
                frames[i] = resizeFrame(rawFrame, finalW, finalH);
            }

            return new SpriteAnimation(frames, frameDurationMs, loop, targetComponent);
        } catch (IOException e) {
            System.out.println("Failed to load animation '" + path + "': " + e.getMessage());
            return null;
        }
    }

    private static BufferedImage resizeFrame(BufferedImage source, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.setRenderingHint(
            RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BILINEAR
        );
        g2d.drawImage(source, 0, 0, width, height, null);
        g2d.dispose();
        return resized;
    }
}