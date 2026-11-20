public class ItemStack {
    private Item item;
    private int quantity;

    public ItemStack(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public void addQuantity(int amount) {
        this.quantity += amount;
    }

    public void removeQuantity(int amount) {
        this.quantity = Math.max(0, this.quantity - amount);
    }

    public Item getItem() { return item; }
    public int getQuantity() { return quantity; }
}