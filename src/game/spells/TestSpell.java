package game.spells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestSpell {
    public static void main(String[] args) {
        List<Spell> playerSpells = generateRandomSpells();
        int playerMana = 50;

        // spell list
        System.out.println("Player's Spells:");
        displaySpells(playerSpells);

        // simulate spell usage
        System.out.println("\nSimulate Spell Usage");
        Random random = new Random();

        while (!playerSpells.isEmpty()) {
            boolean spellCast = false;

            // checks if there is enough mana for the spells
            for (Spell spell : playerSpells) {
                if (playerMana >= spell.getManaCost()) {
                    spellCast = true;
                    break;
                }
            }

            // if no spells can be used, break the loop
            if (!spellCast) {
                System.out.println("No spells can be cast with the remaining mana. Ending simulation.");
                break;
            }

            // randomly select a spell
            int spellIndex = random.nextInt(playerSpells.size());
            Spell selectedSpell = playerSpells.get(spellIndex);

            System.out.println("Selected Spell: " + selectedSpell);
            if (playerMana >= selectedSpell.getManaCost()) {
                System.out.println("Player has enough mana. Casting spell...");
                playerMana -= selectedSpell.getManaCost();
                System.out.println("Spell cast successfully! Remaining mana: " + playerMana);
                playerSpells.remove(spellIndex);
            } else {
                System.out.println("Not enough mana to cast this spell. Using default attack instead.");
            }

            // spell list
            System.out.println("\nUpdated Spell List:");
            displaySpells(playerSpells);
        }

        if (playerSpells.isEmpty()) {
            System.out.println("All spells have been used!");
        } else {
            System.out.println("Simulation ended with remaining spells and mana.");
        }
    }

    // generate a random list of spells with at least one of each type
    private static List<Spell> generateRandomSpells() {
        List<Spell> spells = new ArrayList<>();
        Random random = new Random();

        spells.add(new Ice("Ice", random.nextInt(11) + 10, random.nextInt(6) + 5, "Ice"));
        spells.add(new Fire("Fire", random.nextInt(14) + 13, random.nextInt(3) + 4, "Fire"));
        spells.add(new Earth("Earth", random.nextInt(7) + 8, random.nextInt(8) + 7, "Earth"));

        int additionalSpells = random.nextInt(4) + 3;
        for (int i = 0; i < additionalSpells; i++) {
            int type = random.nextInt(3);
            int damage = random.nextInt(20) + 10;
            int manaCost = random.nextInt(10) + 5;

            switch (type) {
                case 0:
                    spells.add(new Ice("Ice", damage, manaCost, "Ice"));
                    break;
                case 1:
                    spells.add(new Fire("Fire", damage, manaCost, "Fire"));
                    break;
                case 2:
                    spells.add(new Earth("Earth", damage, manaCost, "Earth"));
                    break;
            }
        }

        return spells;
    }

    private static void displaySpells(List<Spell> spells) {
        if (spells.isEmpty()) {
            System.out.println("No spells available.");
        } else {
            for (int i = 0; i < spells.size(); i++) {
                System.out.println(i + ": " + spells.get(i));
            }
        }
    }
}
