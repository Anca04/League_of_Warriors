package game;

import game.entities.Account;
import game.entities.characters.Enemy;
import game.enums.CellEntityType;
import game.exceptions.ImpossibleMove;
import game.grid.Cell;
import game.grid.Grid;
import game.entities.characters.Character;
import game.spells.Earth;
import game.spells.Fire;
import game.spells.Ice;
import game.spells.Spell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private ArrayList<Account> accounts;
    private Grid gameMap;
    private Scanner scanner;
    private Character currentCharacter;
    private int maps;
    private boolean gameOver;
    private Account userAccount;

    // Constructor
    public Game() {
        maps = 0;
        accounts = new ArrayList<>();
        this.accounts = JsonInput.deserializeAccounts();
        if (this.accounts == null || this.accounts.isEmpty()) {
            System.out.println("No accounts found. Exiting game.");
            System.exit(1);
        }
        scanner = new Scanner(System.in);
    }

    public void run(boolean isTest) {
        userAccount = null;
        int choice = 0;
        while (true) {
            System.out.println("Welcome to League of Warriors!");
            System.out.println("Choose an option: ");
            System.out.println("1. New game");
            System.out.println("2. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            while (choice != 1 && choice != 2) {
                System.out.println("Invalid choice. Try again...");
                choice = scanner.nextInt();
                scanner.nextLine();
            }

            // exit game
            if (choice == 2) {
                break;
            }

            // if I run the Test class or the TestGame class
            if (isTest) {
                // authentication
                userAccount = authenticateUser();
                while (userAccount == null) {
                    System.out.println("Authentication failed. Try again...");
                    userAccount = authenticateUser();
                }

                // select character
                currentCharacter = selectCharacter(userAccount);
                while (currentCharacter == null) {
                    System.out.println("No character selected. Try again...");
                    currentCharacter = selectCharacter(userAccount);
                }

                // the grid
                gameMap = Grid.initializeTestGrid(5, 5, currentCharacter);

                // starting the game
                gameOver = false;
                while (!gameOver) {
                    printGridTest(gameMap);
                    displayGameOptions();
                    String command = scanner.nextLine();
                    gameOver = processCommand(command);
                }
            } else {
                // authentication
                userAccount = authenticateUser();
                while (userAccount == null) {
                    System.out.println("Authentication failed. Try again...");
                    userAccount = authenticateUser();
                }

                // select character
                currentCharacter = selectCharacter(userAccount);
                while (currentCharacter == null) {
                    System.out.println("No character selected. Try again...");
                    currentCharacter = selectCharacter(userAccount);
                }

                // grid
                gameMap = Grid.initializeGrid((int) (Math.random() * 6) + 5, (int) (Math.random() * 6) + 5, currentCharacter);

                // start the game
                gameOver = false;
                while (!gameOver) {
                    printGrid(gameMap);
                    displayGameOptions();
                    String command = scanner.nextLine();
                    gameOver = processCommand(command);
                }
            }
        }

        // increases how many maps has the player completed
        if (maps > 0) {
//            userAccount.increaseGamesPlayed();
            System.out.println("Maps completed successfully: " + userAccount.getGamesPlayed());
        }

        System.out.println("Thank you for playing League of Warriors!");
    }

    // entering credentials, email and password
    public Account authenticateUser() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        // checks if the authentification is successful
        for (Account account : accounts) {
            String accountEmail = account.getInformation().getCredentials().getEmail().trim().toLowerCase();
            String accountPassword = account.getInformation().getCredentials().getPassword().trim();

            if (accountEmail.equals(email) && accountPassword.equals(password)) {
                System.out.println("Authentication successful! Welcome, " + account.getInformation().getName() + "!");
                return account;
            }
        }
        System.out.println("Invalid credentials.");
        return null;
    }

    public Character selectCharacter(Account account) {
        ArrayList<Character> characters = account.getCharacters();
        if (characters.isEmpty()) {
            System.out.println("No characters available for this account.");
            return null;
        }

        System.out.println("Select a character:");
        for (int i = 0; i < characters.size(); i++) {
            System.out.println((i + 1) + ". " + characters.get(i).getCharacterName());
        }

        // choosing the character by entering the index
        System.out.print("Enter your choice (1-" + characters.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice > 0 && choice <= characters.size()) {
            return characters.get(choice - 1);
        }
        System.out.println("Invalid choice.");
        return null;
    }

    private void displayGameOptions() {
        System.out.println("Choose an action:");
        System.out.println("1. Move North");
        System.out.println("2. Move South");
        System.out.println("3. Move West");
        System.out.println("4. Move East");
        System.out.println("5. Exit Game");
    }

    // process the user's command
    public boolean processCommand(String command) {
        switch (command) {
            case "1":
                try {
                    gameMap.goNorth();
                    handleCurrentCell();
                } catch (ImpossibleMove e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "2":
                try {
                    gameMap.goSouth();
                    handleCurrentCell();
                } catch (ImpossibleMove e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "3":
                try {
                    gameMap.goWest();
                    handleCurrentCell();
                } catch (ImpossibleMove e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "4":
                try {
                    gameMap.goEast();
                    handleCurrentCell();
                } catch (ImpossibleMove e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "5":
                return true;
            default:
                System.out.println("Invalid command. Try again...");
                break;
        }
        if (gameOver)
            return true;
        return false;
    }

    // handles the type of the cell
    public void handleCurrentCell() {
        Cell currentCell = gameMap.getCurrentCell();
        CellEntityType cellType = currentCell.getOriginalType();
        Random random = new Random();

        switch (cellType) {
            case ENEMY:
                Enemy enemy = currentCell.getEnemy();
                if (enemy != null) {
                    System.out.println("You encountered an enemy!");

                    currentCharacter.setAbilities(generateAbilities());

                    handleEnemy(enemy);
                    currentCell.setType(CellEntityType.VOID);
                    currentCell.setOriginalType(CellEntityType.VOID);
                    currentCell.setType(CellEntityType.PLAYER);
                } else {
                    System.out.println("The enemy is missing!");
                }
                break;
            case SANCTUARY:
                System.out.println("You found a sanctuary! Restoring health and mana...");
                currentCharacter.regenerateHealth(random.nextInt(50) + 1);
                currentCharacter.regenerateMana(random.nextInt(30) + 1);
                currentCell.setType(CellEntityType.VOID);
                currentCell.setOriginalType(CellEntityType.VOID);
                currentCell.setType(CellEntityType.PLAYER);
                break;
            case PORTAL:
                maps++;
                System.out.println("You found a portal! Advancing to the next level...");
                advanceToNextLevel();
                currentCharacter.addExperience(currentCharacter.getExperience());
                break;
            case VOID:
                System.out.println("This cell is void. Choose a different direction.");
                break;
            default:
                System.out.println("This cell is empty.");
                break;
        }
    }

    // fights the enemy by regular attack or using abilities
    public void handleEnemy(Enemy enemy) {
        Random random = new Random();
        int previusMana = 0;

        System.out.println(currentCharacter.getCharacterName() + " health: " + currentCharacter.getHealth());
        System.out.println(currentCharacter.getCharacterName() + " mana: " + currentCharacter.getCurrentMana());
        System.out.println("Enemy health: " + enemy.getCurrentHealth());
        System.out.println("Enemy mana: " + enemy.getCurrentMana());
        while (currentCharacter.getHealth() > 0 && enemy.getCurrentHealth() > 0) {
            System.out.println("Choose an action:");
            System.out.println("1. Attack");
            System.out.println("2. Use Ability");

            String choice = scanner.nextLine();
            if (choice.equals("1")) {
                // regular attack
                currentCharacter.attack(enemy);
                if (enemy.getCurrentHealth() > 0) {
                    System.out.println("Remaining health enemy: " + enemy.getCurrentHealth());
                }
            } else if (choice.equals("2")) {
                // use ability
                if (!currentCharacter.getAbilities().isEmpty()) {
                    // display abilities
                    System.out.println("Choose an ability:");
                    for (int i = 0; i < currentCharacter.getAbilities().size(); i++) {
                        Spell ability = currentCharacter.getAbilities().get(i);
                        System.out.println((i + 1) + ". " + ability.getName() + " (Damage: " + ability.getDamage() + ", Mana Cost: " + ability.getManaCost() + ")");
                    }

                    // get ability index
                    int abilityIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    previusMana = currentCharacter.getCurrentMana();

                    if (abilityIndex >= 0 && abilityIndex < currentCharacter.getAbilities().size()) {
                        // use the selected ability
                        currentCharacter.useAbility(abilityIndex, enemy);
                        if (previusMana < currentCharacter.getAbilities().get(abilityIndex).getManaCost()) {
                            System.out.println("Regular attack.");
                            currentCharacter.attack(enemy);
                            System.out.println("You dealt " + currentCharacter.getDamage() + " damage to the enemy.");
                        }

                        if (previusMana >= currentCharacter.getAbilities().get(abilityIndex).getManaCost() &&
                                !((currentCharacter.getAbilities().get(abilityIndex).getType().equals("Fire") && enemy.isImmunityToIce()) ||
                                (currentCharacter.getAbilities().get(abilityIndex).getType().equals("Ice") && enemy.isImmunityToIce()) ||
                                (currentCharacter.getAbilities().get(abilityIndex).getType().equals("Earth") && enemy.isImmunityToEarth()))) {
                            System.out.println(currentCharacter.getCharacterName() + "has mana left: " + currentCharacter.getCurrentMana());
                            int health = enemy.getCurrentHealth() - currentCharacter.getAbilities().get(abilityIndex).getDamage();
                            if (health < 0) {
                                health = 0;
                            }
                            enemy.setCurrentHealth(health);
                        }

                        currentCharacter.getAbilities().remove(abilityIndex);
                        System.out.println("The enemy's remaining health: " + enemy.getCurrentHealth());
                    } else {
                        System.out.println("Invalid ability index!");
                        continue;
                    }
                } else if (currentCharacter.getAbilities().isEmpty()) {
                    System.out.println("No abilities left! Using a default attack.");
                    System.out.println("Regular attack.");
                    currentCharacter.attack(enemy);
                    System.out.println("You dealt " + currentCharacter.getDamage() + " damage to the enemy.");
                    if (enemy.getCurrentHealth() > 0) {
                        System.out.println("Remaining health enemy: " + enemy.getCurrentHealth());
                    }
                }

                if (enemy.getCurrentHealth() <= 0) {
                    gameMap.getCurrentCell().setEnemy(null);
                    gameMap.getCurrentCell().setType(CellEntityType.PLAYER);
                    break;
                }

                if (currentCharacter.getCurrentHealth() <= 0) {
                    break;
                }
            }

            if (enemy.getCurrentHealth() <= 0) {
                gameMap.getCurrentCell().setEnemy(null);
                gameMap.getCurrentCell().setType(CellEntityType.PLAYER);
                break;
            }

            // the enemy's turn
            int indexAbilityEnemy = 0;
            if (!enemy.getAbilities().isEmpty()) {
                indexAbilityEnemy = random.nextInt(enemy.getAbilities().size());
            }
            System.out.println("Enemy attacks!");
            if (enemy.getAbilities().isEmpty() || enemy.getCurrentMana() < enemy.getAbilities().get(indexAbilityEnemy).getManaCost()) {
                enemy.attack(currentCharacter);
            } else {
                int preManaEnemy = enemy.getCurrentMana();
                enemy.useAbility(indexAbilityEnemy, currentCharacter);
                System.out.println("Enemy has mana left: " + enemy.getCurrentMana());

                if (preManaEnemy >= enemy.getAbilities().get(indexAbilityEnemy).getManaCost() &&
                        !((enemy.getAbilities().get(indexAbilityEnemy).getType().equals("Fire") && currentCharacter.isImmunityToFire()) ||
                                (enemy.getAbilities().get(indexAbilityEnemy).getType().equals("Ice") && currentCharacter.isImmunityToIce()) ||
                                (enemy.getAbilities().get(indexAbilityEnemy).getType().equals("Earth") && currentCharacter.isImmunityToEarth()))) {
                    int health = currentCharacter.getHealth() - enemy.getAbilities().get(indexAbilityEnemy).getDamage();
                    if (health < 0) {
                        health = 0;
                    }
                    currentCharacter.setCurrentHealth(health);
                    enemy.getAbilities().remove(indexAbilityEnemy);
                }

                System.out.println(currentCharacter.getCharacterName() + " health remaining: " + currentCharacter.getHealth());
            }
        }

        if (currentCharacter.getHealth() <= 0) {
            System.out.println("You have been defeated!");
            System.exit(0);
            gameOver = true;
        } else {
            System.out.println("You defeated the enemy!");
            gameMap.getCurrentCell().setType(CellEntityType.VOID);
        }
    }

    // prints the cells as VOID
    public void printGrid(Grid grid) {
        System.out.println("Current grid:");
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                Cell cell = grid.get(i).get(j);
                if (cell.getType() == CellEntityType.PLAYER) {
                    System.out.print("P ");
                } else if (cell.getType() == CellEntityType.SANCTUARY) {
                    System.out.print("N ");
                } else if (cell.getType() == CellEntityType.ENEMY) {
                    System.out.print("N ");
                } else if (cell.getType() == CellEntityType.PORTAL) {
                    System.out.print("N ");
                } else if (cell.isCellVisited() && cell.getType() == CellEntityType.VOID) {
                    System.out.print("V ");
                } else if (cell.getType() == CellEntityType.VOID) {
                    System.out.print("N ");
                }
            }
            System.out.println();
        }
    }

    // lets the user see what cell type it is
    public void printGridTest(Grid grid) {
        System.out.println("Current grid:");
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(i).size(); j++) {
                Cell cell = grid.get(i).get(j);
                if (cell.getType() == CellEntityType.PLAYER) {
                    System.out.print("P ");
                } else if (cell.getType() == CellEntityType.SANCTUARY) {
                    System.out.print("S ");
                } else if (cell.getType() == CellEntityType.ENEMY) {
                    System.out.print("E ");
                } else if (cell.getType() == CellEntityType.PORTAL) {
                    System.out.print("F ");
                } else if (cell.isCellVisited() && cell.getType() == CellEntityType.VOID) {
                    System.out.print("V ");
                } else if (cell.getType() == CellEntityType.VOID) {
                    System.out.print("N ");
                }
            }
            System.out.println();
        }
    }

    // after it handles the portal cell
    public void advanceToNextLevel() {
        // reset the player's health and mana to maximum
        currentCharacter.setCurrentHealth(currentCharacter.getMaxHealth());
        currentCharacter.setCurrentMana(currentCharacter.getMaxMana());
        if (currentCharacter.getAbilities().isEmpty()) {
            currentCharacter.setAbilities(generateAbilities());
        }

        int game = userAccount.increaseGamesPlayed();
        userAccount.setGamesPlayed(game);

        // generate a new grid with new dimensions and entities
        int newLength = (int) (Math.random() * 6) + 5;
        int newWidth = (int) (Math.random() * 6) + 5;
        gameMap = Grid.initializeGrid(newLength, newWidth, currentCharacter);

        // display the new grid
        System.out.println("New level generated:");
    }

    public List<Spell> generateAbilities() {
        Random random = new Random();
        List<Spell> abilities = new ArrayList<>();

        // between 3 and 6 abilities
        int numberOfAbilities = random.nextInt(3) + 1;

        abilities.add(0, new Fire("Fire", random.nextInt(30) + 10, random.nextInt(20) + 10, "Fire"));
        abilities.add(1, new Ice("Ice", random.nextInt(20) + 10, random.nextInt(15) + 10, "Ice"));
        abilities.add(2, new Earth("Earth", random.nextInt(40) + 20, random.nextInt(25) + 15, "Earth"));

        // completes the rest of the abilities
        for (int i = 0; i < numberOfAbilities; i++) {
            // 0 = Fire, 1 = Ice, 2 = Earth
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
            }
        }
        return abilities;
    }
}
