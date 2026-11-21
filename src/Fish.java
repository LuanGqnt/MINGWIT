public class Fish extends Item {
    private String rarity;
    private int feedAmount;

    public Fish(String name, int value, String description, int feedAmount, String rarity) {
        super(name, value, description);
        this.rarity = rarity;
        this.feedAmount = feedAmount;
    }

    @Override 
    public boolean use(Pet pet) {
        return pet.feed(feedAmount);
    }

    public String getRarity() { return rarity; }
}