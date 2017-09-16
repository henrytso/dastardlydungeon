import java.util.Scanner;
/**
 * Write a description of class FinalBoss here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FinalBoss
{
    // instance variables - replace the example below with your own
    private PlayerInfo player;
    private MonsterInfo enemy;
    private MonsterInfo boss1;
    private MonsterInfo boss2;
    private boolean back = false;
    private final String BOSS_1_NAME = "Stevyoungus, Lord of the Burning Inferno";
    private final String BOSS_2_NAME = "TABERNAEUS THE SOUL REAPER";

    private boolean block = false;

    int bossTimer = 0;
    boolean timerReset;
    boolean win = false;

    /**
     * Constructor for objects of class FinalBoss
     */
    public FinalBoss(PlayerInfo pla)
    {
        this.player = pla;
        MonsterInfo boss1 = new MonsterInfo(4,player);
        MonsterInfo boss2 = new MonsterInfo(5,player);
       
       
        Runner2.nextFrame();
        System.out.println("YOU HAVE AWAKENED " + BOSS_1_NAME.toUpperCase() + " !");
        doBossCombat(boss1);
        Runner2.nextFrame();
        if (!(player.getCurrentHealth() < 1))
        {
            System.out.println("**The SHOPKEEPER approaches.");
            pause();
            Runner2.nextFrame();
            System.out.println("SHOPKEEPER: Well done, well done.");
             pause();
           Runner2.nextFrame();
            if(player.getCurrencyDiff() == 0)
            {
                System.out.println("SHOPKEEPER: Impressive. You managed to get this far without purchasing a single potion.");
                pause();
                Runner2.nextFrame();
            }
            else
            {
                System.out.println("SHOPKEEPER: I bet all those potions helped you. The Soulstones definitely helped me!");
                pause();
                Runner2.nextFrame();
            }
            System.out.println("SHOPKEEPER: Oh, and thank you for defeating the only monster that could stop me and my magic.");
            pause();
            Runner2.nextFrame();
            if (player.getCurrencyDiff() > 0)
            {
                System.out.println("SHOPKEEPER: Now, with the power of the Soulstones, I shall become..."); 
                pause();
                Runner2.nextFrame();
            }
            System.out.println("**The SHOPKEEPER transformed into " + BOSS_2_NAME.toUpperCase() +"!!! ");
            pause();
            Runner2.nextFrame();
            if (player.getCurrency() > 0)
            {
                System.out.println("**After being exposed to "+ BOSS_2_NAME +"'s power, the Soulstones in your pocket become active " );
                pause();
                Runner2.nextFrame();
                System.out.println("**Your max health was increased by "+player.getCurrency());
            }   
            player.setMaxHealth(player.getMaxHealth() + player.getCurrency());
            player.setCurrentHealth(player.getMaxHealth());
            boss2.setAttack((player.getMaxHealth() / 2));
            Combat.pause();
            doShopCombat(boss2);
            if (!(player.getCurrentHealth() < 1))
            {
                Runner2.nextFrame();
      
            }
            else
            {
                Runner2.nextFrame();
               
            }
        }
        else
        {
            Runner2.nextFrame();
            
        }

    }

    public void doBossCombat(MonsterInfo enemy)
    {
        boolean fight = true;
        this.enemy = enemy;
        int combatInput = 5;
        if (!playerFirst())
        {
            enemyTurn();
            Combat.pause();
        }
        fight = !(player.getCurrentHealth() < 1);
        while (fight)
        {
            do
            {
                back = false;
                Runner2.nextFrame();
                System.out.println("YOU");
                HealthBar playerBar = new HealthBar(player);
                playerBar.renderBar();
                System.out.println("[ " + player.getCurrentHealth() + " ]");
                System.out.println("   ");
                System.out.println(BOSS_1_NAME);
                HealthBar enemyBar = new HealthBar(enemy);
                enemyBar.renderBar();
                System.out.println("[" + enemy.getCurrentHealth() + "]");
                System.out.println("\nWhat would you like to do? (Inputs are numbers)");
                System.out.println("1. Attack");
                System.out.println("2. Potion");
                Runner2.printInterface();
                Scanner combatTemp = new Scanner(System.in);
                if (combatTemp.hasNextInt())
                    combatInput = combatTemp.nextInt(); 
                else
                    combatInput = 0;

                Runner2.nextFrame();

                switch (combatInput)
                {
                    case 1: attack();
                    break;
                    case 2: Inventory potions = player.getInventory().getPotions();
                    if (potions.size() == 0)
                    {
                        System.out.println("You have no potions!");

                        back = true;
                        pause();
                    }
                    else
                    {
                        System.out.println("Which potion would you like to use?");
                        System.out.println("0. Back");
                        potions.printPotions();
                        Runner2.printInterface();
                        Scanner potionTemp = new Scanner(System.in);
                        int potionInt; 
                        if (potionTemp.hasNextInt())
                            potionInt = potionTemp.nextInt(); 
                        else
                            potionInt = 100;
                        if (potionInt == 0)
                        {
                            back = true;
                        }
                        else
                        if (potionInt <= potions.size() && potionInt > 0)
                        {
                            player.consume(player.getInventory().get(potionInt-1));
                            System.out.println("You drank the potion");
                        }
                        else
                        {
                            System.out.println("INVALID INPUT");
                            pause();
                            back = true;
                        }
                    }
                    break;
                    default:
                    System.out.println("INVALID INPUT");
                    back = true;
                    pause();
                    break;
                }
            }
            while (back);
            if (enemy.getCurrentHealth() < 1)
            {
                Runner2.nextFrame();
                System.out.println("YOU HAVE SLAIN " + BOSS_1_NAME.toUpperCase() + "!");
                pause();
                Runner2.nextFrame();
                System.out.println("His final cry echos through the cavern.");
                pause();
                        Runner2.nextFrame();
                System.out.println(BOSS_1_NAME.toUpperCase() + " dropped the AEGIS OF MIGHT");
                pause();
                Runner2.nextFrame();
                System.out.println("You pick it up.");
                pause();
                Runner2.nextFrame();
                System.out.println("With Stephyongus's AEGIS OF MIGHT you can now DEFEND, blocking all incoming damage!");
                fight = false;
            }
            else
            {
                enemyTurn();
            }
            if (player.getCurrentHealth() < 1)
            {
                System.out.println("You have been slain.");
                fight = false;
            }
            pause();
        }
    }

    public void doShopCombat(MonsterInfo enemy)
    {
        boolean fight = true;
        this.enemy = enemy;
        int combatInput = 5;
        enemy.setCurrentHealth(enemy.getMaxHealth());
        fight = !(player.getCurrentHealth() < 1);
        bossTimer = 4;
        while (fight)
        {
            do
            {
                block = false;
                back = false;
                Runner2.nextFrame();
                System.out.println("YOU");
                HealthBar playerBar = new HealthBar(player);
                playerBar.renderBar();
                System.out.println("[" + player.getCurrentHealth() + " / " + player.getMaxHealth() + "]");
                System.out.println("   ");
                System.out.println(BOSS_2_NAME.toUpperCase());
                HealthBar enemyBar = new HealthBar(enemy);             
                enemyBar.renderBar();
                System.out.println("[" + enemy.getCurrentHealth() + " / " + enemy.getMaxHealth() + "]");
                System.out.println("\nWhat would you like to do? (Inputs are numbers)");
                System.out.println("1. Attack");
                System.out.println("2. Potion");
                System.out.println("3. DEFEND");
                Runner2.printInterface();
                Scanner combatTemp = new Scanner(System.in);
                if (combatTemp.hasNextInt())
                    combatInput = combatTemp.nextInt(); 
                else
                    combatInput = 0;

                Runner2.nextFrame();

                switch (combatInput)
                {
                    case 1: attack();
                    break;
                    case 2: Inventory potions = player.getInventory().getPotions();
                    if (potions.size() == 0)
                    {
                        System.out.println("You have no potions!");

                        back = true;
                        pause();
                    }
                    else
                    {
                        System.out.println("Which potion would you like to use?");
                        System.out.println("0. Back");
                        potions.printPotions();
                        Runner2.printInterface();
                        Scanner potionTemp = new Scanner(System.in);
                        int potionInt; 
                        if (potionTemp.hasNextInt())
                            potionInt = potionTemp.nextInt(); 
                        else
                            potionInt = 100;
                        if (potionInt == 0)
                        {
                            back = true;
                        }
                        else
                        if (potionInt <= potions.size() && potionInt > 0)
                        {
                            player.consume(player.getInventory().get(potionInt-1));
                            System.out.println("You drank the potion");
                        }
                        else
                        {
                            System.out.println("INVALID INPUT");
                            pause();
                            back = true;
                        }
                    }
                    break;
                    case 3: 
                    System.out.println("Filled with hope, you raise your sheild.");
                    block = true;
                    break;
                    default:
                    System.out.println("INVALID INPUT");
                    back = true;
                    pause();
                    break;
                }
            }
            while (back);
            if (enemy.getCurrentHealth() < 1)
            {
                Runner2.nextFrame();
                System.out.println("You slayed " + BOSS_2_NAME +"!");
                fight = false;
            }
            else
            {
                bossTurn();
            }
            if (player.getCurrentHealth() < 1)
            {
                System.out.println(BOSS_2_NAME+": HA HA HA HA! I KNEW YOU WERE NO MATCH FOR ME!!");
                fight = false;
            }
            pause();
            block = false;
        }
    }

    private void attack()
    {        
        int x = enemy.getCurrentHealth() - player.getAttack() + enemy.getArmor();
        if (player.luckRoll(100))
        {
            x = enemy.getCurrentHealth() - player.getAttack() - player.getAttack();
            //ignores armor and deals double damage
            enemy.setCurrentHealth(x);
            System.out.println("YOU LANDED CRITICAL STRIKE!");
        }
        else
        if (x >= enemy.getCurrentHealth())
        {
            System.out.println("YOUR ATTACK WAS INEFFECTIVE!"); 
        }
        else
        {
            enemy.setCurrentHealth(x);
            System.out.println("You managed to land a hit!"); 
        }
        if (player.hasEquipWeapon() && player.getEquipWeapon().getModifier() == 5)
        {
            System.out.println("Your weapon's poison effect dealt " + player.getEquipWeapon().getEffectFactor()+" damage to the enemy!"); 
            enemy.setCurrentHealth(enemy.getCurrentHealth() - player.getEquipWeapon().getEffectFactor());
        }
        if (player.hasEquipWeapon() && player.getEquipWeapon().getModifier() == 6)
        {
            System.out.println("Your weapon's vampiric effect healed you for " + player.getEquipWeapon().getEffectFactor() + " health!"); 
            player.setCurrentHealth(player.getCurrentHealth() + player.getEquipWeapon().getEffectFactor());
        }
    }

    private void enemyTurn()
    {
        int x = 0;
        if (player.hasEquipShield())
        {
            double y = player.getEquipShield().doEffect();
            x = player.getCurrentHealth() - (int) (y * (double) enemy.getAttack()) + player.getArmor();
            if (y!=1)
                System.out.println("You were able to block some of the enemy's damage with your shield!");
        }
        else
            x = player.getCurrentHealth() - enemy.getAttack() + player.getArmor();
        if (enemy.luckRoll(100))
        {
            x = player.getCurrentHealth() - enemy.getAttack() - enemy.getAttack();
            player.setCurrentHealth(x);
            System.out.println("THE ENEMY LANDED CRITICAL STRIKE!");
        }
        else
        if (x >= player.getCurrentHealth())
        {
            System.out.println("THE ENEMY'S ATTACK WAS INEFFECTIVE!"); 
        }
        else
        {
            player.setCurrentHealth(x);
            System.out.println("The enemy landed a hit"); 
        }
        if (player.hasEquipArmor() && player.getEquipArmor().getModifier() == 5)
        {
            System.out.println("Your weapon's thorned effect damaged the enemy"); 
            enemy.setCurrentHealth(enemy.getCurrentHealth() - player.getEquipArmor().getEffectFactor());
        }
        if (player.hasEquipArmor() && player.getEquipArmor().getModifier() == 6)
        {
            System.out.println("Your armor's holy effect healed you a bit"); 
            player.setCurrentHealth(player.getCurrentHealth() + player.getEquipArmor().getEffectFactor());
        }
    }

    private void bossTurn()
    {

        if (bossTimer == 0)
        {
            int x = 0;
            if (block)
            {
                System.out.println(BOSS_2_NAME+"'s attack threw you off balance, but you managed to block all incoming damage.");
               
            }
            else{
           
                    x = player.getCurrentHealth() - enemy.getAttack();
                    
                if (enemy.luckRoll(100))
                {
                    x = player.getCurrentHealth() - enemy.getAttack() - enemy.getAttack();
                    player.setCurrentHealth(x);
                    System.out.println("THE ENEMY LANDED CRITICAL STRIKE!");
                    Runner2.printInterface();
                    pause();
                }
                else
                if (x >= player.getCurrentHealth())
                {
                    System.out.println("THE ENEMY'S ATTACK WAS INEFFECTIVE!"); 
                    Runner2.printInterface();
                    pause();
                }
                else
                {
                    player.setCurrentHealth(x);
                    System.out.println("You were struck with the force of one thousand suns!"); 
                    Runner2.printInterface();
                }
                if (player.hasEquipArmor() && player.getEquipArmor().getModifier() == 5)
                {
                    System.out.println("Your armor's thorned effect damaged the enemy"+ player.getEquipArmor().getEffectFactor()+"!"); 
                    enemy.setCurrentHealth(enemy.getCurrentHealth() - player.getEquipArmor().getEffectFactor());
                }
                if (player.hasEquipArmor() && player.getEquipArmor().getModifier() == 6)
                {
                    System.out.println("Your armor's holy effect healed for "+ player.getEquipArmor().getEffectFactor()+"!"); 
                    player.setCurrentHealth(player.getCurrentHealth() + player.getEquipArmor().getEffectFactor());
                }
            }
            bossTimer = (int) (Math.random() * 3) + 1;
            timerReset = true;

        }
        else
        {
            if (timerReset)
            {
                timerReset = false;
                if (bossTimer == 1)
                    System.out.println(BOSS_2_NAME + ": I WILL CRUSH YOU!!");
                if (bossTimer == 2)
                    System.out.println(BOSS_2_NAME + ": YOU'RE GOING TO BE VERY SORRY, JUST YOU WAIT!!!");
                if (bossTimer == 3)
                    System.out.println(BOSS_2_NAME + ": NO GOOD FOURTUNE SHALL COME TO YOU! HAHAHA!!!!");

            }
            else
                bossTimer--;

            System.out.println(BOSS_2_NAME + " is charging an attack!");

        }
    }

    /**
     * returns true if the player goes first
     */
    private boolean playerFirst()
    {
        return enemy.getSpeed() <= player.getCombatSpeed();
    }

    public static void pause()
    {
        System.out.println("Press ENTER to continue");
        Scanner seeAction = new Scanner(System.in);
        String pause = seeAction.nextLine();
    }

    public static void main(String[] args)
    {
        PlayerInfo player = new PlayerInfo();
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
        player.addCurrency(1000);
        player.setCurrency(25);
        System.out.println(player.getCurrencyDiff());
        FinalBoss x = new FinalBoss(player);
    }
}
