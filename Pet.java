public class Pet {
    private String name;
    private int hunger;     // 0 = full, 100 = starving
    private int hydration;  // 0 = dehydrated, 100 = fully hydrated
    private int energy;     // 0 = exhausted, 100 = energetic
    private int health;     // 0 = critical, 100 = perfect

    public Pet(String name) {
        this.name = name;
        this.hunger = 50;
        this.hydration = 50;
        this.energy = 50;
        this.health = 100;
    }

    public void feed() {
        hunger = clamp(hunger - 20);
        energy = clamp(energy + 10);
        updateStatus();
    }

    public void giveWater() {
        hydration = clamp(hydration + 25);
        updateStatus();
    }

    public void giveMedicine(Medication medication) {
        if (medication != null) {
            health = clamp(health + 20);
            System.out.println(name + " took " + medication.getName() + ".");
        }
        updateStatus();
    }

    //Recomputes health based on hunger, hydration and energy
    public void updateStatus() {
        if (hunger > 80 || hydration < 20 || energy < 20) {
            health = clamp(health - 10);
        }
        if (health <= 0) {
            System.out.println(name + " needs urgent care!");
        }
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(100, value));
    }

    public String getName() { return name; }
    public int getHunger()    { return hunger; }
    public int getHydration() { return hydration; }
    public int getEnergy()    { return energy; }
    public int getHealth()    { return health; }

    @Override
    public String toString() {
        return name + " | Hunger: " + hunger +
               " | Hydration: " + hydration + " | Energy: " + energy +
               " | Health: " + health;
    }
}