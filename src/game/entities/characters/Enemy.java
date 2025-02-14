package game.entities.characters;

import game.spells.Earth;
import game.spells.Fire;
import game.spells.Ice;
import game.spells.Spell;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {
    private int health;
    private int mana;
    private int damage;
    private boolean immunityFire;
    private boolean immunityIce;
    private boolean immunityEarth;
    private ArrayList<Spell> abilities;

    // Constructor
    public Enemy(int maxHealth, int maxMana) {
        super(maxHealth, maxMana, false, false, false);
        Random random = new Random();
        health = random.nextInt(maxHealth) + 50;
        mana = random.nextInt(maxMana) + 50;
        currentMana = mana;
        currentHealth = health;
        damage = random.nextInt(21) + 10;
        this.immunityFire = random.nextBoolean();
        this.immunityIce = random.nextBoolean();
        this.immunityEarth = random.nextBoolean();
        this.abilities = new ArrayList<>();
        // 3 to 6 abilities
        int numAbilities = random.nextInt(3) + 1;
        generateRandomAbilities(numAbilities);
        this.setAbilities(abilities);
    }

    // generate the abilities for enemy
    public void generateRandomAbilities(int numAbilities) {
        Random random = new Random();
        abilities.add(0, new Fire("Fire", random.nextInt(30) + 10, random.nextInt(20) + 10, "Fire"));
        abilities.add(1, new Ice("Ice", random.nextInt(20) + 10, random.nextInt(15) + 10, "Ice"));
        abilities.add(2, new Earth("Earth", random.nextInt(40) + 20, random.nextInt(25) + 15, "Earth"));
        for (int i = 0; i < numAbilities; i++) {
            // assume 0 - Fire, 1 - Ice, 2 - Earth
            int abilityType = random.nextInt(3);
            switch (abilityType) {
                case 0:
                    abilities.add(new Fire("Fire", random.nextInt(30) + 10, random.nextInt(20) + 10, "Fire"));
                    break;
                case 1:
                    abilities.add(new Ice("Ice", random.nextInt(20) + 10, random.nextInt(15) + 10, "Ice"));
                    break;
                case 2:
                    abilities.add(new Earth("Earth", random.nextInt(40) + 20, random.nextInt(25) + 15, "Earth"));
                    break;
                default:
                    System.out.println("Invalid Ability Type");
            }
        }
    }

    @Override
    public void receiveDamage(int damage) {
        Random random = new Random();
        // 50% chance to avoid damage
        if (random.nextBoolean()) {
            System.out.println("Enemy avoided the attack!");
        } else {
            this.health -= damage;
            if (health <= 0) {
                health = 0;
            } else {
                System.out.println("Enemy received " + damage + " damage, remaining health: " + this.health);
            }
        }
    }

    @Override
    public int getDamage() {
        Random random = new Random();
        int damage = random.nextInt(10) + 5;
        // 50% chance to double damage
        if (random.nextBoolean()) {
            return damage * 2;
        }
        return damage;
    }

    @Override
    public boolean isImmunityToFire() {
        return this.immunityFire;
    }

    @Override
    public boolean isImmunityToIce() {
        return this.immunityIce;
    }

    @Override
    public boolean isImmunityToEarth() {
        return this.immunityEarth;
    }

    public int getCurrentHealth() {
        return health;
    }

    public void setCurrentHealth(int health) {
        this.health = health;
    }

    @Override
    public void setCurrentMana(int mana) {
        this.mana = mana;
    }

    @Override
    public int getCurrentMana() {
        return mana;
    }

    public ArrayList<Spell> getAbilities() {
        return this.abilities;
    }

    public void setAbilitiesEnemy(ArrayList<Spell> abilities) {
        this.abilities = abilities;
    }

    public void attack(Entity target) {
        int damage = this.getDamage();
        target.receiveDamage(damage);
    }
}
