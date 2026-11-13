import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Window extends JFrame implements MainMenuPanel.MenuListener {

    private CardLayout cardLayout;
    private JPanel mainContainer;

    private Player player;
    private Pet pet;

    private GamePanel gamePanel;

    public Window() {
        setTitle("MINGWIT");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        player = new Player("Player 1");
        // Hard coded pa to, will update soon
        pet = new Pet("Coco");

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        SceneManager.init(cardLayout, mainContainer);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);
        FishingPanel fishingPanel = new FishingPanel(player);

        gamePanel = new GamePanel(player, pet);

        mainContainer.add(mainMenuPanel, "MENU");
        mainContainer.add(gamePanel, "GAME");
        mainContainer.add(fishingPanel, "FISHING");

        add(mainContainer);
        cardLayout.show(mainContainer, "MENU");
    }

    @Override
    public void onPlayClicked() {
        System.out.println("Play Game clicked!");
        gamePanel.updateHUD();
        SceneManager.goTo("GAME");
    }

    @Override
    public void onSettingsClicked() {
        System.out.println("Vet Clinic clicked!");
        JOptionPane.showMessageDialog(this, "Vet & Medication screen coming soon.");
    }
}