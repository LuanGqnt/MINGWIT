import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {

    private BufferedImage bgSprite;
    private BufferedImage startButtonSprite;
    private BufferedImage settingsSprite;
    private BufferedImage exitSprite;

    public interface MenuListener {
        void onPlayClicked();
        void onSettingsClicked();
    }

    public MainMenuPanel(MenuListener listener) {
        setLayout(new BorderLayout());
        setBackground(new Color(35, 39, 42)); 

        bgSprite = SpriteLoader.loadImage("../assets/main_menu_background.png");
        startButtonSprite = SpriteLoader.loadImage("../assets/start_button.png");
        settingsSprite = SpriteLoader.loadImage("../assets/settings_button.png");
        exitSprite = SpriteLoader.loadImage("../assets/exit_button.png");

        JPanel buttonPanel = new JPanel();

        // buttonPanel.setLayout(new GridLayout(4, 1, 15, 15));
        buttonPanel.setLayout(new GridLayout(5, 1, 15, 15));
        buttonPanel.setOpaque(false); 
        buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(250, 300, 50, 300));

        JButton playButton = createStyledButton(startButtonSprite);
        JButton settingsButton = createStyledButton(settingsSprite);
        JButton exitButton = createStyledButton(exitSprite);

        playButton.addActionListener(e -> {
            if (listener != null) listener.onPlayClicked();
        });

        settingsButton.addActionListener(e -> {
            if (listener != null) listener.onSettingsClicked();
        });

        exitButton.addActionListener(e -> {
            System.exit(0);
        });

        buttonPanel.add(playButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createStyledButton(BufferedImage buttonSprite) {
        JButton button = new JButton();
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);

        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (buttonSprite != null) {
            BufferedImage scaledSprite = SpriteLoader.scaleImage(buttonSprite, 1.2f);
            
            button.setIcon(new ImageIcon(scaledSprite));
            button.setHorizontalTextPosition(JButton.CENTER);
            button.setVerticalTextPosition(JButton.CENTER);
        } else {
            button.setBackground(new Color(114, 137, 218));
            button.setContentAreaFilled(true);
        }

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(bgSprite, 0, 0, getWidth(), getHeight(), this);
    }
}