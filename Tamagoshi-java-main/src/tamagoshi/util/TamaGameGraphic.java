package tamagoshi.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class TamaGameGraphic extends JFrame {
    protected ArrayList<GrosMangeur> listeTotale;

    protected ArrayList<Tamagoshi> alive;

    protected ArrayList<TamaFrame> frames;

    protected JTextArea infos;

    protected int cycle;

    protected int nbActions;

    public TamaGameGraphic() {
        this.listeTotale = new ArrayList<GrosMangeur>();
        this.alive = new ArrayList<>();
        this.frames = new ArrayList<>();
        this.cycle = 0;
        this.nbActions = 0;
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                TamaGameGraphic.this.close();
            }
        });
        initialisation();
    }

    private void initialisation() {
        this.infos = new JTextArea();
        add(new JScrollPane(this.infos));
    }

    private double score() {
        double score = 0;
        for (GrosMangeur t : this.listeTotale)
            score += t.getAge();
        return (score * 100 / Tamagoshi.getLifeTime() * this.listeTotale.size());
    }

    private void resultat() {
        this.infos.append("-------------bilan------------");
        for (GrosMangeur t : this.listeTotale) {
            String classe = t.getClass().getName();
            classe = classe.substring(classe.lastIndexOf(".") + 1);
            this.infos.append("\n" + t.getName() + " qui un " + classe + " " + ((t.getAge() == Tamagoshi.getLifeTime()) ? " a survet vous remercie :)" : " n'est pas arrivau bout et ne vous fpas :("));
        }
        displayInfos("\nniveau de difficult: " + this.listeTotale.size() + ", score obtenu :" + score() + "%");
    }

    private void newCycle() {
        enableButtons("nourrir", true);
        enableButtons("jouer", true);
        displayInfos("\n------------Cycle n"+this.cycle + "-------------");
        this.nbActions = 0;
        for (Tamagoshi t : this.alive)
            t.parle();
    }

    private void displayInfos(String informations) {
        System.out.println(informations);
        this.infos.append(informations);
        this.infos.setCaretPosition(this.infos.getDocument().getLength());
    }

    private void isFinished() {
        if (this.nbActions == 2) {
            for (Iterator<Tamagoshi> i = this.alive.iterator(); i.hasNext(); ) {
                Tamagoshi t = i.next();
                if (!t.consommeEnergie() || !t.consommeFun() || t.vieillit())
                    i.remove();
            }
            if (this.alive.isEmpty()) {
                displayInfos("\n\t--------- fin de partie !! ----------------\n\n");
                resultat();
            } else {
                newCycle();
            }
        }
    }

    private void enableButtons(String buttonName, boolean enable) {
        for (TamaFrame frame : this.frames)
            frame.enableButtons(buttonName, enable);
    }

    public void play() {
        List<String> temp = Arrays.asList(new String[] { "Pierre", "Paul", "Jacques", "Zizou", "Karl", "Neo", "Ubufox", "Hal" });
        ArrayList<String> names = new ArrayList<>(temp);
        for (TamaFrame f : this.frames)
            f.dispose();
        this.listeTotale = new ArrayList<GrosMangeur>();
        displayInfos("\n------------ Nouvelle Partie -------------");
        int n = 0;
        while (n <= 0 || n > 8) {
            try {
                n = Integer.parseInt(JOptionPane.showInputDialog("Entrez le nombre de tamagoshis d(Max : 8) :", "3"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrez un nombre svp (Max : 8)", "Erreur de saisie", 0);
            }
        }
        for (int i = 0, x = 0, y = 0; i < n; i++, x++) {
            GrosMangeur grosMangeur = null;
            String name = names.remove((int)(Math.random() * names.size()));
            if (Math.random() < 0.5D) {
                GrosJoueur grosJoueur = new GrosJoueur(name);
            } else {
                grosMangeur = new GrosMangeur(name);
            }
            this.listeTotale.add(grosMangeur);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            TamaFrame tamaFrame = new TamaFrame((Tamagoshi)grosMangeur, this);
            this.frames.add(tamaFrame);
            System.out.println(tamaFrame.getSize());
            if (x * (tamaFrame.getSize()).width + (tamaFrame.getSize()).width > screen.width) {
                x = 0;
                y += (tamaFrame.getSize()).height + 30;
            }
            tamaFrame.setLocation(x * ((tamaFrame.getSize()).width + 5), y);
            tamaFrame.setVisible(true);
        }
        this.alive = (ArrayList<Tamagoshi>)this.listeTotale.clone();
        this.cycle = 0;
        newCycle();
    }

    public void userAction(String action) {
        enableButtons(action, false);
        this.nbActions++;
        isFinished();
    }

    private void close() {
        for (JFrame f : this.frames)
            f.dispose();
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        TamaGameGraphic jeu = new TamaGameGraphic();
        jeu.setSize(600, 200);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension window = jeu.getSize();
        jeu.setLocationRelativeTo((Component)null);
        jeu.setTitle("Les tamagoshis");
        jeu.setDefaultCloseOperation(3);
        jeu.setVisible(true);
        jeu.play();
    }
}