package game.entities.characters;

import game.spells.Earth;
import game.spells.Fire;
import game.spells.Ice;

public class TestEntityCharacter {
    public static void main(String[] args) {
        Warrior warrior = new Warrior("Scarlett", 21, 4);
        Mage mage = new Mage("John", 50, 7);
        Rogue rogue = new Rogue("Kaz", 40, 15);

        testCharacterAttributes(warrior);
        testCharacterAttributes(mage);
        testCharacterAttributes(rogue);

        // simulate taking damage
        System.out.println("\nTest Receive Damage ");
        warrior.receiveDamage(30);
        mage.receiveDamage(30);
        rogue.receiveDamage(30);

        // simulate healing
        System.out.println("\nTest Health Regeneration ");
        warrior.regenerateHealth(20);
        mage.regenerateHealth(20);
        rogue.regenerateHealth(20);

        // test abilities
        System.out.println("\nTest Abilities ");
        warrior.addAbility(new Fire("Fire", 10, 25, "FIRE"));
        mage.addAbility(new Ice("Ice", 15, 30, "ICE"));
        rogue.addAbility(new Earth("Earth", 5, 20, "EARTH"));

        warrior.useAbility(0, mage); // Warrior attacks Mage
        mage.useAbility(0, rogue);  // Mage attacks Rogue
        rogue.useAbility(0, warrior); // Rogue attacks Warrior

        // test leveling up
        System.out.println("\nTest Level Up ");
        warrior.addExperience(5);
        mage.addExperience(12);
        rogue.addExperience(6);

        // attributes after level up
        System.out.println("\nAfter Level Up ");
        testCharacterAttributes(warrior);
        testCharacterAttributes(mage);
        testCharacterAttributes(rogue);
    }

    private static void testCharacterAttributes(Character character) {
        System.out.println("Character: " + character.getCharacterName());
        System.out.println("Level: " + character.getCurrentLevel());
        System.out.println("Experience: " + character.getExperience());
        System.out.println("Health: " + character.getCurrentHealth() + "/" + character.getMaxHealth());
        System.out.println("Mana: " + character.getCurrentMana() + "/" + character.getMaxMana());
        System.out.println("Strength: " + character.getStrength());
        System.out.println("Charisma: " + character.getCharisma());
        System.out.println("Dexterity: " + character.getDexterity());
    }
}
