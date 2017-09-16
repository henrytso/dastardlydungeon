
/**
 * wahpoo
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Weapon extends Item
{
    private int damage;
    private int effectFactor = 0;
    private int tier;
    private int weight = 0;
    private String name;
    private int modifier = 0;

    /**
     * Constructor for objects of class Weapon
     */
    public Weapon(int tier)
    {
        super(false, -1);
        this.tier = tier;
        
        switch (tier)
        {
            case 0: damage = 1;
                    name = "Battered Dagger";
                    break;
            case 1: damage = 2 + (int)(Math.random() * 4);
            name = "Dagger";
            break;
            case 2: damage = 5 + (int)(Math.random() * 6);
            name = "Sword";
            break;
            case 3: damage = 10 + (int)(Math.random() * 11);
            name = "Claymore";
            break;
        }
        
        weight = (damage/3) + (int)(Math.random() * (damage/4));
        
        if ( (int) ((Math.random() * 100)) <= (int) (Math.pow(2.92,tier)))//maybe have player luck affect this || 1 = 2, 2 = 8, 3 = 24
        {
           modify();
        }
    }

    public void modify()
    {

        if (modifier == 0)
        {
            modifier = (int) ((Math.random() * 6) + 1); // can be improved upon

            switch (modifier)
            {
                case 1: name = "Sharp " + name;  
                damage = (int) (damage * 1.2);
                break;
                case 2: name = "Dull " + name;
                damage = (int) (damage * .9);
                break;
                case 3: name = "Light " + name;
                weight = (int) (weight * .7);
                break;
                case 4: name = "Heavy " + name;
                weight = (int) (weight * 1.2);
                break;
                case 5: name = "Poisonous " + name;
                effectFactor = damage / 8 + 1;
                break;
                case 6: name = "Vampiric " + name;
                effectFactor = damage / 3 + 1;
                break;
                

            }
        }
    }

    public String toString()
    {
        return name + " [" + damage + "]";
    }

    public int getDamage()
    {
        return damage;
    }
    
    public int getWeight()
    {
        return weight;
    }
    
    public int getEffectFactor()
    {
        return effectFactor;
    }
    
    public int getModifier()
    {
        return modifier;
    }
    
    public int getID()
    {
        if (effectFactor > 0)
        return 4000 + damage + 1;
        return 4000 + damage;
    }
}






