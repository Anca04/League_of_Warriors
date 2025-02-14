package game.entities.characters;

public class Warrior extends Character {
    public Warrior(String name, int experience, int level) {
        super(name, 100, 50, 10, 5, 7, experience, level);
        setImmunityToFire(true);
    }

    @Override
    public int getDamage() {
        return getStrength() + getCharisma()+ getCurrentLevel();
    }

    @Override
    public void receiveDamage(int damage) {
        if (Math.random() < 0.5) {
            damage /= 2;
        }
        super.receiveDamage(damage);
    }
}
