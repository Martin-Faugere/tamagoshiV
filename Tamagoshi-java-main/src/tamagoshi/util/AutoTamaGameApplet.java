package tamagoshi.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JButton;

public class AutoTamaGameApplet extends JApplet {
    public void init() {
        super.init();
        JButton b = new JButton("dle jeu");
        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AutoTamaGame jeu = new AutoTamaGame();
                jeu.setSize(600, 200);
                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                Dimension window = jeu.getSize();
                jeu.setLocation((screen.width - window.width) / 2, (screen.height - window.height) / 2);
                jeu.setTitle("Les tamagoshis");
                jeu.setVisible(true);
                jeu.initPlay();
            }
        });
        add(b);
    }
}
