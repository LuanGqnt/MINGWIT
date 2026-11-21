import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {

    private Player player;
    private Pet pet;

    private JLabel statusLabel;
    private InventoryPanel inventoryPanel;
    private BufferedImage petSprite;

    public GamePanel(Player player, Pet pet) {
        this.player = player;
        this.pet = pet;

        setLayout(new BorderLayout());
        setBackground(new Color(45, 52, 54));

        petSprite = SpriteLoader.loadImage("../assets/images/pet_placeholder.jpg");

        // Top
        JPanel topHud = new JPanel();
        topHud.setBackground(new Color(30, 39, 46));
        topHud.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));

        statusLabel = new JLabel();
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        statusLabel.setForeground(Color.WHITE);
        updateHUD();
        topHud.add(statusLabel);
        add(topHud, BorderLayout.NORTH);

        // Center
        inventoryPanel = new InventoryPanel(player, pet, 3, 5);
        inventoryPanel.setVisible(false);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(inventoryPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom
        JPanel bottomDock = new JPanel();
        bottomDock.setLayout(new GridLayout(1, 5, 10, 0));
        bottomDock.setOpaque(false);
        bottomDock.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 40, 30, 40));

        JButton feedButton = createDockButton("Feed");
        JButton waterButton = createDockButton("Water");
        JButton medicineButton = createDockButton("Medicine");
        JButton fishingButton = createDockButton("Fishing");
        JButton inventoryButton = createDockButton("Inventory");

        feedButton.addActionListener(e -> {
            String msg = pet.feed();
            if (!msg.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, msg, "Max Stat!",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
            updateHUD();
            repaint();
        });

        waterButton.addActionListener(e -> {
            String msg = pet.giveWater();
            if (!msg.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, msg, "Max Stat!",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
            updateHUD();
            repaint();
        });

        medicineButton.addActionListener(e -> {
            String msg = pet.giveMedicine(null); // where the medication will be passed in the future
            if (!msg.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, msg, "Max Stat!",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
            updateHUD();
            repaint();
        });

        fishingButton.addActionListener(e -> {
            SoundManager.playMusic("../assets/music/fishing_theme.wav", -5.0f);
            SceneManager.goTo("FISHING");
        });

        inventoryButton.addActionListener(e -> toggleInventory());

        bottomDock.add(feedButton);
        bottomDock.add(waterButton);
        bottomDock.add(medicineButton);
        bottomDock.add(fishingButton);
        bottomDock.add(inventoryButton);

        add(bottomDock, BorderLayout.SOUTH);

        setupKeyBindings();
    }

    private void setupKeyBindings() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("E"), "toggleInventory");
        actionMap.put("toggleInventory", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleInventory();
            }
        });
    }

    public void updateHUD() {
        statusLabel.setText(
                String.format("Coins: %d  |  Pet: %s  |  Hunger: %d/100  |  Hydration: %d/100  |  Energy: %d/100",
                        player.getCoins(), pet.getName(), pet.getHunger(), pet.getHydration(), pet.getEnergy()));
    }

    private JButton createDockButton(String text) {
        JButton button = new JButton();

        button.setText(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBackground(new Color(99, 110, 114));
        button.setForeground(Color.BLACK);

        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(110, 45));
        return button;
    }

    private void toggleInventory() {
        boolean nextState = !inventoryPanel.isVisible();

        inventoryPanel.refresh();
        inventoryPanel.setVisible(nextState);
        inventoryPanel.revalidate();
        inventoryPanel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2 - 20;

        int petSize = 250;
        int drawX = centerX - (petSize / 2);
        int drawY = centerY - (petSize / 2);

        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.fillOval(centerX - 70, centerY + (petSize / 2) - 15, 140, 30);

        g2d.drawImage(petSprite, drawX, drawY, petSize, petSize, this);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
        int stringWidth = g2d.getFontMetrics().stringWidth(pet.getName());
        g2d.drawString(pet.getName(), centerX - (stringWidth / 2), drawY - 15);
    }
}