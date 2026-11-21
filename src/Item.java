import java.io.Serializable;

public abstract class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String name;
    private int value;
    private String description;

    public Item(String name, int value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    // true if successful, else false
    public abstract boolean use(Pet pet);

    public String getName() { return name; }
    public int getValue() { return value; }
    public String getDescription() { return description; }
}