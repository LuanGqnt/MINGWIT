import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {

    private BufferedImage bgSprite;
    private BufferedImage startButtonSprite;
    private BufferedImage settingsSprite;
    private BufferedImage exitSprite;

    private SpriteAnimation catAnimation;

    public interface MenuListener {
        void onPlayClicked();

        void onSettingsClicked();
    }

    public MainMenuPanel(MenuListener listener) {
        setLayout(new BorderLayout());
        setBackground(new Color(35, 39, 42));

        SoundManager.playMusic("../assets/music/main_menu_theme.wav", -10.0f); // Play BGM at -10 dB

        bgSprite = SpriteLoader.loadImage("../assets/images/main_menu_background.png");
        startButtonSprite = SpriteLoader.loadImage("../assets/images/start_button.png");
        settingsSprite = SpriteLoader.loadImage("../assets/images/settings_button.png");
        exitSprite = SpriteLoader.loadImage("../assets/images/exit_button.png");
        catAnimation = SpriteLoader.loadAnimation(
            // "../assets/images/cat_no1.png",
            "../assets/images/cat_menu.png",
            96,
            96,
            10,
            220,
            true,
            96,
            96,
            this
        );
        if (catAnimation != null) {
            catAnimation.play();
        }
      
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 130, 20));

        JButton playButton = createStyledButton(startButtonSprite);
        JButton settingsButton = createStyledButton(settingsSprite);
        JButton exitButton = createStyledButton(exitSprite);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        buttonPanel.add(playButton, gbc);
        buttonPanel.add(settingsButton, gbc);
        buttonPanel.add(exitButton, gbc);

        playButton.addActionListener(e -> {
            SoundManager.playSfx("../assets/sfx/button_click.wav", 0f);
            if (listener != null)
                listener.onPlayClicked();

        });

        settingsButton.addActionListener(e -> {
            SoundManager.playSfx("../assets/sfx/button_click.wav", 0f);
            if (listener != null)
                listener.onSettingsClicked();
        });

        exitButton.addActionListener(e -> {
            SoundManager.playSfx("../assets/sfx/button_click.wav", 0f);
            System.exit(0);
        });

        // Pin the button stack to the bottom of MainMenuPanel
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createStyledButton(BufferedImage buttonSprite) {
        JButton button = new JButton();
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (buttonSprite != null) {
            BufferedImage normalSprite = SpriteLoader.scaleImage(buttonSprite, 1.2f);
            BufferedImage hoverSprite = SpriteLoader.scaleImage(buttonSprite, 1.32f);

            ImageIcon normalIcon = new ImageIcon(normalSprite);
            ImageIcon hoverIcon = new ImageIcon(hoverSprite);

            button.setPreferredSize(new Dimension(hoverIcon.getIconWidth(), hoverIcon.getIconHeight()));
            button.setIcon(normalIcon);
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setVerticalTextPosition(JButton.CENTER);

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    button.setIcon(hoverIcon);
                    SoundManager.playSfx("../assets/sfx/button_hover.wav", 0f);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    button.setIcon(normalIcon);
                }
            });
        }

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bgSprite != null) {
            g.drawImage(bgSprite, 0, 0, getWidth(), getHeight(), this);
        }

        if (catAnimation != null) {
            catAnimation.draw(g, 500, 420);
        }
    }
}