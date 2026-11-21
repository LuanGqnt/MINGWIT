import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class SoundManager {
    private static Clip musicClip;

    public static void playSfx(String resourcePath) {
        try {
            AudioInputStream audioIn = getAudioStream(resourcePath);
            if (audioIn == null)
                return;

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.addLineListener(event -> {
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                    clip.close();
                }
            });
            clip.start();
        } catch (Exception e) {
            System.err.println("Failed to play SFX (" + resourcePath + "): " + e.getMessage());
        }
    }

    public static void playMusic(String resourcePath, float volumeDb) {
        stopMusic();
        try {
            AudioInputStream audioIn = getAudioStream(resourcePath);
            if (audioIn == null)
                return;

            musicClip = AudioSystem.getClip();
            musicClip.open(audioIn);
            setClipVolume(musicClip, volumeDb);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.err.println("Failed to play music (" + resourcePath + "): " + e.getMessage());
        }
    }

    public static void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
            musicClip = null;
        }
    }

    private static void setClipVolume(Clip clip, float decibels) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float clamped = Math.max(gainControl.getMinimum(), Math.min(gainControl.getMaximum(), decibels));
            gainControl.setValue(clamped);
        }
    }

    private static AudioInputStream getAudioStream(String resourcePath) throws Exception {
        URL url = SoundManager.class.getResource(resourcePath);
        if (url != null) {
            return AudioSystem.getAudioInputStream(url);
        }

        InputStream rawStream = new BufferedInputStream(new java.io.FileInputStream(resourcePath));
        return AudioSystem.getAudioInputStream(rawStream);
    }
}
