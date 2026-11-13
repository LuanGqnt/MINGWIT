import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int score;
    private int coins;
    private List<Fish> inventory;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.coins = 0;
        this.inventory = new ArrayList<>();
    }

    public void addScore(int points) {
        if (points > 0) score += points;
    }

    public void earnReward(int amount) {
        if (amount > 0) coins += amount;
    }

    public void addFish(Fish fish) {
        if (fish != null) inventory.add(fish);
    }

    public boolean spendCoins(int amount) {
        if (amount <= coins) {
            coins -= amount;
            return true;
        }
        return false;
    }

    public String getName() { return name; }
    public int getScore()   { return score; }
    public int getCoins()   { return coins; }
    public List<Fish> getInventory() { return inventory; }

    @Override
    public String toString() {
        return "Player: " + name + " | Score: " + score +
               " | Coins: " + coins + " | Fish: " + inventory.size();
    }
}