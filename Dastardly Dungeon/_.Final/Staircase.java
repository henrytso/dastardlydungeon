public class Staircase extends Block
{
    private boolean up;
    private final String upAvatar = "^";
    private final String downAvatar = "V";
    private boolean finalBoss = false;
    
    
    public Staircase (Point loc,boolean up)
    {
        super(loc);
        this.up = up;
    }

    public String toString()
    {
        if(super.discovered())
        
        {if(up) return upAvatar;
        return downAvatar;}
        else return super.getUndiscovered();
    }
    
    
    public boolean isFinalBoss()
    {
        return finalBoss;
    }
    
    public void setFinalBoss()
    {
        finalBoss = true;
    }

    public void setUp(boolean up)
    {
        this.up = up;
    }
    
    public boolean collides()
    {
        return true;
    }
    
    public boolean up()
    {
        return up;
    }
    
    public boolean isStaircase()
    {
        return true;
    }

}