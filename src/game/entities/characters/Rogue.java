package game.entities.characters;


public class Rogue extends Character {
    public Rogue(String name, int experience, int level) {
        super(name, 180, 60, 5, 5, 10, experience, level);
        setImmunityToEarth(true);
    }

    @Override
    public int getDamage() {
        return getDexterity() + getStrength() + getCurrentLevel();
    }

    @Override
    public void receiveDamage(int damage) {
        if (Math.random() < 0.5) {
            damage /= 2;
        }
        super.receiveDamage(damage);
    }
}
