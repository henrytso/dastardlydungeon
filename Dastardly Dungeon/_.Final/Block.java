/**
 * The most basic unit used to display the game.
 */
public abstract class Block 
{
    Point loc = new Point();
    final String UNDISCOVERED = "O";
    public boolean discovered = false;
    /**
     * @param Location of the block
     */
    public Block(Point loc)
    {
        this.loc = loc;
    }
    
    /**
     * @return the location of the block
     */
    public Point loc()
    {
        return loc;
    }
    
    /**
     * Sets the location of the block.
     */
    public void setLoc(Point loc)
    {
        this.loc = loc;
    }
    
    /**
     * @return the block's avatar.
     */
    public abstract String toString();
    
    public boolean isDoor()
    {
        return false;
    }
    
    public boolean isPlayer()
    {
        return false;
    }
    
    public boolean isMonster()
    {
        return false;
    }
    
    public boolean isWall()
    {
        return false;
    }
    
    public boolean isChest()
    {
        return false;
    }
    
    public boolean isShop()
    {
        return false;
    }
    
    public boolean isStaircase()
    {
        return false;
    }
    
    /**
     * @return the undiscovered avatar
     */
    
    public String getUndiscovered()
    {
        return UNDISCOVERED;
    }
    
    /**
     * @return wether or not the block has been revealed
     */
    
    public boolean discovered()
    {
        return discovered;
    }
    
    /**
     * Sets wether or not the block has been revealed
     */
    
    public void setDiscovered(boolean discovered)
    {
        this.discovered = discovered;
    }
    
    /**
     * @return Wether or not the player will collide with the block
     */
    public abstract boolean collides();
    
}