package game.grid;

import game.entities.characters.Enemy;
import game.entities.characters.Entity;
import game.enums.CellEntityType;

public class Cell {
    private int x;
    private int y;
    private CellEntityType type;
    private CellEntityType originalType;
    private boolean cellVisited;
    private Entity entity;
    private Enemy enemy;

    // Constructor
    public Cell(int x, int y, CellEntityType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.originalType = type;
        this.cellVisited = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public CellEntityType getType() {
        return type;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    public boolean isCellVisited() {
        return cellVisited;
    }

    public void setCellVisited(boolean cellVisited) {
        this.cellVisited = cellVisited;
    }

    public CellEntityType getOriginalType() {
        return originalType;
    }

    public void markVisited() {
        this.cellVisited = true;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Enemy getEnemy() {
        if (originalType == CellEntityType.ENEMY) {
            return enemy;
        }
        return null;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
        this.type = CellEntityType.ENEMY;
    }

    // keeping the original type of the cell to handle enemy
    public void setOriginalType(CellEntityType originalType) {
        this.originalType = originalType;
    }
}
