import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class InventoryPanel extends JPanel {

    private Player player;
    private Pet pet;
    private Runnable onInventoryChanged;

    private int rows;
    private int cols;
    private JPanel gridPanel;
    private JLabel statusLabel;

    private JPanel actionButtonPanel;
    private ItemStack selectedStack;

    private BufferedImage bgSprite;
    private BufferedImage inventorySlotsSprite;
    private BufferedImage actionButtonsSprite;

    public InventoryPanel(Player player, Pet pet, int rows, int cols, Runnable onInventoryChanged) {
        this.player = player;
        this.pet = pet;
        this.rows = rows;
        this.cols = cols;
        this.onInventoryChanged = onInventoryChanged;

        // setLayout(new BorderLayout(8, 8));
        setLayout(new BorderLayout());
        // setPreferredSize(new Dimension(cols * 84 + 32, rows * 84 + 80));
        setPreferredSize(new Dimension(520, 420));

        // setBackground(new Color(30, 33, 36, 240));
        // setBorder(BorderFactory.createCompoundBorder(
        //         BorderFactory.createLineBorder(new Color(30, 20, 36, 240), 2),
        //         BorderFactory.createEmptyBorder(10, 10, 10, 10)));


        bgSprite = SpriteLoader.loadImage("../assets/images/inventory_background.png");
        inventorySlotsSprite = SpriteLoader.loadImage("../assets/images/inventory_slots.png");
        actionButtonsSprite = SpriteLoader.loadImage("../assets/images/inventory_buttons.png");

        this.setOpaque(false);

        // Header
        // JPanel headerPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);

        add(headerPanel, BorderLayout.CENTER);

        // JLabel titleLabel = new JLabel("Inventory");
        // titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        // titleLabel.setForeground(Color.WHITE);

        // JButton closeButton = new JButton("X");
        // closeButton.setFocusPainted(false);
        // closeButton.setMargin(new Insets(2, 8, 2, 8));
        // closeButton.addActionListener(e -> setVisible(false));

        // headerPanel.add(titleLabel, BorderLayout.WEST);
        // headerPanel.add(closeButton, BorderLayout.EAST);

        // // Grid
        // gridPanel = new JPanel(new GridLayout(rows, cols, 8, 8));
        // gridPanel.setOpaque(false);

        // // Footer
        // JPanel bottomPanel = new JPanel();
        // bottomPanel.setOpaque(false);

        // actionButtonPanel = new JPanel();
        // actionButtonPanel.setOpaque(false);
        // actionButtonPanel.setVisible(false);

        // JButton sellButton = new JButton("Sell");
        // JButton useButton = new JButton("Use");

        // sellButton.addActionListener(e -> sellClicked());
        // useButton.addActionListener(e -> useClicked());

        // actionButtonPanel.add(sellButton);
        // actionButtonPanel.add(useButton);

        // statusLabel = new JLabel("Click a slot to inspect", SwingConstants.LEFT);
        // statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        // statusLabel.setForeground(new Color(200, 205, 210));

        // bottomPanel.add(statusLabel);
        // bottomPanel.add(actionButtonPanel);

        // add(headerPanel, BorderLayout.NORTH);
        // add(gridPanel, BorderLayout.CENTER);
        // add(bottomPanel, BorderLayout.SOUTH);

        // refresh();
        repaint();
    }

    private void sellClicked() {
        if (selectedStack != null) {
            player.addCoins(selectedStack.getItem().getValue());
            SoundManager.playSfx("../assets/sfx/kaching.wav", 0.0f);
            statusLabel.setText(selectedStack.getItem().getName() + " sold!");
            player.removeItemStackQuantity(selectedStack);

            // refresh();
            if (onInventoryChanged != null) {
                onInventoryChanged.run();
            }
        }
    }

    private void useClicked() {
        if (selectedStack == null)
            return;

        boolean success = selectedStack.use(pet);

        if (!success)
            return;

        statusLabel.setText(selectedStack.getItem().getName() + " used!");
        player.removeItemStackQuantity(selectedStack);

        // refresh();

        if (onInventoryChanged != null) {
            onInventoryChanged.run();
        }

    }

    public void setStatusMessage(String msg) {
        statusLabel.setText(msg);
    }

    // public void refresh() {
    //     gridPanel.removeAll();
    //     int capacity = rows * cols;

    //     for (int i = 0; i < capacity; i++) {
    //         final int slotIndex = i;
    //         ItemStack stack = player.getInventorySlot(i);

    //         JButton slotButton = new JButton();
    //         slotButton.setFocusPainted(false);
    //         slotButton.setBackground(new Color(44, 47, 51));
    //         slotButton.setForeground(Color.WHITE);
    //         slotButton.setFont(new Font("SansSerif", Font.BOLD, 11));
    //         slotButton.setBorder(BorderFactory.createLineBorder(new Color(79, 84, 92), 1));

    //         if (stack != null && stack.getQuantity() > 0) {
    //             slotButton.setText("<html><center>" + stack.getItem().getName()
    //                     + "<br><font color='#AAAAAA'>x" + stack.getQuantity() + "</font></center></html>");

    //             // Button clicked
    //             slotButton.addActionListener(e -> {
    //                 selectedStack = stack;
    //                 statusLabel.setText(stack.getItem().getName() + " (x" + stack.getQuantity() + ")");
    //                 actionButtonPanel.setVisible(true);
    //             });
    //         } else {

    //             slotButton.setText("");
    //             slotButton.addActionListener(e -> {
    //                 selectedStack = null;
    //                 statusLabel.setText("Slot " + (slotIndex + 1) + " is empty");
    //                 actionButtonPanel.setVisible(false);
    //             });

    //         }

    //         gridPanel.add(slotButton);
    //     }

    //     gridPanel.revalidate();
    //     gridPanel.repaint();
    // }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int inventoryWidth = getWidth();
        int inventoryHeight = getHeight();

        if (bgSprite != null) {
            g.drawImage(bgSprite, 0, 0, inventoryWidth, inventoryHeight, this);
            g.drawImage(inventorySlotsSprite, 0, 0, inventoryWidth, inventoryHeight, this);
            g.drawImage(actionButtonsSprite, 0, 0, inventoryWidth, inventoryHeight, this);
        }
    }
}
