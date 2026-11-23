import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {

    private Player player;
    private Pet pet;

    private InventoryPanel inventoryPanel;
    private BufferedImage petSprite;
    private BufferedImage bgSprite;

    private boolean DEBUG_HITBOX = false;

    private BufferedImage doorSprite;
    private MouseHitbox doorHitbox;
    
    private BufferedImage inventoryBagOpen;
    private BufferedImage inventoryBagClose;
    private MouseHitbox inventoryBagHitbox;

    public GamePanel(Player player, Pet pet) {
        this.player = player;
        this.pet = pet;

        setLayout(new BorderLayout());
        setBackground(new Color(45, 52, 54));

        petSprite = SpriteLoader.loadImage("../assets/images/pet_placeholder.jpg");
        bgSprite = SpriteLoader.loadImage("../assets/images/game_panel_bg.png");
        doorSprite = SpriteLoader.loadImage("../assets/images/game_panel_door.png");
        inventoryBagOpen = SpriteLoader.loadImage("../assets/images/inventory_bag_open.png");
        inventoryBagClose = SpriteLoader.loadImage("../assets/images/inventory_bag_close.png");    
    
        doorHitbox = new MouseHitbox(510, 60, 200, 260, () -> {
            SoundManager.playMusic("../assets/music/fishing_theme.wav", -10.0f);
            SceneManager.goTo("FISHING");
        });

        inventoryBagHitbox = new MouseHitbox(20, 450, 90, 100, () -> {
            toggleInventory();
        });
        MouseAdapter hitboxMouse = doorHitbox.createMouseAdapter(
            this,
            inventoryBagHitbox
        );
        addMouseListener(hitboxMouse);
        addMouseMotionListener(hitboxMouse);

        // Top
        // JPanel topHud = new JPanel();
        // topHud.setBackground(new Color(30, 39, 46));
        // topHud.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // add(topHud, BorderLayout.NORTH);

        // Center
        inventoryPanel = new InventoryPanel(player, pet, 3, 5, () -> {
            repaint();
        });
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
        int centerY = getHeight() / 2 + 100;

        int petSize = 96;
        int drawX = centerX - (petSize / 2);
        int drawY = centerY - (petSize / 2);

        g2d.drawImage(bgSprite, 0, 0, getWidth(), getHeight(), this);

        if(!doorHitbox.isHovering())
            g2d.drawImage(doorSprite, 0, 0, getWidth(), getHeight(), this);

        if(inventoryBagHitbox.isHovering())
            g2d.drawImage(inventoryBagOpen, 0, 0, getWidth(), getHeight(), this);
        else
            g2d.drawImage(inventoryBagClose, 0, 0, getWidth(), getHeight(), this);

        g2d.setColor(new Color(0, 0, 0, 40));
        g2d.fillOval(centerX - 70, centerY + (petSize / 2) - 15, 140, 30);

        g2d.drawImage(petSprite, drawX, drawY, petSize, petSize, this);

        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("SansSerif", Font.BOLD, 18));
        int stringWidth = g2d.getFontMetrics().stringWidth(pet.getName());
        g2d.drawString(pet.getName(), centerX - (stringWidth / 2), drawY - 15);
 
        if (DEBUG_HITBOX) {
            doorHitbox.drawDebug(g2d);
            inventoryBagHitbox.drawDebug(g2d);
        }
    }
}