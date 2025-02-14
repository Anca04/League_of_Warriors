package game.spells;

public abstract class Spell {
    private String name;
    protected int damage;
    protected int manaCost;
    private String type;

    public Spell(String name, int damage, int manaCost, String type) {
        this.name = name;
        this.damage = damage;
        this.manaCost = manaCost;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return this.getClass().getSimpleName() + " Damage: " + damage +
                ", Mana Cost: " + manaCost;
    }
}
