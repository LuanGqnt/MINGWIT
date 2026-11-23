import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PetCreationPanel extends JPanel {

    public interface OnPetCreated {
        void accept(int slotIndex, String petName);
    }

    private int slotIndex = 0;
    private JLabel titleLabel;
    private JTextField nameField;

    public PetCreationPanel(OnPetCreated onPetCreated, Runnable onCancel) {
        setLayout(new GridBagLayout());
        setBackground(new Color(45, 52, 54));
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        titleLabel = new JLabel("Create Pet — Slot 1", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel nameLabel = new JLabel("Pet Name:");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        add(nameLabel, gbc);

        gbc.gridx = 1;
        nameField = new JTextField(16);
        nameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        cancelButton.setFocusPainted(false);
        cancelButton.addActionListener(e -> onCancel.run());
        add(cancelButton, gbc);

        gbc.gridx = 1;
        JButton createButton = new JButton("Create");
        createButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        createButton.setFocusPainted(false);
        createButton.addActionListener(e -> {
            String petName = nameField.getText().trim();
            if (petName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a name for your pet.");
                return;
            }
            onPetCreated.accept(slotIndex, petName);
        });
        add(createButton, gbc);
    }

    public void setSlotIndex(int slotIndex) {
        this.slotIndex = slotIndex;
        this.titleLabel.setText("Create Pet — Slot " + (slotIndex + 1));
        this.nameField.setText("");
    }
}