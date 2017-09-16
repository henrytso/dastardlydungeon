
/**
 * Write a description of class Point here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Point
{
    // instance variables - replace the example below with your own
    private int x;
    private int y;
    
    /**
     * Constructor for objects of class Point
     */
    public Point()
    {
        // initialise instance variables
        x = 0;
        y = 0;
    }
    
    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    public int X()
    {
        return x;
    }
    
    public int Y()
    {
        return y;
    }
    
    public void set(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void change(int a, int b)
    {
        x += a;
        y += b;
    }
    
    public boolean equals(Point pt)
    {
        if (x == pt.X() && y == pt.Y())
            return true;
        return false;
        
    }
    
    public Point getNorth() {
        return new Point(this.x, this.y - 1);
    }
    
    public Point getEast() {
        return new Point(this.x + 1, this.y);
    }
    
    public Point getSouth() {
        return new Point(this.x, this.y + 1);
    }
    
    public Point getWest() {
        return new Point(this.x - 1, this.y);
    }

}
