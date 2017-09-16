public class MonsterInfo extends PlayerInfo
{
    int tier;
    String name;
    public MonsterInfo(int tier)
    {
        Inventory drops = new Inventory();
        this.tier = tier;
        genName();
        int type = (int)(Math.random()*100 + 1);
        if (type < 35)
            drops.add(new Potion(tier));
        if (type >= 35 && type < 60)
            drops.add(new Armor(tier));
        if (type >= 60 && type < 80)
            drops.add(new Weapon(tier));
        if (type >= 81 && type < 101)
            drops.add(new Shield(tier));
        int currency = 6 + tier * (int)(Math.random() * 1.5 + 1);
        super.setCurrency(currency);
        super.setInventory(drops);
        super.setExperience((int) Math.pow(2,tier+1));
        switch (tier)
        {
            case 1: super.setMaxHealth( (int)(5 * Math.random()) + 15);
            super.setCurrentHealth(super.getMaxHealth());
            super.setAttack(3);
            super.setArmor(0);
            super.setSpeed((int) (((Math.random() * 2))));
            super.setLuck(6);
            break;
            case 2: super.setMaxHealth((int)(10 * Math.random()) + 60);
            super.setCurrentHealth(super.getMaxHealth());
            super.setAttack(13 + (int) (Math.random() * 6));
            super.setArmor(1 + (int) (Math.random() * 2));
            super.setSpeed(3 + (int) (Math.random() * 8));
            super.setLuck(15);
            break;
            case 3: super.setMaxHealth( (int)(75 * Math.random()) + 200);
            super.setCurrentHealth(super.getMaxHealth());
            super.setExperience((int) Math.pow(2,tier+1) + 5);
            super.setAttack(23 + (int) (Math.random() * 9));
            super.setArmor(2 + (int) (Math.random() * 6));
            super.setSpeed(14 + (int) (Math.random() * 8));
            super.setLuck(15);
            break;
        }
    } 
    
    public MonsterInfo(int tier, PlayerInfo player)
    {
        switch (tier)
        {
            case 4: //final boss - demon
            super.setMaxHealth(1000); 
            super.setCurrentHealth(1000); 
            super.setAttack(35);
            super.setArmor(18); 
            super.setSpeed(35);
            super.setLuck(30);
            break;
            case 5: //final boss - shopkeeper
            super.setMaxHealth(700 + (int) (player.getCurrencyDiff() * .75));
            super.setCurrentHealth((player.getAttack()*8) + (int) (player.getCurrencyDiff() * .75));
            super.setAttack(player.getMaxHealth()/2);
            super.setArmor(10); 
            super.setSpeed(1000); 
            super.setLuck(1); //
            break;
        }
    } 
    
    private void genName()
    {
        name = "";
        String[] syl = new String[]{"Org","Gal","Cho","Gul","Dan","Tor","Kirin","Oth", "Took", "Jib" , "Lod", "Shek" , "Kel"};
        int length = (int)(Math.random() * 3 + 1 );
        int index;
        for (int i = 0; i < length; i++)
        {
            index = (int)(Math.random()*syl.length);
            if (i == 0) name += syl[index];
            else name+= syl[index].toLowerCase();
        }
        if (tier == 2) name += " the Crusher";
        if (tier == 3) name += " the Soul Eater";
    }
    
    public int getTier()
    {
        return tier;
    }
    
    public String getName()
    {
        return name;
    }
    
    public static void main (String[] args)
    {
        MonsterInfo m;
        for (int i = 0; i < 200; i++)
        {
            m = new MonsterInfo(2);
            System.out.println(m.getName());
        }
    }
    
    }
