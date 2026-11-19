public class Fish extends Item {
    private String rarity;

    public Fish(String name, int value, String description, String rarity) {
        super(name, value, description);
        this.rarity = rarity;
    }

    @Override 
    public void use(Pet pet) {}

    public String getRarity() { return rarity; }
}