import javax.swing.JPanel;
import java.awt.CardLayout;

public class SceneManager {
    private static CardLayout cardLayout;
    private static JPanel container;

    public static void init(CardLayout cl, JPanel cont) {
        cardLayout = cl;
        container = cont;
    }

    public static void goTo(String screenName) {
        if (cardLayout != null && container != null) {
            cardLayout.show(container, screenName);
        } else {
            System.out.println("Error: SceneRouter not initialized!");
        }
    }
}