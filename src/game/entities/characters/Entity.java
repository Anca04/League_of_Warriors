package game.entities.characters;

import game.spells.Spell;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements Battle {
    private List<Spell> abilities = new ArrayList<>();
    protected int currentHealth;
    protected int maxHealth;
    protected int currentMana;
    protected int maxMana;
    private boolean immunityToFire;
    private boolean immunityToIce;
    private boolean immunityToEarth;
    private int normalAttack;

    // Constructor
    public Entity(int maxHealth, int maxMana, boolean immunityToFire, boolean immunityToIce, boolean immunityToEarth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.immunityToFire = immunityToFire;
        this.immunityToIce = immunityToIce;
        this.immunityToEarth = immunityToEarth;
        this.abilities = new ArrayList<>();
        normalAttack = 0;
    }

    public void addAbility(Spell ability) {
        abilities.add(ability);
    }

    @Override
    public abstract void receiveDamage(int damage);

    @Override
    public abstract int getDamage();

    public void regenerateHealth(int amount) {
        currentHealth = Math.min(currentHealth + amount, maxHealth);
    }

    public void regenerateMana(int amount) {
        currentMana = Math.min(currentMana + amount, maxMana);
    }

    // use ability, if there is no ability left, it uses default atack
    public void useAbility (int abilityIndex, Entity entity) {
        Spell ability = abilities.get(abilityIndex);

        // checks if there is enough mana
        if (currentMana < ability.getManaCost()) {
            System.out.println("Not enough mana to use " + ability.getName());
            normalAttack = 1;
            return;
        }

        if ((ability.getType().equals("Fire") && entity.hasImmunity("Fire", entity)) ||
                (ability.getType().equals("Ice") && entity.hasImmunity("Ice", entity)) ||
                (ability.getType().equals("Earth") && entity.hasImmunity("Earth", entity))) {
            System.out.println("Target is immune to " + ability.getType());
            int damage = ability.getDamage();
            currentMana -= ability.getManaCost();
            setCurrentMana(currentMana);
        } else {
            int damage = ability.getDamage();
            currentMana -= ability.getManaCost();
            setCurrentMana(currentMana);
            System.out.println("Used: " + ability.getName() + " (Damage: " + ability.getDamage() + ", Mana Cost: " + ability.getManaCost() + ")");
        }
    }

    public boolean hasImmunity(String type, Entity entity) {
        switch (type) {
            case "Fire":
                return entity.isImmunityToFire();
            case "Ice":
                return entity.isImmunityToIce();
            case "Earth":
                return entity.isImmunityToEarth();
            default:
                return false;
        }
    }

    public List<Spell> getAbilities() {
        return this.abilities;
    }

    public void setAbilities(List<Spell> abilities) {
        this.abilities = abilities;
    }

    public int getNormalAttack() {
        return normalAttack;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public boolean isImmunityToFire() {
        return immunityToFire;
    }

    public void setImmunityToFire(boolean immunityToFire) {
        this.immunityToFire = immunityToFire;
    }

    public boolean isImmunityToIce() {
        return immunityToIce;
    }

    public void setImmunityToIce(boolean immunityToIce) {
        this.immunityToIce = immunityToIce;
    }

    public boolean isImmunityToEarth() {
        return immunityToEarth;
    }

    public void setImmunityToEarth(boolean immunityToEarth) {
        this.immunityToEarth = immunityToEarth;
    }
}
