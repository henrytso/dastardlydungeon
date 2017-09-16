import java.util.ArrayList;
import java.util.Scanner;


public class Chest extends Block 
{
    private Inventory inventory;
    private final String AVATAR = "C";
    private int tier;

    public Chest(Point loc, int tier)
    {
        super(loc);
        inventory = new Inventory();
        this.tier = tier;
        int inventorySize =  1 + (int) (Math.random() * 3);
        for (int i = 0; i < inventorySize; i++)
        {
            switch ((int) (Math.random() * 4))
            {
                case 0: 
                Potion p = new Potion(tier);
                inventory.add(p);
                break;
                case 1: 
                Armor a = new Armor(tier);
                inventory.add(a);
                break;
                case 2: 
                Weapon w = new Weapon(tier);
                inventory.add(w);
                break;
                case 3: 
                Shield s = new Shield(tier);
                inventory.add(s);
                break;
            }
        }
    }

    public boolean collides()
    {
        return true;
    }

    public String toString()
    {
        if(super.discovered())
        return AVATAR;
        else return super.getUndiscovered();
    }

    public boolean isChest()
    {
        return true;
    }

    public Inventory activate(PlayerInfo player)
    {
        if(player.getLevel() > tier * 3 - 4)
        {
            Runner2.nextFrame();
            Inventory newInv = player.getInventory();
            int chestInput;
            do
            {
                System.out.println("Inventory [" + newInv.size() + "/9]                   Chest["+ inventory.size() + "/9]");
                System.out.println("0. Exit                           0. Exit");
                for (int i = 0; i < 9; i++)
                {
                    if (newInv.get(i) != null)
                    {
                        System.out.print((i + 1)+ ". " + newInv.get(i).toString());
                        for (int k = 0; k < 31 - newInv.get(i).toString().length(); k++) //25 is amount of char per line
                            System.out.print(" ");
                    }
                    else
                    {
                        System.out.print((i + 1) + ". ------------");
                        for (int k = 0; k < 19; k++) //25 is amount of char per line
                            System.out.print(" ");
                    }
                    if (inventory.get(i) != null)
                        System.out.print((i + 1) + ". " + inventory.get(i).toString());
                    else
                        System.out.print((i + 1) + ". ------------");
                    System.out.print("\n");
                }
                System.out.println("Which item would you like to take from the chest?"); 
                Scanner input = new Scanner(System.in);
                
                if (input.hasNextInt())
                    chestInput = input.nextInt();
                else
                    chestInput = 420; //aka invalid input
                if (chestInput <= inventory.size() && chestInput > 0)
                {
                    Item x = inventory.get(chestInput - 1);
                    newInv.add(x);
                    inventory.removeItem(x);
                }
                else
                if (chestInput != 0)
                
                    System.out.println("INVALID INPUT");
                    Runner2.nextFrame();
            } while (chestInput != 0);  
            return newInv;
        }
        else 
        {
            Runner2.nextFrame();
            System.out.println("Incompetent fool! You must be at least level " + (tier * 3 - 3) + " to open this chest!");
            Combat.pause();
        }
        return player.getInventory();
    }

    public Inventory activate(PlayerInfo player, MonsterInfo monster)
    {
        this.inventory = monster.getInventory();
        Inventory newInv = player.getInventory();
        int chestInput;
        do
        {
            Runner2.nextFrame();
            System.out.println("Inventory [" + newInv.size() + "/9]                   Monster["+ inventory.size() + "/9]");
            System.out.println("0. Exit                           0. Exit");
            for (int i = 0; i < 9; i++)
            {
                if (newInv.get(i) != null)
                {
                    System.out.print((i + 1)+ ". " + newInv.get(i).toString());
                    for (int k = 0; k < 31 - newInv.get(i).toString().length(); k++) //25 is amount of char per line
                        System.out.print(" ");
                }
                else
                {
                    System.out.print((i + 1) + ". ------------");
                    for (int k = 0; k < 19; k++) //25 is amount of char per line
                        System.out.print(" ");
                }
                if (inventory.get(i) != null)
                    System.out.print((i + 1) + ". " + inventory.get(i).toString());
                else
                    System.out.print((i + 1) + ". ------------");
                System.out.print("\n");
            }
            System.out.println("Which item would you like to extract from the limp corpse?"); 
            Runner2.printInterface();
            Scanner input = new Scanner(System.in);

            if (input.hasNextInt())
                chestInput = input.nextInt();
            else
                chestInput = 420; //aka invalid input
            if (chestInput <= inventory.size() && chestInput > 0)
            {
                Item x = inventory.get(chestInput - 1);
                newInv.add(x);
                inventory.removeItem(x);
            }
            else
            if (chestInput != 0)
                System.out.println("INVALID INPUT");
            Runner2.nextFrame();
        } while (chestInput != 0);
        Runner2.nextFrame();
        return newInv;
    }
}