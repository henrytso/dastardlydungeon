public class Wall extends Block 
{
    private final String AVATAR = "#";
    
    public Wall(Point loc)
    {
        super(loc);
    }
    
    public String toString()
    {
        if(super.discovered())
        return AVATAR;
        else return super.getUndiscovered();
    }
    
    public boolean collides()
    {
        return true;
    }
    
    public boolean isWall()
    {
        return true;
    }
}