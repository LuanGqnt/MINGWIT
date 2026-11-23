
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

public class SoundManager {

    private static Clip musicClip;

    private static int masterVolume = 100;
    private static int musicVolume = 100;
    private static int sfxVolume = 100;
    private static boolean muted = false;

    public static void setMasterVolume(int volume) {
        masterVolume = clamp(volume);
        updateMusicVolume();
    }

    public static int getMasterVolume() {
        return masterVolume;
    }

    public static void setMusicVolume(int volume) {
        musicVolume = clamp(volume);
        updateMusicVolume();
    }

    public static int getMusicVolume() {
        return musicVolume;
    }

    public static void setSfxVolume(int volume) {
        sfxVolume = clamp(volume);
    }

    public static int getSfxVolume() {
        return sfxVolume;
    }

    public static void setMuted(boolean value) {
        muted = value;
        updateMusicVolume();
    }

    public static boolean isMuted() {
        return muted;
    }

    public static void resetToDefaults() {
        masterVolume = 100;
        musicVolume = 100;
        sfxVolume = 100;
        muted = false;
        updateMusicVolume();
    }

    private static int clamp(int volume) {
        return Math.max(0, Math.min(100, volume));
    }

    public static void playSfx(String resourcePath, float volumeDb) {
        if (muted || masterVolume == 0 || sfxVolume == 0) {
            return;
        }

        try {
            AudioInputStream audioIn = getAudioStream(resourcePath);

            if (audioIn == null) {
                return;
            }

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);

            float adjustedVolume = volumeDb
                    + volumeToDb(masterVolume)
                    + volumeToDb(sfxVolume);

            setClipVolume(clip, adjustedVolume);

            clip.addLineListener(event -> {
                if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                    clip.close();
                }
            });

            clip.start();

        } catch (Exception e) {
            System.err.println("Failed to play SFX (" +
                    resourcePath + "): " + e.getMessage());
        }
    }

    public static void playMusic(String resourcePath, float volumeDb) {
        stopMusic();

        if (muted || masterVolume == 0 || musicVolume == 0) {
            return;
        }

        try {
            AudioInputStream audioIn = getAudioStream(resourcePath);

            if (audioIn == null) {
                return;
            }

            musicClip = AudioSystem.getClip();
            musicClip.open(audioIn);

            setClipVolume(musicClip,
                    volumeDb
                            + volumeToDb(masterVolume)
                            + volumeToDb(musicVolume));

            musicClip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            System.err.println("Failed to play music (" +
                    resourcePath + "): " + e.getMessage());
        }
    }

    public static void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
            musicClip = null;
        }
    }

    private static void updateMusicVolume() {
        if (musicClip == null) {
            return;
        }

        if (muted || masterVolume == 0 || musicVolume == 0) {
            musicClip.stop();
            return;
        }

        if (musicClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gain = (FloatControl) musicClip.getControl(
                    FloatControl.Type.MASTER_GAIN);

            float volume = volumeToDb(masterVolume)
                    + volumeToDb(musicVolume);

            float clamped = Math.max(
                    gain.getMinimum(),
                    Math.min(gain.getMaximum(), volume));

            gain.setValue(clamped);

            if (!musicClip.isRunning()) {
                musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    private static float volumeToDb(int volume) {
        if (volume <= 0) {
            return -80f;
        }

        return (float) (20.0 * Math.log10(volume / 100.0));
    }

    private static void setClipVolume(Clip clip, float decibels) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) clip.getControl(
                    FloatControl.Type.MASTER_GAIN);

            float clamped = Math.max(
                    gainControl.getMinimum(),
                    Math.min(gainControl.getMaximum(), decibels));

            gainControl.setValue(clamped);
        }
    }

    private static AudioInputStream getAudioStream(
            String resourcePath) throws Exception {

        URL url = SoundManager.class.getResource(resourcePath);

        if (url != null) {
            return AudioSystem.getAudioInputStream(url);
        }

        InputStream rawStream = new BufferedInputStream(
                new java.io.FileInputStream(resourcePath));

        return AudioSystem.getAudioInputStream(rawStream);
    }
}
