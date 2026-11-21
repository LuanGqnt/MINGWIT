import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.function.IntConsumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SavePanel extends JPanel {

    private SaveFile[] saves;
    private JButton[] slotButtons = new JButton[3];

    public SavePanel(SaveFile[] saves, IntConsumer onSlotSelected, Runnable onBack) {
        this.saves = saves;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(45, 52, 54));
        setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JLabel titleLabel = new JLabel("Select Save Slot", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, BorderLayout.NORTH);

        JPanel slotsGrid = new JPanel(new GridLayout(3, 1, 15, 15));
        slotsGrid.setOpaque(false);

        for (int i = 0; i < 3; i++) {
            final int slotIndex = i;
            JButton button = new JButton();
            button.setFont(new Font("SansSerif", Font.BOLD, 15));
            button.setBackground(new Color(99, 110, 114));
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
            button.addActionListener(e -> onSlotSelected.accept(slotIndex));

            slotButtons[i] = button;
            slotsGrid.add(button);
        }

        add(slotsGrid, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> onBack.run());
        add(backButton, BorderLayout.SOUTH);

        refresh();
    }

    public void refresh() {
        for (int i = 0; i < 3; i++) {
            SaveFile save = saves[i];
            if (save != null && !save.isEmpty()) {
                Player p = save.getPlayer();
                Pet pet = save.getPet();
                slotButtons[i].setText(String.format(
                    "<html><center>Slot %d — %s<br><font color='#E0E0E0'>Coins: %d  |  Hunger: %d/100  |  Hydration: %d/100</font></center></html>",
                    i + 1, pet.getName(), p.getCoins(), pet.getHunger(), pet.getHydration()
                ));
            } else {
                slotButtons[i].setText("Slot " + (i + 1) + " — Empty");
            }
        }
    }
}