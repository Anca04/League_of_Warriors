package game.grid;

import game.entities.characters.Warrior;
import game.entities.characters.*;
import game.enums.CellEntityType;
import game.exceptions.ImpossibleMove;

public class TestGrid {
    private static void printGrid(Grid grid) {
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
                } else {
                    System.out.print("N ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        System.out.println("Grid Test");

        // create character
        Warrior warrior = new Warrior("TestWarrior", 100, 1);

        // generate a 5 x 5 grid
        Grid testGrid = Grid.initializeGrid(5, 5, warrior);

        // print grid
        System.out.println("Initial Grid:");
        printGrid(testGrid);

        // simulate moving the player
        simulateGame(testGrid, warrior);

        // print grid
        System.out.println("\nFinal Grid:");
        printGrid(testGrid);
    }

    private static void simulateGame(Grid grid, Warrior warrior) {
        boolean gameOver = false;
        while (!gameOver) {
            System.out.println("\nCurrent grid:");
            printGrid(grid);

            System.out.println("Choose an action:");
            System.out.println("1. Move North");
            System.out.println("2. Move South");
            System.out.println("3. Move West");
            System.out.println("4. Move East");
            System.out.println("5. Exit Game");

            int action = getUserInput();

            switch (action) {
                case 1:
                    try {
                        grid.goNorth();
                        processCell(grid, warrior);
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        grid.goSouth();
                        processCell(grid, warrior);
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        grid.goWest();
                        processCell(grid, warrior);
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        grid.goEast();
                        processCell(grid, warrior);
                    } catch (ImpossibleMove e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Exiting game...");
                    gameOver = true;
                    break;
                default:
                    System.out.println("Invalid action. Please choose again.");
            }
        }
    }

    private static void processCell(Grid grid, Warrior warrior) {
        CellEntityType currentCellType = grid.getCurrentCell().getOriginalType();

        Cell currentCell = grid.getCurrentCell();
        switch (currentCell.getOriginalType()) {
            case ENEMY:
                System.out.println("You encountered an enemy!");
                break;
            case SANCTUARY:
                System.out.println("You found a sanctuary!");
                break;
            case PORTAL:
                System.out.println("You found a portal!");
                break;
            case VOID:
                System.out.println("This cell is empty.");
                break;
            default:
                System.out.println("Unknown cell type.");
                break;
        }
    }

    private static int getUserInput() {
        System.out.print("Enter your choice: ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        return scanner.nextInt();
    }
}
