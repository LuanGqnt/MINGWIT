public abstract class Item {
    private String name;
    private int value;
    private String description;

    public Item(String name, int value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    public abstract void use(Pet pet);

    public String getName() { return name; }
    public int getValue() { return value; }
    public String getDescription() { return description; }
}