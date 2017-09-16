import java.util.ArrayList;
public class Monster extends Player
{
    int tier;
    private boolean alive = false;
    
    ArrayList <Item> drops;
    private final String aliveAvatar = "M";
    private final String deadAvatar = "W";
    MonsterInfo info;
    private final int RANGE_OF_VISION = 3;
    private int distanceToPlayer;
    private Point humanPlayerLoc;

    public Monster(Point loc, int tier)
    {
        super(loc);
        this.tier = tier;
        alive = true;
        info = new MonsterInfo(tier);

      
    }
    
      public boolean discovered()
    {
        return super.discovered();
    }
    


    public String toString()
    {   if (super.discovered())
        if (alive) return aliveAvatar;
        else return deadAvatar;
      return super.getUndiscovered();
    }
    
    public void setPlayerLoc(Point humanLoc)
    {
        this.humanPlayerLoc = humanLoc;
    }
    
    public boolean inRange()
    {
        if ((int)Math.abs(humanPlayerLoc.Y() - super.loc().Y()) <= RANGE_OF_VISION && (int)Math.abs(humanPlayerLoc.X() - super.loc().X()) <= RANGE_OF_VISION)
        return true;
        return false;

    }
    
    public void step()
    {
       
        
        if (alive)
        {
            if (!inRange())
            {
                double choose = Math.random();
                if (choose < .25) 
                {
                    super.setDir("N");
                    super.move();
                }
                else if (choose < .50)
                {
                    super.setDir("E");
                    super.move();
                }
                else if (choose < .75)
                {
                    super.setDir("S");
                    super.move();
                }
                else if (choose < 1)
                {
                    super.setDir("W");
                    super.move();
                }
            }
            else
            {
                if (humanPlayerLoc.X() - super.loc().X() < 0) //Is human left of monster?
                {
                    super.setDir("W");
                    super.move();
                }
                if (humanPlayerLoc.X() - super.loc().X() > 0) //Is human right of monster?
                {
                     super.setDir("E");
                    super.move();
                }
                 if (humanPlayerLoc.Y() - super.loc().Y() < 0) //Is human above the monster?
                {
                    super.setDir("N");
                    super.move();
                }
                 if (humanPlayerLoc.Y() - super.loc().Y() > 0) //Is human below the monster?
                {
                    super.setDir("S");
                    super.move();
                }
            }
        }
    }

    public boolean collides()
    {
        if (alive) return true;
        return false;
    }

    public MonsterInfo info()
    {
        return info;
    }

    public boolean isMonster()
    {
        return true;
    }

    public void update()
    {
        if (info.getCurrentHealth() < 1) alive = false;
    }
}