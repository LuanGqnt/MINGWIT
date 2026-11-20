import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int score;
    private int coins;
    private List<ItemStack> inventory;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.coins = 0;
        this.inventory = new ArrayList<>();
    }

    public void addScore(int points) {
        if (points > 0) score += points;
    }

    public void addCoins(int amount) {
        if (amount > 0) coins += amount;
    }

    public String getName() { return name; }
    public int getScore()   { return score; }
    public int getCoins()   { return coins; }
    public List<ItemStack> getInventory() { return inventory; }

    public ItemStack getInventorySlot(int slot) {
        if (slot < 0 || slot >= inventory.size()) {
            return null;
        }
        
        return inventory.get(slot);
    }

    public void addItem(Item newItem) {
        for (ItemStack slot : inventory) {
            if (slot.getItem().getName() == newItem.getName()) {
                slot.addQuantity(1);
                return;
            }
        }

        
        inventory.add(new ItemStack(newItem, 1));
    }

    public void removeItem(Item newItem) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack slot = inventory.get(i);
            
            if (slot.getItem().getName() == newItem.getName()) {
                slot.removeQuantity(i);

                if (slot.getQuantity() <= 0) {
                    inventory.remove(i);
                }
                return;
            }
        }
    }

    @Override
    public String toString() {
        return "Player: " + name + " | Score: " + score +
               " | Coins: " + coins + " | Fish: " + inventory.size();
    }
}