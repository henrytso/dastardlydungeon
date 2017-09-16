import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Write a description of class Inventory here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Inventory
{
    // instance variables - replace the example below with your own
    private ArrayList<Item> inventory;

    /**
     * Constructor for objects of class Inventory
     */
    public Inventory()
    {
        inventory  = new ArrayList<Item>();
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  x   an item you want to add to your inventory
     */
    public void add(Item x)
    {
        if (inventory.size() < 9)
        {
            inventory.add(x);
            inventorySort();
        }
        else //aka if it is 10 (aka hacking in which case, all bets are off, I hope the program crashes. scum.)
        {
            //fake a gui here
            int inventoryInput;
            do 
            {
                Runner2.nextFrame();
                System.out.println("Which item do you want to remove from your inventory for: " + x.toString()); //plsytesters
                System.out.println("0. Throw away the " + x.toString() + "you found");
                for (int i = 0; i < inventory.size(); i++)
                {
                    System.out.println((i + 1) + ". " + inventory.get(i).toString());
                }
                Runner2.printInterface();
                Scanner inventoryIn = new Scanner(System.in);
                inventoryInput = inventoryIn.nextInt();
            }
            while(inventoryInput > 9 || inventoryInput < 0);
            if (inventoryInput != 0)

            {
                inventory.remove(inventoryInput - 1);
                add(x); //recursion in case 2 items were added at once or something idk just here for safety
            }
        }
    }

    /**
     * Insertion sort, most useful here because it is nearly sorted, runs through 9 times to ensure that the list is sorted (because reasons)
     */
    public void inventorySort()
    {
        for (int a = 0; a < 9; a++)
            for (int i = 1; i < inventory.size(); i++)
            {
                for (int k = i - 1; k >= 0 && compare(inventory.get(i),inventory.get(k)); k--)
                {
                    Collections.swap(inventory, i, k);
                }
            }
    }

    /**
     * Gives a random item from the inventory, used for monster drops
     * @return a random item from the inventory
     */
    public Item randomItem()
    {
        return inventory.get((int)(Math.random() * inventory.size()));
    }

    /**
     * Removes an item from x
     */
    public void removeItem(Item x)
    {
        inventory.remove(x);
    }

    /**
     * Compares the type and the value that type does
     * @param x an Item being comapred
     * @param y an Item being comapred
     * @return whether or not Item y has a greater value than Item x if they are the same type
     */
    private boolean compare(Item x, Item y)
    {
        return x.getID() < y.getID();
    }

    /**
     * Gets in inventory of only potions
     * @return an inventory of potions
     */
    public Inventory getPotions()
    {
        Inventory retInv = new Inventory();
        for (int i = 0; i < inventory.size(); i++)
        {
            if (get(i).getID() < 2000)
                retInv.add(get(i));
        }
        return retInv;
    }
    
    public Inventory getEquips()
    {
        Inventory retInv = new Inventory();
        for (int i = 0; i < inventory.size(); i++)
        {
            if (!(get(i).getID() < 2000))
                retInv.add(get(i));
        }
        return retInv;
    }

    /**
     * I hate you too java, makes .get work so that inventory can be outside of a static context or something dumb like that, used in the tester (main method below)
     * @param x the item you want to get from the inventory 
     */
    public Item get(int x)
    {
        if (x < inventory.size())
            return inventory.get(x);
        return null;
    }

    public int size()
    {
        return inventory.size(); 
    }

    public void printInventory()
    {
        if (size() > 0)
        {  
            System.out.println("Inventory [" + size() + "/9]");
            for (int i = 0; i < size(); i++)
            {
                System.out.println((i + 1) + ". " + get(i).toString());
            }
            for (int i = 0; i <  (9 - size()); i++)
            {
                System.out.println((i + size() + 1) + ". ------------");
            }
        }
        else
        {
            System.out.println("Inventory [0/9]");
            System.out.println("1. ------------");
            System.out.println("2. ------------");
            System.out.println("3. ------------");
            System.out.println("4. ------------");
            System.out.println("5. ------------");
            System.out.println("6. ------------");
            System.out.println("7. ------------");
            System.out.println("8. ------------");
            System.out.println("9. ------------");
        }
    }

    public void printPotions()
    {
        for (int i = 0; i < size(); i++)
        {
            System.out.println((i + 1) + ". " + get(i).toString());
        }
    }

    public Inventory chestSwap(Inventory chest, int x)
    {
        Inventory y = chest;
        if ( x > -1 && x < y.size())
            inventory.add(y.get(x));
        y.removeItem(y.get(x));
        return y;
    }

    public static void main(String[] args)
    {
        Inventory inv = new Inventory();
        for (int i = 0; i < 10; i++)
        {
            Weapon wep = new Weapon(3);
            inv.add(wep);
            for (int k = 0; k < inv.size(); k++)
            {
                System.out.println(inv.get(k).toString() + "\t" + ((Weapon)inv.get(k)).getID() + "\t" + ((Weapon)inv.get(k)).getDamage());
            }
        }
    }
}
