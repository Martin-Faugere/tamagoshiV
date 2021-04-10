package tamagoshi.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class AutoTamaGame extends TamaGameGraphic {
    public AutoTamaGame() {
        this.listeTotale = new ArrayList<GrosMangeur>();
        this.alive = new ArrayList<>();
        this.frames = new ArrayList<>();
        Tamagoshi.setLifeTime(1000);
        initialisation();
    }

    private void initialisation() {
        JMenuBar menubar = new JMenuBar();
        JMenu partie = new JMenu("jeu");
        menubar.add(partie);
        JMenuItem nou = new JMenuItem("nouvelle partie", 78);
        nou.setAccelerator(KeyStroke.getKeyStroke(78, 8));
        nou.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AutoTamaGame.this.initPlay();
            }
        });
        partie.add(nou);
        setJMenuBar(menubar);
        this.infos = new JTextArea();
        add(new JScrollPane(this.infos));
    }

    private double score() {
        int score = 0;
        for (Tamagoshi t : this.listeTotale)
            score += t.getAge();
        return score;
    }

    private void resultat() {
        this.infos.append("-------------bilan------------");
        for (Tamagoshi t : this.listeTotale) {
            String classe = t.getClass().getName();
            classe = classe.substring(classe.lastIndexOf(".") + 1);
            this.infos.append("\n" + t.getName() + " qui un " + classe + " a survjusqu'l'de " + t.getAge());
        }
        afficheInfos("\nniveau de difficult: " + this.listeTotale.size() + ", score obtenu :" + score());
    }

    private void afficheInfos(String informations) {
        System.out.println(informations);
        this.infos.append(informations);
        this.infos.setCaretPosition(this.infos.getDocument().getLength());
    }

    private void isFinished() {
        if (this.alive.isEmpty()) {
            afficheInfos("\n\t--------- fin de partie !! ----------------\n\n");
            resultat();
        }
    }

    public final void initPlay() {
        for (JFrame frame : this.frames)
            frame.dispose();
        this.listeTotale = new ArrayList<GrosMangeur>();
        this.alive = new ArrayList<>();
        this.frames = new ArrayList<>();
        List<String> temp = Arrays.asList(new String[] { "Pierre", "Paul", "Jacques", "Zizou", "Karl", "Neo", "Pikachu", "Goldorak" });
        ArrayList<String> names = new ArrayList<>(temp);
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
            grosMangeur.setInitialPause(1000 * n);
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            TamaFrame tamaFrame = new TamaFrame((Tamagoshi)grosMangeur, this);
            System.out.println(tamaFrame.getSize());
            if (x * (tamaFrame.getSize()).width + (tamaFrame.getSize()).width > screen.width) {
                x = 0;
                y += (tamaFrame.getSize()).height + 30;
            }
            tamaFrame.setLocation(x * ((tamaFrame.getSize()).width + 5), y);
            tamaFrame.setVisible(true);
            this.frames.add(tamaFrame);
        }
        this.alive = (ArrayList<Tamagoshi>)this.listeTotale.clone();
        JOptionPane.showMessageDialog(this, "cliquez pour lancer la partie");
        launch();
    }

    public final void tamaDeath(Tamagoshi t) {
        this.alive.remove(t);
        for (Tamagoshi tt : this.alive)
            tt.diminuerPause(500);
        isFinished();
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(true);
        AutoTamaGame jeu = new AutoTamaGame();
        jeu.setSize(600, 200);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension window = jeu.getSize();
        jeu.setLocation((screen.width - window.width) / 2, (screen.height - window.height) / 2);
        jeu.setTitle("Les tamagoshis");
        jeu.setDefaultCloseOperation(3);
        jeu.setVisible(true);
        jeu.initPlay();
    }

    private void launch() {
        for (Tamagoshi t : this.listeTotale) {
            Thread tamaThread = new Thread((Runnable)t);
            tamaThread.start();
        }
    }
}