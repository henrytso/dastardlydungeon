import java.util.Scanner;
public class Shop extends Block
{
    private final String AVATAR = "$";
    int tier;
    String dir;

    public Shop(Point loc, int tier)
    {
        super(loc);
        this.tier = tier;
    }

    public String toString()
    {
        if(super.discovered())
        return AVATAR;
        else return super.getUndiscovered();
    }

    public boolean collides()
    {
        return true;
    }

    public boolean isShop()
    {
        return true;
    }

    public Inventory activate(PlayerInfo player)
    {
        Inventory newInv = player.getInventory();
        int inp = -1;
        do
        {
            Runner2.nextFrame();
            if (tier == 1)
                System.out.println("SHOPKEEPER: Greetings, hero. Make sure you're prepared before going to the next floor.");
            if (tier == 2)
                System.out.println("SHOPKEEPER: You've come far, but you have long way ahead. ");
            if (tier == 3)
                System.out.println("SHOPKEEPER: A very strong monster lives in the floor below. Be careful.");
            Combat.pause();
            System.out.println("SHOPKEEPER: You have [" + newInv.size() + "/9] items and " +  player.getCurrency() + " Soulstones");
            System.out.println("Would you like to buy a" + (new Potion(tier)).toString() + "?");
            System.out.println("0. Exit");
            System.out.println("1. Yes ($" + ((tier*50)+50) + ")");
            Scanner input = new Scanner(System.in);
            Runner2.printInterface();
            if (input.hasNextInt())
            {
                inp = input.nextInt();
                if (inp == 1)
                {
                    if (player.getCurrency() >= ((tier*50)+50))
                    {
                        player.setCurrency(player.getCurrency() - ((tier*50)+50));
                      
                        newInv.add(new Potion(tier));
                        System.out.println("You have bought a " + (new Potion(tier)).toString() + "!");
                        Combat.pause();
                    }
                    else
                    {
                        System.out.println("You don't have enough Soulstones!");
                        Combat.pause();
                    }
                }
                else
                if (inp == 0)
                {
                    System.out.println("Goodbye traveler, please come again!");
                }
                else
                {
                    System.out.println("INVALID INPUT");
                    Combat.pause();
                }
            }
            else
            {
                System.out.println("INVALID INPUT");
                Combat.pause();
            }
        } while (inp != 0);
        return newInv;
    }

    public static void main(String[] args)
    {
        PlayerInfo p = new PlayerInfo();
        Shop s = new Shop(null, 1);
        p.setCurrency(300);
        p.setInventory(s.activate(p));
        p.printInventory();
    }

}