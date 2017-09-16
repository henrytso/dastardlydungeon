/**
 * wahpoo
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Potion extends Item
{
    private int restore;
    private int tier;
    private String name;

    /**
     * Constructor for objects of class Weapon
     */
    public Potion(int tier)
    {
        super(true, (tier * 5));
        this.tier = tier;
        switch (tier)
        {
            case 1: name = "Minor Health Potion";
            restore = 10;
            break;
            case 2: name = "Health Potion";
            restore = 30;
            break;
            case 3: name = "Greater Health Potion";
            restore = 100;
            break;
        }
    }

    public String toString()
    {
        return name;
    }

    public int getRestore()
    {
        return restore;
    }
    
    public int getID()
    {       
        return 1000 + tier;
    }
}



