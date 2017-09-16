import java.util.Scanner;
public class Menu
{
    final String[] OPTION = new String[5];
    PlayerInfo player;
    public Menu()
    {
        OPTION[0] = "0. RESUME";
        OPTION[1] = "1. CHARACTER STATS";
        OPTION[2] = "2. USE ITEM";
        OPTION[3] = "3. DISCARD ITEM";
        OPTION[4] = "4. QUIT GAME";
    }

    public boolean invoke(PlayerInfo player)
    {
        this.player = player;
        int input;
        boolean game = true;
        boolean loop = true;

        do
        {
            Runner2.nextFrame();
            System.out.println("Choose an option:");
            for (int i = 0; i < OPTION.length; i++)
                System.out.println(OPTION[i]);
            Runner2.printInterface();
            Scanner in = new Scanner(System.in);
            input = -1;
            if (in.hasNextInt())
                input = in.nextInt();
            switch (input)
            {
                case 0: game = true;
                loop = false;
                break;
                case 1: stats();
                break;
                case 2: item();
                break;
                case 3: disItem();
                break;
                case 4: 
                System.out.println("Are you sure you want to quit?");
                System.out.println("0. No");
                System.out.println("1. Yes");
                Scanner in2 = new Scanner(System.in);
                boolean r = false;
                if (in2.hasNextInt())
                r = (in2.nextInt() == 1);
                if (r)
                {
                    System.out.println("You have quit the game");
                    game = false;
                    loop = false;
                }
                else
                {
                    System.out.println("You did not quit");
                }
                break;
                default:
                break;
            }
          //  Combat.pause();
        } while (loop);

        return game;
    }

    public void stats()
    {
        player.printStats();
        Combat.pause();
    }
    
    public void disItem()
    {
        while (true)
        {
            Runner2.nextFrame();
            System.out.println("Which item would you like to Discard?");
            if (player.getInventory().size() > 0)
            {  
                System.out.println("Inventory [" + player.getInventory().size() + "/9]");
                System.out.println("0. Exit");
                for (int i = 0; i < player.getInventory().size(); i++)
                {
                    System.out.println((i + 1) + ". " + player.getInventory().get(i).toString());
                }
                for (int i = 0; i <  (9 - player.getInventory().size()); i++)
                {
                    System.out.println((i + player.getInventory().size() + 1) + ". ------------");
                }
            }
            else
            {
                System.out.println("Inventory [0/9]");
                System.out.println("0. Exit");
                System.out.println("1. ------------");
                System.out.println("2. ------------");
                System.out.println("3. ------------");
                System.out.println("4. ------------");
                System.out.println("5. ------------");
                System.out.println("6. ------------");
                System.out.println("7. ------------");
                System.out.println("8. ------------");
                System.out.println("9. ------------");
            }
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt())
            {
                int y = input.nextInt();
                if  (y <= player.getInventory().size() && y > 0)
                {
                    System.out.println("Are you sure you want to discard " + player.getInventory().get(y-1).toString());
                    System.out.println("0. No");
                    System.out.println("1. Yes");
                    Scanner in2 = new Scanner(System.in);
                    boolean r = false;
                    if (in2.hasNextInt())
                    r = (in2.nextInt() == 1);
                    if (r)
                    {
                    System.out.println("You have discarded" + player.getInventory().get(y-1).toString());
                    player.getInventory().removeItem(player.getInventory().get(y-1));
                    }
                    else
                    {
                        System.out.println("You did not discard " + player.getInventory().get(y-1).toString());
                    }
                }
                else
                if (y == 0)
                    break;
                else
                    System.out.println("INVALID INPUT");
           }
            else
            {  
                System.out.println("INVALID INPUT");
            }
          // Combat.pause();
        }
    }

    public void item()
    {
        while (true)
        {
            Runner2.nextFrame();
            System.out.println("Which item would you like to use?");
            if (player.getInventory().size() > 0)
            {  
                System.out.println("Inventory [" + player.getInventory().size() + "/9]");
                System.out.println("0. Exit");
                for (int i = 0; i < player.getInventory().size(); i++)
                {
                    System.out.println((i + 1) + ". " + player.getInventory().get(i).toString());
                }
                for (int i = 0; i <  (9 - player.getInventory().size()); i++)
                {
                    System.out.println((i + player.getInventory().size() + 1) + ". ------------");
                }
            }
            else
            {
                System.out.println("Inventory [0/9]");
                System.out.println("0. Exit");
                System.out.println("1. ------------");
                System.out.println("2. ------------");
                System.out.println("3. ------------");
                System.out.println("4. ------------");
                System.out.println("5. ------------");
                System.out.println("6. ------------");
                System.out.println("7. ------------");
                System.out.println("8. ------------");
                System.out.println("9. ------------");
            }
            Scanner input = new Scanner(System.in);
            if (input.hasNextInt())
            {
                int y = input.nextInt();
                if  (y <= player.getInventory().size() && y > 0)
                    if (player.getInventory().get(y-1).getID() < 3000)
                    {
                        System.out.println("You drank a " + player.getInventory().get(y-1));
                        player.consume(player.getInventory().get(y-1));
                    }
                    else
                    {
                        System.out.println("You have equipped a " + player.getInventory().get(y-1));
                        player.equip(player.getInventory().get(y-1));
                    }
                else
                if (y == 0)
                    break;
                else
                    System.out.println("INVALID INPUT");
            }
            else
            {  
                System.out.println("INVALID INPUT");
            }
           // Combat.pause();
        }
    }
    
    public static void main(String[] args)
    {
        PlayerInfo x = new PlayerInfo();
        x.getInventory().add(new Potion(1));
        x.getInventory().add(new Potion(1));
        x.getInventory().add(new Potion(1));
        x.getInventory().add(new Weapon(1));
        x.getInventory().add(new Armor(1));
        x.getInventory().add(new Armor(1));
        x.getInventory().add(new Shield(1));
        x.getInventory().add(new Shield(1));
        Menu p = new Menu();
        boolean u = p.invoke(x);
    }
}