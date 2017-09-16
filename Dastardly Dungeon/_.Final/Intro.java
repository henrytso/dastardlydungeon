import java.util.Scanner;
/**
 * Handles the game's intro and ending graphics
 */
public class Intro
{
    String flame1 = "''wWw''";
    String flame2 = ".'WwW'.";
    String pFlame = "";
    String name;
    boolean flame = true;
    public Intro(){}

    /**
     * Prints the introduction to the game
     * @return the player's name.
     */
    public String getIntro()
    {

        Scanner in = new Scanner(System.in);
  
        printTorch("WELCOME TO DASTARDLY DUNGEON");
        
        System.out.println();
        String name = "";
        while(name.length() > 10 || name.length() < 1)
        {
            System.out.println("Please type your name below: (Max 10 characters)");
            name = in.nextLine();
        }
        this.name  = name;
        Runner2.nextFrame();

        printTorch("You have been chosen to rid the dungeon of its evil.");
        printTorch("Its curse dates back to the ancient times.");
        printTorch("Two brothers, Stevyoungus and Tabernaeus");
        printTorch("Fought for control of the dungeon");
        printTorch("Both brothers yearned to harness its dark power");
        printTorch("In the end, only one survived, Stevyoungus");
        printTorch("For centuries he has harvested evil power. ");
        printTorch("The prophecies told of a hero that would bring his downfall");
        printTorch("The hero's name");
        printTorch(name);
        return name;
    }   
    
    /**
     * Prints the win screen
     */
    
    public void end()
    {
        printTorch("CONGRATULATIONS! you have defeated the Dastardly Brothers,");
        printTorch("ending their centuries long feud!");
        printTorch("With their demise, evil has been permanently banished from the dungeon");
        printTorch("Go forth, "+name+", and tell your tale to all who will listen.");
        printTorch("Thank You.");

    }
    
    /**
     * Given a string, formats it to fit on the torch ASCII art, then prints it.
     * @param Message to be printed
     */
    
    private void printTorch(String message)
    {
        String[] words = message.split("\\s");
        String[] line = new String[5];
        line[1] = "";
        line[2] = "";
        line[3] = "";
        line[0] = "";
        line[4] = "";
        int counter = 0;
        
        

        for (int i = 0; i < 5; i++)
        {
            while (counter < words.length && (line[i] + " " + words[counter]).length()<= 17)
               {
                   line[i] =line[i] + " " + words[counter];
                counter++;
            }
                
        }

        for (int i = 0; i < 5; i++)
        {
            String dots = "";
            for (int j = 0; j < 17 - line[i].length(); j++)
                dots += ".";
            line[i] = dots.substring(0, dots.length() / 2) + line[i] + dots.substring(dots.length() / 2);
            if (line[i].length() < 17) line[i] += ".";
        }
        
        flame = !flame;
        
        if (flame) pFlame = flame1;
        else pFlame = flame2;
        System.out.println("......................................");
        System.out.println("..."+pFlame + line[0] +pFlame+"...");
        System.out.println("....'\\|/'."+line[1]+".'\\|/'....");
        System.out.println("......|..."+line[2]+"...|......");
        System.out.println("......U..."+line[3]+"...U......");
        System.out.println(".........."+line[4]+"..........");
        System.out.println("......................................");
        Combat.pause();
        Runner2.nextFrame();

    }

    /**
     * Prints the loss screen
     */
    
    public void printGrave()
    {
        System.out.println("......_______......");
        System.out.println("..../........\\.....");
        System.out.println(".../....RIP...\\....");
        System.out.print("..|.");
        for (int i = 0; i < ((10-name.length())+1)/2; i++)
            System.out.print(".");
        System.out.print(name);
        for (int i = 0; i < (10-name.length())/2; i++)
            System.out.print(".");
        System.out.println(".|...");
        System.out.println("..|.....||.....|...");
        System.out.println("..|....=||=....|...");
        System.out.println("..|.....||.....|...");
        System.out.println("..|.....||.....|...");
    }
}
