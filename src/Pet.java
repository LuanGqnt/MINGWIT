public class Pet {
    private String name;
    private int hunger; // 0 = starving, 100 = full
    private int hydration; // 0 = dehydrated, 100 = fully hydrated
    private int energy; // 0 = exhausted, 100 = energetic

    public Pet(String name) {
        this.name = name;
        this.hunger = 50;
        this.hydration = 50;
        this.energy = 50;
    }

    public String feed() {
        int oldHunger = hunger;
        hunger = clamp(hunger + 20);
        energy = clamp(energy + 10);
        
        if (hunger == 100 && oldHunger < 100) {
            return name + " is now completely full!";
        }
        return "";
    }

    public String giveWater() {
        int oldHydration = hydration;
        hydration = clamp(hydration + 25);
        
        if (hydration == 100 && oldHydration < 100) {
            return name + " is fully hydrated!";
        }
        return "";
    }

    public String giveMedicine(Medication medication) {
        int oldEnergy = energy;
        energy = clamp(energy + 30);
        
        if (energy == 100 && oldEnergy < 100) {
            if (medication != null) {
                return name + " took " + medication.getName() + " and is now fully energized!";
            }
            return name + " took medicine and is now fully energized!";
        }
        
        if (medication != null) {
            return name + " took " + medication.getName() + ".";
        }
        return name + " took some medicine.";
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(100, value));
    }

    public String getName() {
        return name;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHydration() {
        return hydration;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return name + " | Hunger: " + hunger +
                " | Hydration: " + hydration + " | Energy: " + energy;
    }
}