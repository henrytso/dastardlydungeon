import java.util.Scanner;
/**
 * Write a description of class Combat here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Combat
{
    private boolean fight;
    private boolean back = false;
    private boolean run = false;
    private PlayerInfo player;
    private MonsterInfo enemy;
    private String name;

    public void doCombat(PlayerInfo player, MonsterInfo enemy)
    {
        name = enemy.getName();
        fight = true;
        this.player = player;
        this.enemy = enemy;
        int combatInput = 5;
        if (!playerFirst())

            enemyTurn();
        fight = !(player.getCurrentHealth() < 1);
        while (fight && player.getCurrentHealth() > 0 && enemy.getCurrentHealth() > 0)
        {
            do
            {
                back = false;
                run = false;
                Runner2.nextFrame();
                System.out.println("YOU");
                HealthBar playerBar = new HealthBar(player);
                playerBar.renderBar();
                System.out.println("[" + player.getCurrentHealth() + " / " + player.getMaxHealth() + "]");
                System.out.println("   ");
                System.out.println(name);
                HealthBar enemyBar = new HealthBar(enemy);
                enemyBar.renderBar();
                System.out.println("[" + enemy.getCurrentHealth() + " / " + enemy.getMaxHealth() + "]");
                System.out.println("\nWhat would you like to do? (Inputs are numbers)");
                System.out.println("1. Attack");
                System.out.println("2. Potion");
                System.out.println("3. Run");
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
                            pause();
                            if (player.hasEquipWeapon() && player.getEquipWeapon().getModifier() == 5)
                            {
                                System.out.println("Your weapon's poison effect dealt " + player.getEquipWeapon().getEffectFactor() + " damage to the enemy"); 
                                enemy.setCurrentHealth(enemy.getCurrentHealth() - player.getEquipWeapon().getEffectFactor());
                                pause();
                            }

                            if (player.hasEquipArmor() && player.getEquipArmor().getModifier() == 6)
                            {
                                System.out.println("Your armor's holy effect healed you for " +  player.getEquipArmor().getEffectFactor() +  " health."); 
                                player.setCurrentHealth(player.getCurrentHealth() + player.getEquipArmor().getEffectFactor());
                                pause();
                            }
                        }
                        else
                        {
                            System.out.println("INVALID INPUT");
                            pause();
                            back = true;
                        }
                    }
                    break;
                    case 3: fight = ((Math.random()*enemy.getSpeed()) > player.getSpeed());
                    run = true;
                    break;
                    default:
                    System.out.println("INVALID INPUT");
                    back = true;
                    pause();
                    break;
                }
            }
            while (back);

            if (player.getCurrentHealth() < 1)
            {
                System.out.println("You have been slain.");
                break;
            }

            if (run && !fight)
            {
                System.out.println("You have escaped");
                pause();
                break;
            }
            else
            if (run && fight)
            {
                System.out.println("You failed to escape!");
                pause();
                Runner2.nextFrame();
				if (player.hasEquipWeapon() && player.getEquipWeapon().getModifier() == 5 && enemy.getCurrentHealth() < enemy.getMaxHealth())
				{
					System.out.println("Your weapon's poison effect dealt " + player.getEquipWeapon().getEffectFactor() + " damage to the enemy"); 
					enemy.setCurrentHealth(enemy.getCurrentHealth() - player.getEquipWeapon().getEffectFactor());
					pause();
                }
                enemyTurn();
            }
            else
            if (enemy.getCurrentHealth() < 1)
            {
                int gainedExperience = enemy.getExperience() + (int) (Math.random() * 4);
                player.setExperience(player.getExperience() + gainedExperience);
                Runner2.nextFrame();
                System.out.println("You have killed "+ name + "!");
                System.out.println("You have gained some experience!");
                int moneyDrop = ((enemy.getTier()+1)*150)/4;
                System.out.println("You have found " + moneyDrop + " Soulstones!");
                player.addCurrency (moneyDrop);
                pause();
                player.levelUp();
                Chest carcass = new Chest(new Point(-1,-1) , enemy.getTier());
                player.setInventory(carcass.activate(player, enemy));
                pause();
                break;
                //fight = false;
            }
            else
            {
                run = false;
                enemyTurn();
            }

            if (player.getCurrentHealth() < 1)
            {
                System.out.println("You have been slain.");
                fight = false;
            }
        }
    }

    public PlayerInfo getPlayer()
    {
        return player;
    }

    public MonsterInfo getEnemy()
    {
        return enemy;
    }

    private void attack()
    {        
        int x = enemy.getCurrentHealth() - player.getAttack() + enemy.getArmor();
        if (player.luckRoll(100))
        {
            x = enemy.getCurrentHealth() - player.getAttack() - player.getAttack();
            //ignores armor and deals double damage
            enemy.setCurrentHealth(x);
            System.out.println("YOU LANDED A CRITICAL STRIKE, DEALING "+ (2 * player.getAttack()) + " DAMAGE!");
        }
        else
        if (x >= enemy.getCurrentHealth())
        {
            System.out.println("YOUR ATTACK WAS INEFFECTIVE!"); 
        }
        else
        {
            enemy.setCurrentHealth(x);
            System.out.println("You landed a hit, dealing " + (player.getAttack() - enemy.getArmor()) + " damage!"); 
        }
        if (player.hasEquipWeapon() && player.getEquipWeapon().getModifier() == 5)
        {
            System.out.println("Your weapon's poison effect dealt" + player.getEquipWeapon().getEffectFactor() + " damage to the enemy"); 
            enemy.setCurrentHealth(enemy.getCurrentHealth() - player.getEquipWeapon().getEffectFactor());
        }
        if (player.hasEquipWeapon() && player.getEquipWeapon().getModifier() == 6)
        {
            System.out.println("Your weapon's vampiric effect healed you for "+ player.getEquipWeapon().getEffectFactor() +" health!"); 
            player.setCurrentHealth(player.getCurrentHealth() + player.getEquipWeapon().getEffectFactor());
        }
        if (player.hasEquipArmor() && player.getEquipArmor().getModifier() == 6)
        {
            System.out.println("Your armor's holy effect healed you for " +  player.getEquipArmor().getEffectFactor() +  " health."); 
            player.setCurrentHealth(player.getCurrentHealth() + player.getEquipArmor().getEffectFactor());
            pause();
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
                System.out.println("THE ENEMY LANDED A CRITICAL STRIKE, DEALING " + (2*enemy.getAttack()) + " DAMAGE!");
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
                System.out.println("The enemy landed a hit, dealing " + (enemy.getAttack() - player.getArmor()) +" damage!"); 
                Runner2.printInterface();
                pause();
            }
            if (player.hasEquipArmor() && player.getEquipArmor().getModifier() == 5)
            {
                System.out.println("Your armor's thorned effect damaged the enemy for " +  player.getEquipArmor().getEffectFactor() + " damage."); 
                enemy.setCurrentHealth(enemy.getCurrentHealth() - player.getEquipArmor().getEffectFactor());
                pause();
            }
            if (player.hasEquipArmor() && player.getEquipArmor().getModifier() == 6)
            {
                System.out.println("Your armor's holy effect healed you for " +  player.getEquipArmor().getEffectFactor() +  " health."); 
                player.setCurrentHealth(player.getCurrentHealth() + player.getEquipArmor().getEffectFactor());
                pause();
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
    }
