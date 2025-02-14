package game.grid;

import game.entities.characters.Enemy;
import game.enums.CellEntityType;
import game.exceptions.ImpossibleMove;
import game.entities.characters.Character;

import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    private static int length;
    private static int width;
    private Character currentCharacter;
    private Cell currentCell;
    private int currentX;
    private int currentY;

    // Constructor
    private Grid(int length, int width) {
        this.length = Math.min(length, 10);
        this.width = Math.min(width, 10);
    }

    // this is for the test class, it initializes exactly the grid from the pdf
    public static Grid initializeTestGrid(int length, int width, Character character) {
        Grid grid = new Grid(length, width);
        Random rand = new Random();

        // initialize with VOID
        for (int i = 0; i < length; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID));
            }
            grid.add(row);
        }

        // place player
        int playerX = 0;
        int playerY = 0;
        Cell playerCell = grid.get(playerX).get(playerY);
        playerCell.setType(CellEntityType.PLAYER);
        grid.setCurrentCell(playerCell);
        grid.setCurrentCharacter(character);

        // places entities
        placeEntityTest(grid, 0, 3, CellEntityType.SANCTUARY);
        placeEntityTest(grid, 1, 3, CellEntityType.SANCTUARY);
        placeEntityTest(grid, 2, 0, CellEntityType.SANCTUARY);
        placeEntityTest(grid, 3, 4, CellEntityType.ENEMY);
        placeEntityTest(grid, 4, 3, CellEntityType.SANCTUARY);
        placeEntityTest(grid, 4, 4, CellEntityType.PORTAL);

        return grid;
    }

    public static Grid initializeGrid(int length, int width, Character character) {
        Grid grid = new Grid(length, width);
        Random rand = new Random();

        // initialize with VOID
        for (int i = 0; i < length; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID));
            }
            grid.add(row);
        }

        // place at least 2 SANCTUARY
        for (int i = 0; i < 2; i++) {
            placeEntity(grid, length, width, CellEntityType.SANCTUARY);
        }

        // place at least 4 ENEMY
        for (int i = 0; i < 4; i++) {
            placeEntity(grid, length, width, CellEntityType.ENEMY);
        }

        // place 1 PORTAL
        placeEntity(grid, length, width, CellEntityType.PORTAL);

        // place other random cells
        int generateRandom = rand.nextInt(3) + 1;
        for (int i = 0; i < generateRandom; i++) {
            CellEntityType cellType;
            if (rand.nextBoolean()) {
                cellType = CellEntityType.ENEMY;
            } else {
                cellType = CellEntityType.SANCTUARY;
            }
            placeEntity(grid, length, width, cellType);
        }

        // place PLAYER
        int playerX = rand.nextInt(length);
        int playerY = rand.nextInt(width);
        Cell playerCell = grid.get(playerX).get(playerY);
        playerCell.setType(CellEntityType.PLAYER);
        grid.setCurrentCell(playerCell);
        grid.setCurrentCharacter(character);

        return grid;
    }

    // places entities for the test class at specific places
    public static void placeEntityTest(Grid grid, int length, int width, CellEntityType type) {
        while (true) {
            int x = length;
            int y = width;
            Cell cell = grid.get(x).get(y);
            if (cell.getType() == CellEntityType.VOID) {
                cell.setType(type);
                cell.setOriginalType(type);

                if (type == CellEntityType.ENEMY) {
                    Enemy enemy = new Enemy(100, 50);
                    cell.setEnemy(enemy);
                }
                break;
            }
        }
    }

    // places entities random positions
    public static void placeEntity(Grid grid, int length, int width, CellEntityType type) {
        Random random = new Random();
        while (true) {
            int x = random.nextInt(length);
            int y = random.nextInt(width);
            Cell cell = grid.get(x).get(y);
            if (cell.getType() == CellEntityType.VOID) {
                cell.setType(type);
                cell.setOriginalType(type);

                if (type == CellEntityType.ENEMY) {
                    Enemy enemy = new Enemy(100, 150);
                    cell.setEnemy(enemy);
                }
                break;
            }
        }
    }

    public void goNorth() throws ImpossibleMove {
        if (currentCell.getX() == 0) {
            throw new ImpossibleMove("Can't move north. Out of bounds.");
        }
        movePlayer(currentCell.getX() - 1, currentCell.getY());
    }

    public void goSouth() throws ImpossibleMove {
        if (currentCell.getX() == length - 1) {
            throw new ImpossibleMove("Can't move south. Out of bounds.");
        }
        movePlayer(currentCell.getX() + 1, currentCell.getY());
    }

    public void goWest() throws ImpossibleMove {
        if (currentCell.getY() == 0) {
            throw new ImpossibleMove("Can't move west. Out of bounds.");
        }
        movePlayer(currentCell.getX(), currentCell.getY() - 1);
    }

    public void goEast() throws ImpossibleMove {
        if (currentCell.getY() == width - 1) {
            throw new ImpossibleMove("Can't move east. Out of bounds.");
        }
        movePlayer(currentCell.getX(), currentCell.getY() + 1);
    }

    private void movePlayer(int newX, int newY) {
        currentCell.setType(CellEntityType.VOID);
        currentCell.setCellVisited(true);
        currentCell.markVisited();
        currentCell = this.get(newX).get(newY);
        currentCell.setType(CellEntityType.PLAYER);
    }

    public void setCurrentCharacter(Character character) {
        currentCharacter = character;
    }

    public void setCurrentCell(Cell cell) {
        currentCell = cell;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }
}
