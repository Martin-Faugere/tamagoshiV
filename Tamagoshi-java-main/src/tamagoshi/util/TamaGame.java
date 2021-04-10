package tamagoshi.util;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class TamaGame {
    private ArrayList<Tamagoshi> allTamagoshis;
    private ArrayList<Tamagoshi> aliveTamagoshis;

    public TamaGame() {
        this.allTamagoshis = initialisationAll();
        this.aliveTamagoshis = initialisationAlive(this.allTamagoshis);
    }

    public ArrayList<Tamagoshi> initialisationAll(){
        ArrayList<Tamagoshi> all = new ArrayList<Tamagoshi>();
        System.out.println("Entrez le nombre tamagoshis souhaités :");
        int nb = inputNb();
        for(int i = 0; i<nb; ++i){
            System.out.println("Entrez le nom du tamagoshi "+(i+1)+" :");
            String nomTama = Utilisateur.saisieClavier();
            if (Math.random() < 0.5D) {
                all.add(i,new GrosJoueur(nomTama));
            } else {
                all.add(i,new GrosMangeur(nomTama));
            }
        }
        return all;
    }

    public ArrayList<Tamagoshi> initialisationAlive(ArrayList<Tamagoshi> all){
        return new ArrayList<Tamagoshi>(all);
    }

    public int inputNb(){
        boolean type = false;
        int nb = 0;
        while(!type) {
            String nbS = Utilisateur.saisieClavier();
            try {
                nb = parseInt(nbS);
                type = true;
            } catch (java.lang.NumberFormatException e) {
                System.out.println("La valeur entrée n'est pas un chiffre, veuillez ressaisir SVP");
            }
        }
        return nb;
    }

    public int inputNbMax(int max){
        int i = inputNb();
        if (i>max)
            return max;
        else
            return i;
    }

    public void play(){
        int i = 1;
        while(!this.aliveTamagoshis.isEmpty()&&i<=10&&this.aliveTamagoshis.size()>1){
            tour(i);
            i++;
        }
        fin();
    }

    public void tour(int i){
        int x = 1;
        System.out.println("\n--------- Cycle n°"+i+" ---------\n");
        for (Tamagoshi t : this.aliveTamagoshis){
            t.parle();
        }
        System.out.println("Nourrir quel Tamagochi ?");
        for (Tamagoshi t : this.aliveTamagoshis){
            System.out.println("("+x+") "+t.getName());
            x++;
        }
        int choix = inputNbMax(this.aliveTamagoshis.size());
        this.aliveTamagoshis.get(choix-1).mange();
        this.aliveTamagoshis.removeIf(t -> !t.consommeEnergie());
        System.out.println("Jouer avec quel Tamagochi ?");
        x = 1;
        for (Tamagoshi t : this.aliveTamagoshis){
            System.out.println("("+x+") "+t.getName());
            x++;
        }
        int choix2 = inputNbMax(this.aliveTamagoshis.size());
        this.aliveTamagoshis.get(choix2-1).joue();
        this.aliveTamagoshis.removeIf(t -> !t.consommeFun());
    }

    public int score(){
        int score;
        float scoreF = (float)this.aliveTamagoshis.size()/this.allTamagoshis.size();
        scoreF*=100;
        score = (int)scoreF;
        return score;
    }

    public void fin(){
        System.out.println("\n--------- Fin de partie ---------");
        System.out.println("------------- Bilan -------------\n");
        for (Tamagoshi t : this.allTamagoshis){
            boolean dead = true;
            for (Tamagoshi t1 : this.aliveTamagoshis){
                if(t.equals(t1)) {
                    System.out.println(t.getName()+ " a survécu et vous remercie :)");
                    dead = false;
                }
            }
            if(dead)
                System.out.println(t.getName()+" n'est pas arrivé au bout et ne vous félicite pas :(");
        }
        System.out.println("Score obtenu : "+score()+"%");
    }

    public static void main(String[] args) {
        TamaGame jeu = new TamaGame();
        jeu.play();
    }
}
