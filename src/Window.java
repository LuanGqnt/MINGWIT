import java.awt.CardLayout;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Window extends JFrame implements MainMenuPanel.MenuListener {

    private CardLayout cardLayout;
    private JPanel mainContainer;

    private SaveFile[] saves;
    private SavePanel savePanel;
    private Timer autosaveTimer;
    private PetCreationPanel petCreationPanel;

    private GamePanel gamePanel;
    private FishingPanel fishingPanel;

    public Window() {
        setTitle("MINGWIT");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        SceneManager.init(cardLayout, mainContainer);

        MainMenuPanel mainMenuPanel = new MainMenuPanel(this);

        saves = SaveStorage.loadAll();

        savePanel = new SavePanel(
            saves,
            this::onSlotSelected,
            () -> SceneManager.goTo("MENU")
        );

        petCreationPanel = new PetCreationPanel(
            this::onPetCreated,
            () -> {
                savePanel.refresh();
                SceneManager.goTo("SAVES");
            }
        );

        mainContainer.add(mainMenuPanel, "MENU");
        mainContainer.add(savePanel, "SAVES");
        mainContainer.add(petCreationPanel, "PET_CREATION");

        add(mainContainer);
        SceneManager.goTo("MENU");

        startAutosaveTimer();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                SaveStorage.saveAll(saves);
            }
        });
    }

    @Override
    public void onPlayClicked() {
        savePanel.refresh();
        SceneManager.goTo("SAVES");
    }

    @Override
    public void onSettingsClicked() {
        System.out.println("Vet Clinic clicked!");
        JOptionPane.showMessageDialog(this, "Vet & Medication screen coming soon.");
    }

    private void onSlotSelected(int slotIndex) {
        SaveFile selected = saves[slotIndex];
        if (selected == null || selected.isEmpty()) {
            petCreationPanel.setSlotIndex(slotIndex);
            SceneManager.goTo("PET_CREATION");
        } else {
            loadGame(selected);
        }
    }

    private void onPetCreated(int slotIndex, String petName) {
        Player player = new Player("Player " + (slotIndex + 1));
        Pet pet = new Pet(petName);

        saves[slotIndex] = new SaveFile(player, pet);
        SaveStorage.saveAll(saves);
        loadGame(saves[slotIndex]);
    }

    private void loadGame(SaveFile save) {
        Player player = save.getPlayer();
        Pet pet = save.getPet();

        if (gamePanel != null) {
            mainContainer.remove(gamePanel);
        }
        if (fishingPanel != null) {
            mainContainer.remove(fishingPanel);
        }

        gamePanel = new GamePanel(player, pet);
        fishingPanel = new FishingPanel(player);

        mainContainer.add(gamePanel, "GAME");
        mainContainer.add(fishingPanel, "FISHING");

        SoundManager.playMusic("../assets/music/mingwit_main_theme.wav", -10.0f);
        SceneManager.goTo("GAME");
    }

    private void startAutosaveTimer() {
        if (autosaveTimer != null && autosaveTimer.isRunning()) {
            return;
        }

        autosaveTimer = new Timer(10_000, e -> SaveStorage.saveAll(saves));
        autosaveTimer.start();
    }

}