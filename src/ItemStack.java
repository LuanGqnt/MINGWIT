public class ItemStack {
    private Item item;
    private int count;

    public ItemStack(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public void addCount(int amount) {
        this.count += amount;
    }

    public void removeCount(int amount) {
        this.count = Math.max(0, this.count - amount);
    }

    public Item getItem() { return item; }
    public int getCount() { return count; }
}