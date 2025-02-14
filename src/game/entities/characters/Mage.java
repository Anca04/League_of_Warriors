package game.entities.characters;

public class Mage extends Character {
    public Mage(String name, int experience, int level) {
        super(name, 150, 100, 5, 10, 5, experience, level);
        setImmunityToIce(true);
    }

    @Override
    public int getDamage() {
        return getCharisma() + getDexterity() + getCurrentLevel();
    }

    @Override
    public void receiveDamage(int damage) {
        if (Math.random() < 0.5) {
            damage /= 2;
        }
        super.receiveDamage(damage);
    }
}
