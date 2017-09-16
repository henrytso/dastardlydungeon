
/**
 * Write a description of class HealthBar here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HealthBar
{
    PlayerInfo player;
    /**
     * Constructor for objects of class HealthBar, prints out a health bar
     */
    public HealthBar(PlayerInfo player)
    {
        this.player = player;
    }
    public void renderBar()
    {
        double x =(double) ( (double) (player.getCurrentHealth()) / (double) (player.getMaxHealth()));
        int a = (int) (20.0 * x);
        System.out.print("█");
        for (int i = 0; i < a-1; i++)
        System.out.print("█"); //filled
        for (int i = 0; i < (20 - a); i++)
        System.out.print("░"); //empty
    }
    
    public static void main(String[] args)
    {
        PlayerInfo player = new PlayerInfo();
        HealthBar hp = new HealthBar(player);
        hp.renderBar();
        player.setCurrentHealth(5);
        System.out.print("/t/t");
        hp.renderBar();
    }
}
