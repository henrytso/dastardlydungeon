
/**
 * wahpoo
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Armor extends Item
{
    private int armor;
    private int effectFactor = 0;
    private int tier;
    private int weight = 0;
    private String name;
    private int modifier = 0;

    /**
     * Constructor for objects of class Weapon
     */
    public Armor(int tier)
    {
        super(false, -1);
        this.tier = tier;

        switch (tier)
        {
            case 0: armor = 0;
            name = "Old Shirt";
            break;
            case 1: armor = 2 + (int)(Math.random() * 4);
            name = "Tunic";
            break;
            case 2: armor = 5 + (int)(Math.random() * 6);
            name = "Chainmail";
            break;
            case 3: armor = 10 + (int)(Math.random() * 11);
            name = "Chestplate";
            break;
        }

        weight = (armor) + (int)(Math.random() * (armor/4));

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
                case 1: name = "Reinforced " + name;  
                armor = (int) (armor * 1.3);
                break;
                case 2: name = "Worn " + name;
                armor = (int) (armor * .8);
                break;
                case 3: name = "Light " + name;
                weight = (int) (weight * .6);
                break;
                case 4: name = "Heavy " + name;
                weight = (int) (weight * 1.3);
                break;
                case 5: name = "Thorned " + name; //deals damage back to attacker 
                effectFactor = armor / 6 + 1;
                break;
                case 6: name = "Holy " + name; //passive heal
                effectFactor = armor / 3 + 1;
                break;

            }
        }
    }

    public String toString()
    {
        return name + " [" + armor + "]";
    }

    public int getArmor()
    {
        return armor;
    }

    public int getWeight()
    {
        return weight;
    }

    public int getModifier()
    {
        return weight;
    }

    public int getEffectFactor()
    {
        return effectFactor;
    }

    public int getID()
    {       
        return 3000 + armor + effectFactor * 10;
    }

    public static void main(String[] args)
    {
        Armor arm;
        for (int i = 0; i < 50; i++)
        {arm = new Armor(1);
            System.out.println(arm.toString());}
        for (int i = 0; i < 50; i++)
        {arm = new Armor(2);
            System.out.println(arm.toString());}
        for (int i = 0; i < 50; i++)
        {arm = new Armor(3);
            System.out.println(arm.toString());}

    }
}



