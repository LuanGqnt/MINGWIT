import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SettingsPanel extends JPanel {

    private final JToggleButton masterButton;
    private final JToggleButton musicButton;
    private final JToggleButton sfxButton;
    private final JToggleButton muteButton;

    private String returnScene = "GAME";

    public SettingsPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(38, 50, 56));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JButton backButton = createButton("Back", new Color(96, 125, 139));
        backButton.addActionListener(e -> SceneManager.goTo(returnScene));
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel title = new JLabel("Sound Settings", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        topPanel.add(title, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // on and off buttons
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        masterButton = createToggleButton();
        musicButton = createToggleButton();
        sfxButton = createToggleButton();
        muteButton = createToggleButton();

        addToggleRow(form, 0, "Master Volume", masterButton);
        addToggleRow(form, 1, "Music", musicButton);
        addToggleRow(form, 2, "Sound Effects", sfxButton);
        addToggleRow(form, 3, "Mute All", muteButton);

        JButton resetButton = createButton("Reset to Default", new Color(120, 144, 156));
        GridBagConstraints resetConstraints = new GridBagConstraints();
        resetConstraints.gridx = 1;
        resetConstraints.gridy = 4;
        resetConstraints.insets = new Insets(20, 10, 10, 10);
        form.add(resetButton, resetConstraints);

        add(form, BorderLayout.CENTER);

        masterButton.addActionListener(e -> {
            SoundManager.setMasterVolume(masterButton.isSelected() ? 100 : 0);
            updateToggleAppearance(masterButton);
        });

        musicButton.addActionListener(e -> {
            SoundManager.setMusicVolume(musicButton.isSelected() ? 100 : 0);
            updateToggleAppearance(musicButton);
        });

        sfxButton.addActionListener(e -> {
            SoundManager.setSfxVolume(sfxButton.isSelected() ? 100 : 0);
            updateToggleAppearance(sfxButton);
            if (sfxButton.isSelected()) {
                SoundManager.playSfx("../assets/sfx/cat_drinking_short.wav", 0f);
            }
        });

        muteButton.addActionListener(e -> {
            SoundManager.setMuted(muteButton.isSelected());
            updateToggleAppearance(muteButton);
        });

        resetButton.addActionListener(e -> {
            SoundManager.resetToDefaults();
            loadFromSoundManager();
        });

        loadFromSoundManager();
    }

    // back to mennu button
    public void setReturnScene(String sceneName) {
        this.returnScene = sceneName;
    }

    // sound master shi
    private void loadFromSoundManager() {
        masterButton.setSelected(SoundManager.getMasterVolume() > 0);
        musicButton.setSelected(SoundManager.getMusicVolume() > 0);
        sfxButton.setSelected(SoundManager.getSfxVolume() > 0);
        muteButton.setSelected(SoundManager.isMuted());

        updateToggleAppearance(masterButton);
        updateToggleAppearance(musicButton);
        updateToggleAppearance(sfxButton);
        updateToggleAppearance(muteButton);
    }

    private JToggleButton createToggleButton() {
        JToggleButton button = new JToggleButton("OFF");
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(130, 42));
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setForeground(Color.WHITE);

        // on off sprite
        ImageIcon offIcon = loadSpriteIcon("../assets/sprites/toggle_off.png", 130, 42);
        ImageIcon onIcon = loadSpriteIcon("../assets/sprites/toggle_on.png", 130, 42);

        if (offIcon != null && onIcon != null) {
            button.setIcon(offIcon);
            button.setSelectedIcon(onIcon);
            button.setText("");
        }

        return button;
    }

    private void updateToggleAppearance(JToggleButton button) {
        boolean on = button.isSelected();

        if (button.getIcon() == null) {
            button.setText(on ? "ON" : "OFF");
            button.setBackground(on
                    ? new Color(46, 125, 50)
                    : new Color(97, 97, 97));
        }
    }

    private void addToggleRow(JPanel form, int row, String labelText,
            JToggleButton toggleButton) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = row;
        c.insets = new Insets(12, 10, 12, 10);

        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 18));

        c.gridx = 0;
        c.anchor = GridBagConstraints.EAST;
        form.add(label, c);

        c.gridx = 1;
        c.anchor = GridBagConstraints.WEST;
        form.add(toggleButton, c);
    }

    private JButton createButton(String text, Color background) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(background);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        return button;
    }

    private ImageIcon loadSpriteIcon(String path, int width, int height) {
        BufferedImage sprite = loadSprite(path);
        if (sprite == null) {
            return null;
        }

        Image scaled = sprite.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private BufferedImage loadSprite(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException | IllegalArgumentException ex) {
            System.err.println("Could not load sprite: " + path + " (" + ex.getMessage() + ")");
            return null;
        }
    }

    private BufferedImage loadSpriteFrame(String path, int x, int y,
            int frameWidth, int frameHeight) {
        BufferedImage sheet = loadSprite(path);
        if (sheet == null) {
            return null;
        }

        if (x < 0 || y < 0 || frameWidth <= 0 || frameHeight <= 0
                || x + frameWidth > sheet.getWidth()
                || y + frameHeight > sheet.getHeight()) {
            throw new IllegalArgumentException("Sprite frame is outside the sheet bounds.");
        }

        return sheet.getSubimage(x, y, frameWidth, frameHeight);
    }

    private ImageIcon loadSpriteFrameIcon(String path, int x, int y,
            int frameWidth, int frameHeight,
            int displayWidth, int displayHeight) {
        BufferedImage frame = loadSpriteFrame(path, x, y, frameWidth, frameHeight);
        if (frame == null) {
            return null;
        }

        Image scaled = frame.getScaledInstance(
                displayWidth, displayHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
