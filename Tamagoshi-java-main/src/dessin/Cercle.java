package dessin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Cercle extends ObjetGraphique {
    private Point centre;

    private int rayon;

    public Cercle() {
        this.centre = new Point();
        this.rayon = 0;
    }

    public Cercle(Point centre, int rayon) {
        this.centre = centre;
        this.rayon = rayon;
    }

    public Cercle(Point centre, int rayon, Color couleur) {
        super(couleur);
        this.centre = centre;
        this.rayon = rayon;
    }

    public void setLocation(Point p) {
        this.centre = p;
    }

    public void setRayon(int r) {
        this.rayon = r;
    }

    public Point getLocation() {
        return this.centre;
    }

    public int getRayon() {
        return this.rayon;
    }

    public void dessineToi(Graphics g) {
        super.dessineToi(g);
        g.drawOval(this.centre.x - this.rayon, this.centre.y - this.rayon, this.rayon * 2, this.rayon * 2);
    }

    public boolean contient(int vx, int vy) {
        int dx = vx - this.centre.x;
        int dy = vy - this.centre.y;
        int d = dx * dx + dy * dy;
        return (d <= this.rayon * this.rayon);
    }
}
