package dessin;

import java.awt.Color;
import java.awt.Graphics;

public abstract class ObjetGraphique {
    private Color couleur;

    private boolean visible;

    public ObjetGraphique() {
        this(Color.black);
        this.visible = true;
    }

    public ObjetGraphique(Color c) {
        this.couleur = c;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setColor(Color c) {
        this.couleur = c;
    }

    public Color getColor() {
        return this.couleur;
    }

    public void dessineToi(Graphics g) {
        g.setColor(getColor());
    }

    public abstract boolean contient(int paramInt1, int paramInt2);
}
