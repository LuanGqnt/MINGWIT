import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Timer;

public class SpriteAnimation {

    private final BufferedImage[] frames;
    private final int frameDurationMs;
    private final boolean loop;
    private final Component targetComponent;

    private Timer timer;
    private int currentIndex = 0;
    private boolean finished = false;

    public SpriteAnimation(
            BufferedImage[] frames,
            int frameDurationMs,
            boolean loop,
            Component targetComponent
    ) {
        this.frames = frames;
        this.frameDurationMs = Math.max(1, frameDurationMs);
        this.loop = loop;
        this.targetComponent = targetComponent;

        if (frames != null && frames.length > 0) {
            this.timer = new Timer(this.frameDurationMs, e -> tick());
        }
    }

    private void tick() {
        if (frames == null || frames.length == 0 || finished) {
            return;
        }

        if (targetComponent != null && !targetComponent.isShowing()) {
            return;
        }

        if (currentIndex + 1 < frames.length) {
            currentIndex++;
        } else if (loop) {
            currentIndex = 0;
        } else {
            finished = true;
            if (timer != null) {
                timer.stop();
            }
        }

        if (targetComponent != null) {
            targetComponent.repaint();
        }
    }

    public void play() {
        if (timer == null || frames == null || frames.length == 0) {
            return;
        }
        if (finished) {
            currentIndex = 0;
            finished = false;
        }
        if (!timer.isRunning()) {
            timer.start();
        }
        if (targetComponent != null) {
            targetComponent.repaint();
        }
    }

    public void stop() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    public void reset() {
        currentIndex = 0;
        finished = false;
        if (targetComponent != null) {
            targetComponent.repaint();
        }
    }

    public void draw(Graphics g, int x, int y) {
        if (frames == null || frames.length == 0 || g == null) {
            return;
        }
        BufferedImage frame = frames[currentIndex];
        if (frame != null) {
            g.drawImage(frame, x, y, targetComponent);
        }
    }

    public boolean isFinished() {
        return !loop && finished;
    }

    public boolean isPlaying() {
        return timer != null && timer.isRunning();
    }
}