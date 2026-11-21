import java.io.Serializable;

public class SaveFile implements Serializable {
    
    private Player player;
    private Pet pet;

    public SaveFile(Player player, Pet pet) {
        this.player = player;
        this.pet = pet;
    }

    public boolean isEmpty() {
        return player == null || pet == null;
    }

    public Player getPlayer() {
        return player;
    }

    public Pet getPet() {
        return pet;
    }
}