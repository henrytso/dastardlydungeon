import java.util.Scanner;
public class Runner2
{
    public static void main(String[] args)
    {

        //The player's current floor
        int currentFloor;

        //Controls
        String north = ",";
        String east = "e";
        String south = "o";
        String west = "a";

        //Checks why game has ended
        boolean quit = false;

        //Handles input
        String input;
        Scanner in = new Scanner(System.in);

        //Version of the project
        final String VERSION = "F 1.1";

        //Class declarations
        Combat battle;
        Menu menu;
        MapGenerator mapGen;
        FinalBoss finalBoss;

        while (true){

            menu = new Menu();
            currentFloor = 0;
            Room[] floor = new Room[3];
            //Generates 3 random maps
            for (int i = 0; i < 3; i++)
            {
                mapGen = new MapGenerator( 50, 200 , i + 1);
                mapGen.generate();
                floor[i] = new Room(mapGen.getBlockMap(), i + 1);

            }
            boolean alreadyInCombat = false;
            battle = new Combat();

            Player p = new Player (floor[0].getSpawn());
            PlayerInfo player = new PlayerInfo();

            floor[currentFloor].spawnPlayer(p.loc());
            System.out.println("Version " + VERSION);
            Intro intro = new Intro();
            String name = intro.getIntro();
            System.out.println("Press enter to continue...");

            //Cheatcode
            if (name.equals("KHAIRULSUX"))
            {
                player.setExperience(4096);
                player.levelUp();
                player.equip(new Weapon(3));
                player.equip(new Armor(3));
                player.equip(new Shield(3));
                player.getInventory().add(new Potion(3));
                player.getInventory().add(new Potion(3));
                player.getInventory().add(new Potion(3));
                player.getInventory().add(new Potion(3));
                player.getInventory().add(new Potion(3));
                player.getInventory().add(new Potion(3));
            }

            while(player.getCurrentHealth() > 1)
            {
                input = in.nextLine();
                input = input.toLowerCase();
                p.loadMap(floor[currentFloor].map());

                //if Menu is invoked
                if (input.equals("m")) 
                {

                    if( !menu.invoke(player))
                    {
                        quit = true;
                        break;
                    }
                    p.outOfChest();
                }

                //if Pinged
                if (input.equals("p"))
                {
                    floor[currentFloor].ping();

                }

                //gets direction
                if (input.equals(north))
                {
                    p.setDir("N");
                }
                if (input.equals(east))
                {
                    p.setDir("E");
                }
                if (input.equals(south))
                {
                    p.setDir("S");
                }
                if (input.equals(west))
                {
                    p.setDir("W");
                }

                input = ""; // allows changing direction and moving on the same turn

                //Ends the game. For dev use only.
                /*
                if (input.equals("n"))                                             
                break;
                 */

                //Attempts to step the player forwards
                if (input.equals(""))                                                      
                {
                    p.move();
                    //Checks if the player has invoked combat with a monster
                    if(p.inCombat() && !alreadyInCombat)
                    {
                        alreadyInCombat = true;
                        nextFrame();
                        Monster temp =  (Monster)( floor[currentFloor].getBlock(p.monster()));
                        MonsterInfo tempInfo = temp.info(); 
                        battle.doCombat(player, tempInfo);     
                        player.setCurrentHealth(battle.getPlayer().getCurrentHealth());
                        tempInfo.setCurrentHealth(battle.getEnemy().getCurrentHealth());
                        temp.update();
                        p.outOfCombat();
                        if (player.getCurrentHealth() < 1)
                        {
                            break;
                        }

                    }

                    //Checks if a player has invoked a chest.
                    if(p.inChest())
                    {
                        nextFrame();
                        Chest temp = (Chest) (floor[currentFloor].getBlock(p.chest()));
                        temp.activate(player);
                        p.outOfChest();
                        nextFrame();

                    }

                    //Checks if player has invoked the shop
                    if(p.inShop())
                    {
                        nextFrame();
                        Shop temp = (Shop) (floor[currentFloor].getBlock(p.shop()));
                        temp.activate(player);
                        p.outOfShop();
                        nextFrame();
                    }

                    //Checks if player has invoked a Staircase
                    if(p.atStaircase)
                    {
                        nextFrame();
                        Staircase temp = (Staircase) (floor[currentFloor].getBlock(p.staircase()));

                        //Invokes final boss battle
                        if (temp.isFinalBoss())
                        {
                            finalBoss = new FinalBoss(player);
                            break;
                        }

                        if (temp.up())
                        {
                            currentFloor--;
                            p.setLoc(floor[currentFloor].getStaircaseSpawn(true));
                        }
                        else 
                        {
                            currentFloor++;
                            p.setLoc(floor[currentFloor].getStaircaseSpawn(false));
                        }
                        p.notAtStaircase();
                        p.setLoc(floor[currentFloor].playerLoc());
                    }

                    //Checks if Monster has invoked combat with the player.
                    if (floor[currentFloor].inCombat() && !alreadyInCombat)
                    {

                        nextFrame();
                        Monster temp = floor[currentFloor].getMonster();
                        MonsterInfo tempInfo = temp.info(); 
                        battle.doCombat(player, tempInfo);     
                        player.setCurrentHealth(battle.getPlayer().getCurrentHealth());
                        tempInfo.setCurrentHealth(battle.getEnemy().getCurrentHealth());
                        temp.update();
                        p.outOfCombat();
                        if (player.getCurrentHealth() < 1)
                        {
                            break;
                        }

                    }
                    alreadyInCombat = false; //makes sure combat can only be invoked once per turn.
                    floor[currentFloor].updateMap(); //updates map with player and monster movements

                    p.loadMap( floor[currentFloor].map()); // loads updated map to player

                    nextFrame();    
                    floor[currentFloor].print();
                }

                printInterface();
            }        

            //If player died
            if (player.getCurrentHealth() < 1)
            {
                intro.printGrave();
                player.setCurrentHealth(0);

            }

            //If player quit
            if (quit)
            {
                quit = false;
                player.setCurrentHealth(0);

            }

            //If player won
            if (player.getCurrentHealth() > 1)
            {
                player.setCurrentHealth(0);
                intro.end();

            }

            //Allows for a second play
            if (quitMenu()) 
            {
                System.out.println("Thank you for playing!");
                break;
            }

        }
    }

    /**
     * Allows the player to play again or quit the game.
     * @return  boolean indicating if the game has been quit or not.
     */
    public static boolean quitMenu()
    {
        Scanner in = new Scanner(System.in);

        String input = "";
        while (!input.equals("y") || !input.equals("n"))
        {
            System.out.println("Would you like to play agaian? (y / n)");
            input = in.nextLine();
            if ( input.equals("y"))
            {
                return false;
            }
            if (input.equals("n"))
            {

                return true;
            }
        }
        return true;

    }

    /**
     * Prints the interfaced used when inputting. 
     */
    public static void printInterface()
    {
        System.out.print(">> ");
    }

    /**
     * Clears the terminal.
     */
    public static void nextFrame()
    {
        for (int i = 0; i < 60; i++) System.out.println();
    }

    
    

    
}
