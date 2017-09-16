
/**
 * wahpoo
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Shield extends Item
{
    private int tier;
    private int guardChance;
    private int guardAmount;
    private int weight = 0;
    private int modifier = 0;
    private String name = "";

    /**
     * Constructor for objects of class Weapon
     */
    public Shield(int tier)
    {
        super(false, -1);
        this.tier = tier;
        switch (tier)
        {
            case 1: guardChance = 10 + (int)(Math.random() * 10);
            guardAmount = 30 + (int)(Math.random() * 10);
            name = "Guard";
            break;
            case 2: guardChance = 20 + (int)(Math.random() * 10);
            guardAmount = 40 + (int)(Math.random() * 10);
            name = "Shield";
            break;
            case 3: guardChance = 30 + (int)(Math.random() * 10);
            guardAmount = 50 + + (int)(Math.random() * 20);
            name = "Aegis";
            break;
        }
        
        weight = (int) (tier * 3 + Math.random() * tier);
        
        if ( (int) ((Math.random() * 100)) <= (int) (Math.pow(2.92,tier)))//maybe have player luck affect this || 1 = 2, 2 = 8, 3 = 24
        {
           modify();
        }
    }

    public void modify()
    {
        if (modifier == 0)
        {
            modifier = (int) ((Math.random() * 2) + 1); // can be improved upon

            switch (modifier)
            {
                case 1: name = "Light " + name;
                weight = (int) (weight * .8);
                guardChance = (int) (guardChance * 1.2);
                break;
                case 2: name = "Heavy " + name;
                weight = (int) (weight * 1.1);
                guardAmount = (int) (guardAmount * 1.2);
                break;
            }
        }
    }

    public String toString()
    {
        return name + " [" + guardAmount + "/" + guardChance + "%]";
    }
    
    public double doEffect()
    {
        if (Math.random() * 100 < guardChance)
        return (double) ((100 - guardAmount)/100.0);
        return 1.0;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public int getGuardChance()
    {
        return guardChance;
    }
    
    public int getGuardAmount()
    {
        return guardAmount;
    }
    
    public int getID()
    {
        return 50000 + (guardChance * 100) + guardAmount;
    }
}






