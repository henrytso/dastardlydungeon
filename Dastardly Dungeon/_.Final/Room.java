import java.util.ArrayList;
/**
 * Where the player moves about. Contains walls, tiles, chests, a shop, and staircases. Keeps track of all moving entities' locations
 */
public class Room
{    
    Player p;
    Block[][] map;
    Block[][] staticMap;
    ArrayList<Monster> monsters;
    private int tier;
    private Point shop;
    
    private boolean inCombat;
    Monster monsterInCombat;
    
    private final int FIELD_OF_VISION = 6;
    
    
    /**
     * Converts a 2D block array into a Room, spawning Monsters in it.
     * @param 2D Block array
     * @param floor #
     */
    
    public Room(Block[][] bMap, int tier)
    {
        monsters = new ArrayList<Monster>();
        this.tier = tier;
        map = new Block[bMap.length][bMap[0].length];
        staticMap = new Block[bMap.length][bMap[0].length];
        for (int r = 0; r < bMap.length; r++)
        {
            for (int c = 0; c < bMap[0].length; c++)
            {
               map[r][c] = bMap[r][c];
               staticMap[r][c] = bMap[r][c];
               if (map[r][c].isShop())
               {
                   shop = map[r][c].loc();
                }
            }
        }
        
        for (int r = 0; r < map.length; r++)
        {
            for(int c = 0; c < map[0].length; c++)
            {
                double chance = Math.random();
                 Point temp = new Point ( c , r);
                 if (!(map[r][c].collides()) && chance < .007)
                    {
                        map[r][c] = new Monster (temp, tier);
                       
                        monsters.add((Monster)map[r][c]);
                    
                    }
            }
        
        }
        
        for ( int r = 0; r < map.length; r++)
            for ( int c = 0; c < map[0].length; c++)
                {
                    if (map[r][c].isMonster())
                     ((Monster)map[r][c]).loadMap(map);
                }
    }
    
    /**
     * @param Point loc
     * @return the Block in the room located at loc.
     */
    
    public Block getBlock(Point loc)
    {
        return map[loc.Y()][loc.X()];
    }
    
    /**
     * Pings the player, making it temporarily more visible.
     */
    
    public void ping()
    {
        p.ping();
    }
    
    /**
     * Gets a valid point for the player to spawn on the first floor.
     * @return a valid spawn point.
     */
     public Point getSpawn()
    {
       for(int r = 0; r < map.length; r++)
            for(int c = 0; c < map[0].length; c++)
                   if(! map[r][c].collides()){
                    //   System.out.println(r + " , " + c);
                       return new Point(c, r);
                    }
                   
       return new Point(-1 , -1);
    }
    
    /**
     * @param the direction of the staircase
     * @return a valid point around the staircase to spawn the player
     */
    public Point getStaircaseSpawn (boolean up)
    {
        Point temp = new Point(0,0);
        for(int r = 0; r < map.length; r++)
            for(int c = 0; c < map[0].length; c++)
                if (map[r][c].isStaircase() && ((Staircase) map[r][c]).up() != up)
                    temp = new Point ( c , r);
        
        
        if(!map[temp.getNorth().Y()][temp.getNorth().X()].collides())
            temp = temp.getNorth();
        else if(!map[temp.getEast().Y()][temp.getEast().X()].collides())
            temp = temp.getEast();
        else if (!map[temp.getSouth().Y()][temp.getSouth().X()].collides())
            temp = temp.getSouth();
        else if (!map[temp.getWest().Y()][temp.getWest().X()].collides())
            temp = temp.getWest();
        map[temp.Y()][temp.X()] = new Player(temp);
        p = new Player(temp);
        
        return temp;
    }
    
    
    /**
     * Spawns the player in the Room.
     * @param Spawn point
     */
    public Point spawnPlayer(Point temp)
    {
        
         map[temp.Y()][temp.X()] = new Player(temp);
        p = new Player(temp);
        
        return temp;
    }
    
    /**
     * Updates player and monster movement.
     */
    
    public void updateMap()
    {
         
        for (int r = 0; r < map.length; r++)
        {
            for (int c = 0; c < map[0].length; c++)
            {
                if (((int)(Math.abs(r - p.loc().Y())) <= FIELD_OF_VISION && ((int)(Math.abs(c - p.loc().X()))) <= FIELD_OF_VISION ))
                {
                    map[r][c].setDiscovered(true);
                    staticMap[r][c].setDiscovered(true);
                }
                if (((int)(Math.abs(r - shop.X())) <= FIELD_OF_VISION && ((int)(Math.abs(c - shop.Y()))) <= FIELD_OF_VISION ))
                 {
                     map[r][c].setDiscovered(true);
                    staticMap[r][c].setDiscovered(true);
                }
              /*  else
                {
                    map[r][c].setDiscovered(false);
                    staticMap[r][c].setDiscovered(false);
                }*/
                map[r][c] = staticMap[r][c];
                
            }
        }
        
        map[p.loc().Y()][p.loc().X()] = p;  
        
        for (int m = 0; m < monsters.size(); m++)
        {      
             monsters.get(m).loadMap(map);
             map[monsters.get(m).loc().Y()][monsters.get(m).loc().X()] = monsters.get(m);
             monsters.get(m).setPlayerLoc(p.loc());
         
             if ((monsters.get(m)).discovered())
             {
             
                
                 monsters.get(m).step();
                 if (monsters.get(m).monsterCombat())
                   {
                       inCombat = true;
                       monsterInCombat = monsters.get(m);
                   }
             }
        }
                
        
     
    }
    
    /** 
     * Checks if a monster is in combat.
     */
    
    public boolean inCombat()
    {
        if(inCombat)
        {
            inCombat = false;
            return true;
        }
        return inCombat;
    }
    
    /**
     * @return the reference to the monster currently engaged in combat.
     */
    
    public Monster getMonster()
    {
        return monsterInCombat;
    }
    
    /**
     * Prints the current state of the room.
     */
    
    public void print()
    {
        System.out.println("FLOOR " + tier + "                                                                                                                                                                           Menu = 'm'  Ping = 'p'  ");
        for ( int r = 0; r < map.length; r++)
        {
            for ( int c = 0; c < map[0].length; c++)
            {
                System.out.print(map[r][c].toString());
            }
            System.out.println();
        }
    }
       
    /**
     * @returns the 2D Block array (with Monsters and Player)
     */
    
    public Block[][] map()
    {
        return map;
    }
    
    /**
     * @return the Player's current location
     */
    
    public Point playerLoc()
    {
        return p.loc();
    }
    
   
}