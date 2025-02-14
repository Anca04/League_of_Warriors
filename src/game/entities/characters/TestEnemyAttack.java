package game.entities.characters;

public class TestEnemyAttack {
    public static void main(String[] args) {
        // create character
        Warrior player = new Warrior("Test Warrior", 50, 1);

        // create enemy
        Enemy enemy = new Enemy(50, 20);

        // simulate attacks
        for (int i = 0; i < 5; i++) {
            System.out.println("\nTurn " + (i + 1) + ":");
            enemy.attack(player);
            System.out.println("Player Health: " + player.getCurrentHealth());
            System.out.println("Enemy Mana: " + enemy.getCurrentMana());
        }
    }
}
