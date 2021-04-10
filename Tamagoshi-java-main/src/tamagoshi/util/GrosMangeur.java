package tamagoshi.util;

public class GrosMangeur extends Tamagoshi {
    public GrosMangeur(String name) {
        super(name);
    }

    public boolean consommeEnergie() {
        this.energy--;
        return super.consommeEnergie();
    }
}
