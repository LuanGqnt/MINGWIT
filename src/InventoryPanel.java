import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class InventoryPanel extends JPanel {

    private Player player;
    private Pet pet;
    private int rows;
    private int cols;
    private JPanel gridPanel;
    private JLabel statusLabel;

    public InventoryPanel(Player player, Pet pet, int rows, int cols) {
        this.player = player;
        this.pet = pet;
        this.rows = rows;
        this.cols = cols;

        setLayout(new BorderLayout(8, 8));
        setPreferredSize(new Dimension(cols * 84 + 32, rows * 84 + 80));
        setBackground(new Color(30, 33, 36, 240));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(30, 20, 36, 240), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("Inventory");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        JButton closeButton = new JButton("X");
        closeButton.setFocusPainted(false);
        closeButton.setMargin(new Insets(2, 8, 2, 8));
        closeButton.addActionListener(e -> setVisible(false));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(closeButton, BorderLayout.EAST);

        // Grid
        gridPanel = new JPanel(new GridLayout(rows, cols, 8, 8));
        gridPanel.setOpaque(false);

        // Footer
        statusLabel = new JLabel("Click a slot to inspect", SwingConstants.LEFT);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(200, 205, 210));

        add(headerPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        refresh();
    }

    public void setStatusMessage(String msg) {
        statusLabel.setText(msg);
    }

    public void refresh() {
        gridPanel.removeAll();
        int capacity = rows * cols;

        for (int i = 0; i < capacity; i++) {
            final int slotIndex = i;
            ItemStack stack = player.getInventorySlot(i); // adapt to your Player getter

            JButton slotButton = new JButton();
            slotButton.setFocusPainted(false);
            slotButton.setBackground(new Color(44, 47, 51));
            slotButton.setForeground(Color.WHITE);
            slotButton.setFont(new Font("SansSerif", Font.BOLD, 11));
            slotButton.setBorder(BorderFactory.createLineBorder(new Color(79, 84, 92), 1));

            if (stack != null && stack.getQuantity() > 0) {
                slotButton.setText("<html><center>" + stack.getItem().getName()
                    + "<br><font color='#AAAAAA'>x" + stack.getQuantity() + "</font></center></html>");
                slotButton.addActionListener(e ->
                    statusLabel.setText(stack.getItem().getName() + " (x" + stack.getQuantity() + ")")
                );
            } else {
                slotButton.setText("");
                slotButton.addActionListener(e ->
                    statusLabel.setText("Slot " + (slotIndex + 1) + " is empty")
                );
            }

            gridPanel.add(slotButton);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }
}