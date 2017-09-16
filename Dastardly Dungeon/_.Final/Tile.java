public class Tile extends Block
{
    private final String AVATAR = ".";
    
    
    public Tile(Point loc)
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
        return false;
    }
    
    public Tile copy(Block block)
    {
        return new Tile(new Point( block.loc.X(), block.loc.Y()));
    }
   
}