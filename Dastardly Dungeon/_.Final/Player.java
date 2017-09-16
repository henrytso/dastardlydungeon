public class Player extends Block 
{
    String dir = "N";
    final String AVATAR = "@";
    Point loc, North, East, South, West, wLoc;
    Block[][] map;
    Room[][] worldMap; 
    boolean nextRoom;
    boolean fighting;
    boolean inChest;
    boolean inShop;
    boolean atStaircase;
    boolean monsterCombat;
    boolean pinged;

    Point dest;
    Point chest, monster, shop, staircase, player;

    public Player(Point loc)
    {
        super(loc);
        // this.wLoc = wLoc;
        this.loc = loc;
        North = new Point (loc.X() , loc.Y() - 1);
        East = new Point (loc.X() + 1 , loc.Y());
        South = new Point (loc.X() , loc.Y() + 1);
        West = new Point (loc.X() - 1 , loc.Y());

    }

    // Methods to be used every tick

    public boolean collides()
    {
        return true;
    }

    public boolean monsterCombat()
    {
        if(monsterCombat)
        {

            monsterCombat = false;
            return true;
        }
        return false;
    }
    
    public void ping()
    {
        pinged = true;
    }
    
    public boolean inCombat()
    {
        return fighting;
    }

    public void outOfCombat()
    {
        fighting = false;
    }

    public boolean inChest()
    {
        return inChest;
    }

    public void outOfChest()
    {
        inChest = false;
    }

    public boolean inShop()
    {
        return inShop;
    }

    public void outOfShop()
    {
        inShop = false;
    }

    public boolean atStaircase()
    {
        return atStaircase;
    }

    public Point staircase()
    {
        return staircase;
    }

    public void notAtStaircase()
    {
        atStaircase = false;
    }

    public boolean nextRoom()
    {
        return nextRoom;

    }

    public void loadMap(Block[][] bMap)
    {
        map = new Block[bMap.length][bMap[0].length];
        for ( int r = 0; r < map.length; r++)
            for(int c = 0; c < map[0].length; c++)
                map[r][c] = bMap[r][c];
    }

    // 

    public void loadWorldMap(Room[][] wMap)
    {
        worldMap = new Room[wMap.length][wMap[0].length];
        for (int r = 0; r < worldMap.length; r++)
            for(int c = 0; c < worldMap[0].length; c++)
                worldMap[r][c] = wMap[r][c];
    }

    //Movement and location methods

    public Point monster()
    {
        return monster;
    }

    public Point chest()
    {
        return chest;
    }

    public Point shop()
    {
        return shop;
    }

    public void setLoc(Point loc)
    {
        this.loc = loc;
        updateLoc();
    }

    public Point loc()
    {
        return loc;
    }

    public void setDir(String dir)
    {
        this.dir = dir;
    }

    public String dir()
    {
        return dir;
    }

    public void move()
    {
        if(dir.equals("N"))
        {
            stepUp();

        }
        if(dir.equals("E"))
        {
            stepRight();
        }
        if(dir.equals("S"))
        {
            stepDown();
        }
        if(dir.equals("W"))
        {
            stepLeft();
        }   
    }

    public void stepUp()
    {
        if ( map[North.Y()][North.X()].isDoor())
        {
            nextRoom = true;
        }
        else nextRoom = false;

        if ( ! map[North.Y()][North.X()].collides())
        {
            if (map[North.getNorth().Y()][North.getNorth().X()].isMonster() && map[North.getNorth().Y()][North.getNorth().X()].collides())
            {
                fighting = true;
                monster = North.getNorth();
            }
            else {
                fighting = false;
                dest = North;
                loc.change(0 , -1);
                super.setLoc(loc);
                updateLoc();
            }
        }
        else 
        {
            if (map[North.Y()][North.X()].isMonster())
            {
                fighting = true;
                monster = North;
            }
            else fighting = false;
            if (map[North.Y()][North.X()].isChest())
            {
                inChest = true;
                chest = North;
            }
            else inChest = false;
            if (map[North.Y()][North.X()].isShop())
            {
                inShop = true;
                shop = North;
            }
            else inShop = false;
            if (map[North.Y()][North.X()].isStaircase())
            {
                atStaircase = true;
                staircase = North;
            }
            else atStaircase = false;
            if ((map[North.Y()][North.X()].isPlayer() && ! map[North.Y()][North.X()].isMonster()))
            {
                monsterCombat = true;
                player = North;
            }
            else monsterCombat = false;
        }

    }

    public boolean isPlayer()
    {
        return true;
    }

    public void stepDown()
    {
        if ( map[South.Y()][South.X()].isDoor())
        {
            nextRoom = true;
        }
        else nextRoom = false;

        if(! map[South.Y()][South.X()].collides())
        {
            if (map[South.getSouth().Y()][South.getSouth().X()].isMonster() && map[South.getSouth().Y()][South.getSouth().X()].collides())
            {
                fighting = true;
                monster = South.getSouth();
            }
            else {
                fighting = false;
                dest = South;
                loc.change(0, 1);
                super.setLoc(loc);
                updateLoc();
            }
        }
        else 
        {
            if (map[South.Y()][South.X()].isMonster())
            {
                fighting = true;
                monster = South;
            }
            else fighting = false;
            if (map[South.Y()][South.X()].isChest())
            {
                inChest = true;
                chest = South;
            }
            else inChest = false;
            if (map[South.Y()][South.X()].isShop())
            {
                inShop = true;
                shop = South;
            }
            else inShop = false;
            if (map[South.Y()][South.X()].isStaircase())
            {
                atStaircase = true;
                staircase = South;
            }
            else atStaircase = false;
            if (map[South.Y()][South.X()].isPlayer() && ! map[South.Y()][South.X()].isMonster() )
            {
                monsterCombat = true;
                player = South;
            }
            else monsterCombat = false;
        }
    }

    public void stepRight()
    {
        if ( map[East.Y()][East.X()].isDoor())
        {
            nextRoom = true;
        }
        else nextRoom = false;

        if(! map[East.Y()][East.X()].collides())
        {
            if (map[East.getEast().Y()][East.getEast().X()].isMonster() && map[East.getEast().Y()][East.getEast().X()].collides())
            {
                fighting = true;
                monster = East.getEast();
            }
            else {
                fighting = false;
                dest = East;
                loc.change(1, 0);
                super.setLoc(loc);
                updateLoc();
            }
        }
        else 
        {
            if (map[East.Y()][East.X()].isMonster())
            {
                fighting = true;
                monster = East;
            }
            else fighting = false;
            if (map[East.Y()][East.X()].isChest())
            {
                inChest = true;
                chest = East;
            }
            else inChest = false;
            if (map[East.Y()][East.X()].isShop())
            {
                inShop = true;
                shop = East;
            }
            else inShop = false;
            if (map[East.Y()][East.X()].isStaircase())
            {
                atStaircase = true;
                staircase = East;
            }
            else atStaircase = false;
            if (map[East.Y()][East.X()].isPlayer() && ! map[East.Y()][East.X()].isMonster() )
            {
                monsterCombat = true;
                player = East;
            }
            else monsterCombat = false;
        }
    }

    public void stepLeft()
    {
        if( map[West.Y()][West.X()].isDoor())
        {
            nextRoom = true;
        }
        else nextRoom = false;

        if(! map[West.Y()][West.X()].collides())
        {
            if (map[West.getWest().Y()][West.getWest().X()].isMonster() && map[West.getWest().Y()][West.getWest().X()].collides())
            {
                fighting = true;
                monster = West.getWest();
            }
            else {
                fighting = false;
                dest = West;
                loc.change(-1, 0);
                super.setLoc(loc);
                updateLoc();
            }
        }    
        else 
        {
            if (map[West.Y()][West.X()].isMonster())
            {
                fighting = true;
                monster = West;
            }
            else fighting = false;
            if (map[West.Y()][West.X()].isChest())
            {
                inChest = true;
                chest = West;
            }
            else inChest = false;
            if (map[West.Y()][West.X()].isShop())
            {
                inShop = true;
                shop = West;
            }
            else inShop = false;
            if (map[West.Y()][West.X()].isStaircase())
            {
                atStaircase = true;
                staircase = West;
            }
            else atStaircase = false;
            if (map[West.Y()][West.X()].isPlayer() && ! map[West.Y()][West.X()].isMonster() )
            {
                monsterCombat = true;
                player = West;
            }
            else monsterCombat = false;
        }
    }

    // returns the player avatar
    public String toString()
    {
        if (pinged)
        {
            pinged = false;
            return "█";
        }
        return AVATAR;
    }

    public Point dest()
    {
        return dest;
    }

    private void updateLoc()
    {
        North.set(loc.X() , loc.Y() - 1);
        East.set(loc.X() + 1 , loc.Y());
        South.set(loc.X() , loc.Y() + 1);
        West.set(loc.X() - 1 , loc.Y());
    }

}