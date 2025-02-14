package game.entities.characters;

import game.spells.Spell;

import java.util.ArrayList;
import java.util.Random;

public abstract class Character extends Entity {
    private String characterName;
    private int experience;
    private int currentLevel;
    protected int strength;
    protected int charisma;
    protected int dexterity;
    protected ArrayList<Spell> abilities;

    // Constructor
    public Character(String characterName, int maxHealth, int maxMana, int strength, int charisma, int dexterity, int experience, int currentLevel) {
        super(maxHealth, maxMana, false, false, false);
        this.characterName = characterName;
        this.experience = experience;
        this.currentLevel = currentLevel;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
        this.abilities = new ArrayList<>();
    }

    // level up
    public void addExperience(int exp) {
        experience += exp;
        int oldExperience = experience;
        System.out.println(characterName + " gained " + exp + " experience. Total: " + experience);

        if (experience >= currentLevel * 10) {
            checkLevelUp();
        }
    }

    // increase values for level up and regenerate
    public void checkLevelUp() {
        Random random = new Random();
        currentLevel++;
        strength += 5;
        charisma += 3;
        dexterity += 4;
        experience -= (currentLevel - 1) * 10;
        regenerateHealth(random.nextInt(50) + 1);
        regenerateMana(random.nextInt(30) + 1);
        System.out.println(characterName + " has leveled up to level " + currentLevel + "!");
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public int getExperience() {
        return experience;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getStrength() {
        return strength;
    }

    public int getCharisma() {
        return charisma;
    }

    @Override
    public int getCurrentMana() {
        return maxMana;
    }

    @Override
    public void setCurrentMana(int mana) {
        maxMana = mana;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getHealth() {
        return currentHealth;
    }

    @Override
    public void regenerateHealth(int amount) {
        this.currentHealth = Math.min(this.currentHealth + amount, this.maxHealth);
        System.out.println("Health regenerated to " + this.currentHealth);
    }

    @Override
    public void regenerateMana(int amount) {
        this.currentMana = Math.min(this.currentMana + amount, this.maxMana);
        System.out.println("Mana regenerated to " + this.currentMana);
    }

    @Override
    public void receiveDamage(int damage) {
        currentHealth = Math.max(0, currentHealth - damage);
        System.out.println(characterName + " receives " + damage + " damage, " + currentHealth + " health remaining.");
    }

    @Override
    public  int getDamage() {
        return strength + charisma + dexterity;
    }

    @Override
    public String toString() {
        return "Character: " + "Name = " + characterName + ", Level = " + currentLevel + ", Experience = " + experience;
    }

    // regular atack for enemy
    public void attack(Enemy target) {
        int damage = this.getDamage();
        target.receiveDamage(damage);
    }
}

