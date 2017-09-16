/**
 * The Player and enemies are all characters and have stats
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayerInfo
{
    private int maxHealth;
    private int currentHealth;
    private int attack;
    private int armor;
    private int speed;
    private int luck;
    private int experience;
    private int level;
    private int currency;
    private int totalCurrency;
    private int currencySpent = 0;
    
    
    private int effectArmor;
    private Inventory inventory;
    private Player player;
    private Weapon equipWeapon;
    private Shield equipShield;
    private Armor equipArmor;
    private int equipWeaponWeight = 0;
    private int equipShieldWeight = 0;
    private int equipArmorWeight = 0;

    /**
     * Constructs a level 1 player, there is never a need to create a player with a level greater than 1
     * If you are using a player in the world map, contruct the player like Player(loc, null)
     */
    public PlayerInfo()
    {
        maxHealth = 20;
        currentHealth = 20;
        attack = 4;
        armor = 0;
        speed = 1;
        luck = 2;
        experience = 0;
        level = 1;
        currency = 0;
        this.inventory = new Inventory();
        this.player = player;
        Weapon defaultWeapon = new Weapon(0);
        equip(defaultWeapon);
    }

    //get
    public int getMaxHealth()
    {
        return maxHealth;
    }

    public int getCurrentHealth()
    {
        return currentHealth;
    }

    public int getAttack()
    {
        return attack + equipWeapon.getDamage();
    }

    public int getArmor()
    {
        return armor;
    }
    
    public int getEffectArmor()
    {
        return effectArmor;
    }

    public int getSpeed()
    {
        return speed;
    }
    
    public int getCombatSpeed()
    {
        return speed + equipArmorWeight + equipWeaponWeight + equipShieldWeight;
    }

    public int getLuck()
    {
        return luck;
    }

    public int getExperience()
    {
        return experience;
    }

    public int getLevel()
    {
        return level;
    }

    public int getCurrency()
    {
        return currency;
    }

    public Inventory getInventory()
    {
        return inventory;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Weapon getEquipWeapon()
    {
        return equipWeapon;
    }
    
    public Shield getEquipShield()
    {
        return equipShield;
    }
    
    public Armor getEquipArmor()
    {
        return equipArmor;
    }
    
    public int getCurrencyDiff()
    {
        return totalCurrency - currency;
    }
    
    public boolean hasEquipWeapon()
    {
        return null != equipWeapon;
    }
    
    public boolean hasEquipShield()
    {
        return null != equipShield;
    }
    
    public boolean hasEquipArmor()
    {
        return null != equipArmor;
    }
    
    public void printStats()
    {
        Runner2.nextFrame();
        System.out.println("Level [" + level + "]");
        System.out.println("Experience to Next Level [" + (Math.pow(2,level+3) - experience) + "]");
        System.out.println("Health [" + currentHealth + "/" + maxHealth + "]");
        System.out.println("Attack [" + attack + "]");
        System.out.println("Armor [" + armor + "]");
        System.out.println("Speed [" + speed + "]");
        System.out.println("Luck [" + luck + "]");
        System.out.println("Soulstones [" + currency + "]");
        System.out.println("Equipped");
        int equipWeight = 0;
        if (equipWeapon != null)
        equipWeight += equipWeapon.getWeight();
        if (equipShield != null)
        equipWeight += equipShield.getWeight();
        if (equipArmor != null)
        equipWeight += equipArmor.getWeight();
        
        System.out.println("Total Weight [" + equipWeight + "]");
        if (equipWeapon == null)
            System.out.println("-NO WEAPON EQUIPED-");
        else
        {
            System.out.println(equipWeapon.toString());
            System.out.println("  Damage [" + equipWeapon.getDamage() + "]");
            System.out.println("  Weight [" + equipWeapon.getWeight() + "]");
            if (equipWeapon.getModifier() == 5)
            System.out.println("  Poison [" + equipWeapon.getEffectFactor() + "]");
            if (equipWeapon.getModifier() == 6)
            System.out.println("  Vampiric [" + equipWeapon.getEffectFactor() + "]");
        }
        if (equipShield == null)
            System.out.println("-NO SHIELD EQUIPED-");
        else
        {
            System.out.println(equipShield.toString());
            System.out.println("  Guard Chance [" + equipShield.getGuardChance() + "]");
            System.out.println("  Guard Amount [" + equipShield.getGuardAmount() + "]");
            System.out.println("  Weight [" + equipShield.getWeight() + "]");
        }
        if (equipArmor == null)
            System.out.println("-NO ARMOR EQUIPED-");
        else
        {
            System.out.println(equipArmor.toString());
            System.out.println("  Armor [" + equipArmor.getArmor() + "]");
            System.out.println("  Weight [" + equipArmor.getWeight() + "]");
        }
    }

    public void printInventory()
    {
        Runner2.nextFrame();
        System.out.println("Soulstones: " + currency);
        System.out.println("Equipped");
        int equipWeight = 0;
        if (equipWeapon != null)
        equipWeight += equipWeapon.getWeight();
        if (equipShield != null)
        equipWeight += equipShield.getWeight();
        if (equipArmor != null)
        equipWeight += equipArmor.getWeight();
        
        System.out.println("Total Weight [" + equipWeight + "]");
        if (equipWeapon == null)
            System.out.println("-NO WEAPON EQUIPED-");
        else
        {
            System.out.println(equipWeapon.toString());
            System.out.println("  Damage [" + equipWeapon.getDamage() + "]");
            System.out.println("  Weight [" + equipWeapon.getWeight() + "]");
            if (equipWeapon.getModifier() == 5)
            System.out.println("  Poison [" + equipWeapon.getEffectFactor() + "]");
            if (equipWeapon.getModifier() == 6)
            System.out.println("  Vampiric [" + equipWeapon.getEffectFactor() + "]");
        }
        if (equipShield == null)
            System.out.println("-NO SHIELD EQUIPED-");
        else
        {
            System.out.println(equipShield.toString());
            System.out.println("  Guard Chance [" + equipShield.getGuardChance() + "]");
            System.out.println("  Guard Amount [" + equipShield.getGuardAmount() + "]");
            System.out.println("  Weight [" + equipShield.getWeight() + "]");
        }
        if (equipArmor == null)
            System.out.println("-NO ARMOR EQUIPED-");
        else
        {
            System.out.println(equipArmor.toString());
            System.out.println("  Armor [" + equipArmor.getArmor() + "]");
            System.out.println("  Weight [" + equipArmor.getWeight() + "]");
        }
        inventory.printInventory();
    }
    //set    
    public void setMaxHealth(int x)
    {
        maxHealth = x;
    }

    public void setCurrentHealth(int x)
    {
        if (x > maxHealth)
            currentHealth = maxHealth;
        else
        currentHealth = x;
    }

    public void setAttack(int x)
    {
        attack = x;
    }

    public void setArmor(int x)
    {
        armor = x;
    }
    
    public void setEffectArmor(int x)
    {
        effectArmor = x;
    }

    public void setSpeed(int x)
    {
        speed = x;
    }

    public void setLuck(int x)
    {
        luck = x;
    }

    public void setExperience(int x)
    {
        experience = x;
    }

    public void setLevel(int x)
    {
        level = x;
    }

    public void addCurrency(int x)
    {
        currency += x;
        totalCurrency += x;
    }
    
    public void setCurrency(int x)
    {
        int diff = (int) Math.abs(x - currency);
        if (diff > 0)
        totalCurrency += diff;
        currency = x;
    }
    
    public void setInventory(Inventory x)
    {
        inventory = x;
    }

    //equippers
    public void equip(Item x)
    {
        if (x.getID() >= 3000)
        {
            inventory.removeItem(x);
            if (x.getID() >= 3000 && x.getID() < 4000)
            {
                if (equipArmor == null)
                {
                    equipArmor = (Armor) x;
                    equipArmorWeight = ((Armor) x).getWeight();
                }
                else
                {
                    Armor temp = equipArmor;
                    equipArmor = (Armor) x;
                    inventory.add(temp);
                    equipArmorWeight = ((Armor) x).getWeight();
                }
            }
            else
            {
                if (x.getID() >= 4000 && x.getID() < 50000) //not a typo, shields are 50000
                {
                    if (equipWeapon == null)
                    {
                        equipWeapon = (Weapon) x;
                        equipWeaponWeight = ((Weapon) x).getWeight();
                    }
                    else
                    {
                        Weapon temp = equipWeapon;
                        equipWeapon = (Weapon) x;
                        inventory.add(temp);
                        equipWeaponWeight = ((Weapon) x).getWeight();
                    }
                }
                else
                {
                    if (equipShield == null)
                    {
                        equipShield = (Shield) x;
                        equipShieldWeight = ((Shield) x).getWeight();
                    }
                    else
                    {
                        Shield temp = equipShield;
                        equipShield = (Shield) x;
                        inventory.add(temp);
                        equipShieldWeight = ((Shield) x).getWeight();
                    }
                }
            }
        }
    }
    
    public void consume(Item x)
    {
        if (x.getID() < 2000)
        {
            setCurrentHealth(currentHealth + ((Potion) x).getRestore());
            inventory.removeItem(x);
        }
    }

    /**
     * odds of returning true are luck/x
     */
    public boolean luckRoll(int x)
    {
        return ((double) luck > (double)(Math.random() * x));
    }
    
    public void levelUp()
    {
        while (experience >= Math.pow(2,level+3))
        {
            level++;
            switch (level)
            {
                case 2: maxHealth = 60;
                        attack = 6;
                        armor = 1;
                        speed = 1;
                        luck = 9;
                        break;
                case 3: maxHealth = 75;
                        attack = 8;
                        armor = 2;
                        speed = 2;
                        luck = 12;
                        break;
                case 4: maxHealth = 97;
                        attack = 11;
                        armor = 4;
                        speed = 4;
                        luck = 15;
                        break;
                case 5: maxHealth = 130;
                        attack = 15;
                        armor = 8;
                        speed = 6;
                        luck = 18;
                        break;
                case 6: maxHealth = 180;
                        attack = 21;
                        armor = 12;
                        speed = 12;
                        luck = 21;
                        break;
                case 7: maxHealth = 250;
                        attack = 29;
                        armor = 18;
                        speed = 20;
                        luck = 24;
                        break;
                case 8: maxHealth = 357;
                        attack = 350;
                        armor = 24;
                        speed = 28;
                        luck = 27;
                        break;
                case 9: maxHealth = 500;
                        attack = 40;
                        armor = 30;
                        speed = 40;
                        luck = 30;
                        break;
                default:
                break;
            }
            currentHealth = maxHealth;
            System.out.println("You have leved up to level " + level + "!");
            Combat.pause(); //reusing the code
        }
    }
}