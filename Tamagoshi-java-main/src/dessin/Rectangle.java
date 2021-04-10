package dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Rectangle extends ObjetGraphique {
    private java.awt.Rectangle rec;

    public Rectangle(int x, int y, int largeur, int hauteur) {
        this.rec = new java.awt.Rectangle(x, y, largeur, hauteur);
    }

    public Rectangle(Point p, int largeur, int hauteur) {
        this(p.x, p.y, largeur, hauteur);
    }

    public Rectangle(Point p, int largeur, int hauteur, Color c) {
        this(p.x, p.y, largeur, hauteur);
        setColor(c);
    }

    public void dessineToi(Graphics g) {
        super.dessineToi(g);
        g.drawRect(this.rec.x, this.rec.y, this.rec.width, this.rec.height);
    }

    public boolean contient(int x, int y) {
        return this.rec.contains(x, y);
    }
}
