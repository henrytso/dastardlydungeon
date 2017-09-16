//import java.lang.Math;
/**
 * Randomly generates a map, which includes an assortment of  Walls, Tiles, Chests, Shops, and Staircases.
 */
public class MapGenerator
{
    private boolean[][] map;
    private Block[][] bMap;
    private int[][] attempts;
    private int floor;

    private Point  center;


    //mapGen variables

    private final double ATTEMPTS = 1000;
    private final double SUCCESS_LIMITER = 2;
    private final double IDEAL = .68;
    private final int ROUNDS = 1000;
    private final int CHEST_MAX = 15;

    //Statistic Info

    private int wallCount = 0;
    private int tileCount = 0;
    private int monsterCount = 0;
    private int chestCount = 0;
    
    //Graphics
    
    private PlayerInfo loader;
    private HealthBar loading;

    /**
     * Initializes generator's parameters.
     * @param height of the map
     * @param length of the map
     * @param floor (1 through 4)
     */
    public MapGenerator(int h , int l, int floor)
    {
        loader = new PlayerInfo();
        loader.setCurrentHealth(0);
        loading = new HealthBar(loader);
        map = new boolean[h][l];
        attempts = new int[h][l];
        this.floor = floor;

        for (int r = 0; r < attempts.length; r++)
            for (int c = 0; c < attempts[0].length; c++)
                attempts[r][c] = 0;

        for (int r = 0; r < map.length; r++)
        {
            for (int c = 0; c < map[0].length; c++)
            {
                map[r][c] = false;
            }   
        }

        
        center = new Point(l / 2 , h / 2);
        

    }
    
    
    /**
     * Randomly creates a boolean array of Tiles/Walls and then converts them into Blocks
     */
    
    public void generate()
    {

        double total = map.length * map[0].length;
        double actualOpen;
        double open;
        connect();
        do{
            actualOpen = 0;
            open = 0;
            clear(); 
            for (int i = 0; i < ROUNDS; i++)
            {
                for (int r = 0; r < map.length; r++)
                {
                    for (int c = 0; c < map[0].length; c++)
                    {

                        pointGen(new Point ( c , r ));

                    }

                }
            }

            for (int r = 0; r < map.length; r++)
                for (int c = 0; c < map[0].length; c++)
                    if (map[r][c]) open++;

            actualOpen = open / total;
           Runner2.nextFrame();
           
           System.out.print("LOADING MAP " + floor + ":");
           loading.renderBar();
      
           loader.setCurrentHealth(loader.getCurrentHealth() + loader.getMaxHealth() / 10);
           
            
            

        }while(actualOpen <  IDEAL - .3 || actualOpen > 1 ); 
        Runner2.nextFrame();
        loader.setCurrentHealth( loader.getMaxHealth() );
        System.out.print("LOADING MAP " + floor + ":");
        loading.renderBar();
        Runner2.nextFrame();
        System.out.println("LOADING COMPLETE");
        spawn();
       
    }

    /**
     * Resets the boolean array 
     */
    
    public void clear()
    {
        for (int r = 0; r < map.length; r++)
            for (int c = 0; c < map[0].length; c++)
            {
                map[r][c] = false;
                attempts[r][c] = 0;
            }
        
        connect();

    }

    /**
     * Creates the base structure of the map in the boolean array. (Three tangent circles)
     */
    
    private void connect()
    {
        for ( int r = 0; r < map.length; r++)
        {
            for (int c = 0; c < map[0].length; c++)
            {

                if ((int)Math.pow((r - center.Y()),2) + (int)Math.pow((c - center.X()), 2) <= (int)Math.pow(map.length / 3 , 2)
                && (int)Math.pow((r - center.Y()),2) + (int)Math.pow((c - center.X()), 2) > (int)Math.pow(map.length / 3 - 2, 2))
                    map[r][c] = true;
                if ((int)Math.pow((r - center.Y()),2) + (int)Math.pow((c - center.X()) -2*(map.length/3), 2) <= (int)Math.pow(map.length / 3 , 2)
                && (int)Math.pow((r - center.Y()),2) + (int)Math.pow((c - center.X()) - 2*(map.length/3), 2) > (int)Math.pow(map.length / 3 - 2, 2))
                    map[r][c] = true;
                if ((int)Math.pow((r - center.Y()),2) + (int)Math.pow((c - center.X())+ 2*(map.length/3), 2) <= (int)Math.pow(map.length / 3 , 2)
                && (int)Math.pow((r - center.Y()),2) + (int)Math.pow((c - center.X())+ 2*(map.length/3), 2) > (int)Math.pow(map.length / 3 - 2, 2))
                    map[r][c] = true;
               
            }
        }
    }
 
    /**
     * Prints the map (For dev use)
     */

    private void print()
    {
        for (int r = 0; r < bMap.length; r ++)
        { 
            for(int c = 0; c < bMap[0].length; c++)
            {
                bMap[r][c].setDiscovered(true);
                System.out.print(bMap[r][c].toString());
            }

            System.out.println();
        }
    }
    
    /**
     * Prints the map statistics (For dev use)
     */
    
    private void printInfo()
    {
        System.out.println("Total number of walls: " + wallCount);
        System.out.println("Total number of tiles: " + tileCount);
        System.out.println("Percent tiles: " + (double)(tileCount) / (double)(wallCount));
        System.out.println("Total number of monsters: " + monsterCount);
        System.out.println("Tiles per Monster: " +  (double) tileCount / (double) monsterCount);
        System.out.println("Total number of chests: " + chestCount);
        System.out.println("Tiles per chest: " + (double) tileCount / (double) chestCount);
    }

    /**
     * Provides a chance that a given true point in a 2D boolean array to spawn another true point in one of the four cardinal directions.
     * @param A point on the boolean array
     */
    
    private void pointGen(Point pt)
    {
        if (map[pt.Y()][pt.X()] && attempts[pt.Y()][pt.X()] < ATTEMPTS && pt.Y() > 2 && pt.Y() < map.length - 2 && pt.X() > 1 && pt.X() < map[0].length - 2)
        {
            double chance = Math.random();
            if(chance <= .2)
            {
                map[pt.getNorth().Y()][pt.getNorth().X()] = true;
                attempts[pt.Y()][pt.X()] += ATTEMPTS / SUCCESS_LIMITER;
            }
            else if(chance <= .4)
            {
                map[pt.getEast().Y()][pt.getEast().X()] = true;
                attempts[pt.Y()][pt.X()] += ATTEMPTS / SUCCESS_LIMITER;
            }
            else if(chance <= .6)
            {
                map[pt.getSouth().Y()][pt.getSouth().X()] = true;
                attempts[pt.Y()][pt.X()] += ATTEMPTS / SUCCESS_LIMITER;
            }
            else if(chance <= .8)
            {
                map[pt.getWest().Y()][pt.getWest().X()] = true;
                attempts[pt.Y()][pt.X()] += ATTEMPTS / SUCCESS_LIMITER;
            }
            else
                attempts[pt.Y()][pt.X()] +=2;
        }

    }

    /**
     * Converts the boolean array into a Block array, and spawns chests, staircases, and the shop.
     */
    
    private void spawn()
    {
        double chance;
        bMap = new Block[map.length][map[0].length];
        monsterCount = 0;
        tileCount = 0;
        wallCount = 0;
        chestCount = 0;
        for (int r = 0; r < map.length; r++)
        {
            for (int c = 0; c < map[0].length; c++)
            {
                chance =  Math.random();
                Point temp = new Point ( r , c);
               
                if (map[r][c])
                {

                    if (chance >= .000 && chance < .0083)
                    {
                        if (chestCount < CHEST_MAX)
                        {
                            chestCount++;
                            bMap[r][c] = new Chest(temp, floor);
                        }
                        else bMap[r][c] = new Tile (temp );

                        tileCount++;
                    }
                    if (chance >= .0083)
                    {
                        bMap[r][c] = new Tile ( temp);
                        tileCount++;
                    }
                }
                else 
                {
                    bMap[r][c] = new Wall (temp);
                    wallCount++;
                }
                  if (c < 3 || r < 3 || c > map[0].length - 4 || r > map.length - 4) bMap[r][c] = new Wall(temp); //Creates a 3 wall thick border, preventing any OOB errors
            }
        }
        int rowChance = 0;
        int colChance = 0;
        while (bMap[rowChance][colChance].collides())
        { 
            rowChance = (int)( Math.random() * map.length);
            colChance = (int)( Math.random() * map[0].length);
        }
        bMap[rowChance][colChance] = new Staircase (new Point (rowChance, colChance) , false);
        if (floor == 3)((Staircase) bMap[rowChance][colChance]).setFinalBoss();

        /* 
        if (floor == 3)
        {
        bMap[rowChance][colChance] = new Staircase (new Point (rowChance, colChance) , true);
        }

        else */if (floor != 1)
        {
            while (bMap[rowChance][colChance].collides())
            { 
                rowChance = (int)( Math.random() * map.length);
                colChance = (int)( Math.random() * map[0].length);
            }
            bMap[rowChance][colChance] = new Staircase (new Point (rowChance, colChance) , true);

        }
        rowChance = 0;
        colChance = 0;
        while (bMap[rowChance][colChance].collides())
        { 
            rowChance = (int)( Math.random() * map.length);
            colChance = (int)( Math.random() * map[0].length);
        }
        bMap[rowChance][colChance] = new Shop (new Point (rowChance, colChance) , floor);

        for (int r = 0; r < bMap.length; r++)
        {
            for (int c = 0; c < bMap[0].length; c++)
            {
                if (!bMap[r][c].isWall() && (bMap[r][c]).collides())
                {
                    Point temp = new Point ( r , c);
                    bMap[r-1][c-1] = new Tile (new Point ( r-1 , c-1));
                    bMap[r-1][c] = new Tile (new Point ( r-1 , c));
                    bMap[r-1][c+1] = new Tile (new Point ( r-1 , c+1));
                    bMap[r][c-1] = new Tile (new Point ( r , c-1));
                    bMap[r][c+1] = new Tile (new Point ( r , c+1));
                    bMap[r+1][c-1] = new Tile (new Point ( r+1 , c-1));
                    bMap[r+1][c] = new Tile (new Point ( r+1 , c));
                    bMap[r+1][c+1] = new Tile (new Point ( r+1 , c+1));
                }
               
            }
        }

    }

    /**
     * Returns the Block array
     * @return the generated map of Blocks
     */
    
    public Block[][] getBlockMap()
    {
        return bMap;
    }

    
    public static void main(String[] args)
    {
        MapGenerator map = new MapGenerator(100,200 , 1);

        for (int i = 0; i < 30; i++)
        {
            System.out.println("\n\n MAP " + (i + 1) );
            map.generate();
            map.printInfo();
            map.print();
        }

    }
}