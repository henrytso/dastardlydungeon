public abstract class Item
{
    private int price;
    private boolean canPurchase = false;
   

    public Item(boolean canPurchase, int price)
    { 
        this.canPurchase = canPurchase;
        this.price = price;
    }
    
    public boolean canPurchase()
    {
        return canPurchase;
    }
    public int price()
    {
        return price;
    }
    public abstract int getID();
    public abstract String toString();
}