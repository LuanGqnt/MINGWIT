import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

public class MainMenuPanel extends JPanel {

    private MenuListener listener;

    public interface MenuListener {
        void onPlayClicked();
        void onSettingsClicked();
    }

    public MainMenuPanel(MenuListener listener) {
        this.listener = listener;

        setLayout(new BorderLayout());
        setBackground(new Color(35, 39, 42)); 

        JLabel titleLabel = new JLabel("MINGWIT", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);

        titleLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(60, 0, 40, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 15, 15));
        buttonPanel.setOpaque(false); 
        buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 250, 100, 250));

        JButton playButton = createStyledButton("Play");
        JButton settingsButton = createStyledButton("Settings");
        JButton exitButton = createStyledButton("Exit Game");

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

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBackground(new Color(114, 137, 218));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false); 
        return button;
    }
}