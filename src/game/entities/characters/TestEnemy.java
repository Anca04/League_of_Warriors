package game.entities.characters;

import game.spells.Spell;

public class TestEnemy {
    public static void main(String[] args) {
        Enemy enemy = new Enemy(100, 50);

        System.out.println("Health: " + enemy.getCurrentHealth());
        System.out.println("Mana: " + enemy.getCurrentMana());
        System.out.println("Immunities: Fire=" + enemy.isImmunityToFire() +
                ", Ice=" + enemy.isImmunityToIce() +
                ", Earth=" + enemy.isImmunityToEarth());

        System.out.println("Abilities:");
        for (Spell ability : enemy.getAbilities()) {
            System.out.println(" - " + ability.getName() + " (Type: " + ability.getType() +
                    ", Damage: " + ability.getDamage() +
                    ", Mana Cost: " + ability.getManaCost() + ")");
        }

        // simulate receiving damage
        System.out.println("\nSimulating damage...");
        enemy.receiveDamage(30);
        enemy.receiveDamage(50);

        // simulate attacking
        System.out.println("\nSimulating attack...");
        System.out.println("Enemy dealt " + enemy.getDamage() + " damage!");
        System.out.println("Enemy dealt " + enemy.getDamage() + " damage!");
    }
}
