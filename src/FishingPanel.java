import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class FishingPanel extends JPanel {

    private Player player;
    private Random random;

    private enum FishingState {
        WAITING_FOR_CAST,
        WAITING_FOR_BITE,
        REELING
    }
    private FishingState currentState = FishingState.WAITING_FOR_CAST;

    private String statusMessage = "Click anywhere on the water to cast your line!";
    
    private Timer biteTimer;

    private int mouseX, mouseY;

    public FishingPanel(Player player) {
        this.player = player;
        this.random = new Random();

        setLayout(new BorderLayout());
        setBackground(new Color(13, 71, 161)); // Water source

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JButton backButton = new JButton("Back to Pet Room");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setBackground(new Color(96, 125, 139));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            resetFishingState();
            SceneManager.goTo("GAME");
        });

        topPanel.add(backButton, BorderLayout.WEST);

        add(topPanel, BorderLayout.NORTH);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleCanvasClick(e.getX(), e.getY());
            }
        });
    }

    private void handleCanvasClick(int mouseX_, int mouseY_) {
        if (currentState == FishingState.WAITING_FOR_CAST) {
            currentState = FishingState.WAITING_FOR_BITE;
            statusMessage = "Line cast! Waiting for a bite...";
            mouseX = mouseX_;
            mouseY = mouseY_;

            repaint();

            // Milliseconds
            int waitTime = 1000 + random.nextInt(3000);
            
            if (biteTimer != null && biteTimer.isRunning()) {
                biteTimer.stop();
            }

            biteTimer = new Timer(waitTime, event -> {
                triggerBite();
            });
            biteTimer.setRepeats(false);
            biteTimer.start();
        } 
        else if (currentState == FishingState.WAITING_FOR_BITE) {
            if (biteTimer != null) biteTimer.stop();
            statusMessage = "You reeled in too early and scared the fish away! Click water to cast again.";
            currentState = FishingState.WAITING_FOR_CAST;
            repaint();
        }
        else if (currentState == FishingState.REELING) {
            catchFish();
        }
    }

    private void triggerBite() {
        currentState = FishingState.REELING;
        statusMessage = "🐟 BITE! Click the water quickly to reel it in!";
        repaint();
    }

    private void catchFish() {
        Fish catchedFish = rollRandomFish();

        statusMessage = "You successfully caught a " + catchedFish.getName() + "! Click water to cast again.";
        currentState = FishingState.WAITING_FOR_CAST;

        player.addCoins(catchedFish.getValue());
        player.addItem(catchedFish);

        repaint();
    }

    private Fish rollRandomFish() {
        int roll = random.nextInt(100); // 0 to 99

        if (roll < 40) {
            return new Fish("Common Fish", 10, "Common", "Common");
        } else if (roll < 65) {
            return new Fish("Blue Fish", 18, "Common", "Common");
        } else if (roll < 83) {
            return new Fish("Red Fish", 35, "Uncommon", "Uncommon");
        } else if (roll < 93) {
            return new Fish("Golden Fish", 80, "Rare", "Rare");
        } else if (roll < 98) {
            return new Fish("Diamond Fish", 180, "Epic", "Epic");
        } else {    
            return new Fish("Rainbow Fish", 400, "Legendary", "Legendary");
        }
    }

    private void resetFishingState() {
        if (biteTimer != null) biteTimer.stop();
        currentState = FishingState.WAITING_FOR_CAST;
        statusMessage = "Click anywhere on the water to cast your line!";
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Ovean
        GradientPaint oceanGradient = new GradientPaint(
            0, 0, new Color(13, 71, 161), 
            0, height, new Color(1, 22, 53)
        );
        g2d.setPaint(oceanGradient);
        g2d.fillRect(0, 0, width, height);

        // Waves
        g2d.setColor(new Color(255, 255, 255, 20));
        g2d.setStroke(new BasicStroke(2));
        for (int i = 100; i < height; i += 80) {
            g2d.drawArc(50, i, width - 100, 40, 0, 180);
        }

        // Status
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(statusMessage);
        
        // Backdrop for the status
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.fillRoundRect((width - textWidth - 40) / 2, height - 100, textWidth + 40, 45, 20, 20);

        g2d.setColor(Color.WHITE);
        g2d.drawString(statusMessage, (width - textWidth) / 2, height - 72);

        if (currentState == FishingState.WAITING_FOR_BITE || currentState == FishingState.REELING) {
            int centerX = mouseX;
            int centerY = mouseY;

            // Ripple circles
            g2d.setColor(new Color(255, 255, 255, 70));
            g2d.drawOval(centerX - 30, centerY - 15, 60, 30);
            g2d.drawOval(centerX - 50, centerY - 25, 100, 50);

            // If biting, make it flash red/orange
            if (currentState == FishingState.REELING) {
                g2d.setColor(new Color(255, 82, 82));
                g2d.fillOval(centerX - 12, centerY - 22, 24, 24);
            } else {
                g2d.setColor(new Color(255, 235, 59)); // Yellow bobber
                g2d.fillOval(centerX - 10, centerY - 20, 20, 20);
            }
        }
    }
}