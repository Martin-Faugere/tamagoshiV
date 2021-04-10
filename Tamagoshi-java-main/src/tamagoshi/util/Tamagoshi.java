package tamagoshi.util;

import java.util.Random;

public class Tamagoshi {
    private int age;
    private int maxEnergy;
    protected int energy;
    private String name;
    private Random random;
    protected int fun;
    private int maxFun;
    private static int lifeTime = 10;
    private TamaJPanel monPanel;
    private TamaFrame maframe;
    private int pause;

    public Tamagoshi(String name) {
        this.age = 0;
        this.maxEnergy = (int)(Math.random()*(9-5)+5);
        setEnergy((int)(Math.random()*(7-3)+3));
        this.maxFun=(int)(Math.random()*(9-5)+5);
        setFun((int)(Math.random()*(7-3)+3));
        this.name = name;
    }

    public static void setLifeTime(int i) {
        lifeTime=i;
    }

    public String getName() {
        return name;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public int getMaxFun(){ return maxFun;}

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public static int getLifeTime() {
        return lifeTime;
    }

    public void addEnergy(int energy) {
        if(this.maxEnergy>=this.energy+energy)
            this.energy += energy;
        else this.energy = this.maxEnergy;
    }

    public void addFun(int fun){
        this.fun+=fun;
    }

    public int getFun() {
        return fun;
    }

    public void setFun(int fun){
        if(this.maxFun>=fun)
            this.fun = fun;
        else this.fun = this.maxFun;
    }

    public void setEnergy(int energy) {
        if(this.maxEnergy>=energy)
            this.energy = energy;
        else this.energy = this.maxEnergy;
    }

    private void parler(String phrase, boolean reponse) {
        System.out.println("\t" + this.name + " : \"" + phrase + "\"");
        if (this.monPanel != null) {
            if (reponse) {
                this.monPanel.phraseReponse(phrase);
            } else {
                this.monPanel.phraseEtat(phrase, this.age);
            }
        }
    }

    public boolean parle() {
        String s = "";
        if (this.energy < 5)
            s = String.valueOf(s) + "j'ai faim";
        if (this.fun < 5) {
            if (!s.equals(""))
                s = String.valueOf(s) + " et ";
            s = String.valueOf(s) + "je m'ennuie";
        }
        if (s.equals("")) {
            parler("Je vais très bien!!!", false);
            return true;
        }
        parler(String.valueOf(s) + " !!!", false);
        return false;
    }

    public boolean parleNourriture(){
        boolean heureux = false;
        if(this.getEnergy()>4){
            parler("Je suis heureux!",false);
            heureux = true;
        }
        else
            parler("Je suis affamé!",false);
        return heureux;
    }

    public boolean parleAmusement(){
        boolean amuse = false;
        if(this.getEnergy()>4){
            parler("Je m'amuse!",false);
            amuse = true;
        }
        else
            parler("Je m'ennuie!",false);
        return amuse;
    }

    public boolean mange(){
        boolean mange = true;
        if(this.getEnergy()<this.getMaxEnergy()) {
            this.addEnergy((int) (Math.random() * (4 - 2)+2));
            parler(this.getName()+" a bien mangé!!!",false);
        }
        else {
            mange = false;
            parler(this.getName()+" n'a pas faim!!!",false);
        }
        return mange;
    }

    public boolean joue(){
        boolean joue = true;
        if(this.getFun()<this.getMaxFun()) {
            this.addFun((int) (Math.random() * (4 - 2)+2));
            parler(this.getName()+" a bien joué!!!",false);
        }
        else {
            joue = false;
            parler(this.getName()+" fait autre chose!!!",false);
        }
        return joue;
    }

    public void setPanel(TamaJPanel tamaPanel) {
        this.monPanel = tamaPanel;
        parleNourriture();
    }

    public void setInitialPause(int i) {
        this.pause = i;
    }

    public boolean consommeEnergie(){
        if(this.getEnergy()<1){
            parler(this.getName()+" est KO",false);
            return false;
        }
        else{
            this.addEnergy(-1);
            return true;
        }
    }

    public boolean consommeFun(){
        if(this.getFun()<1){
            parler(this.getName()+" est dépressif",false);
            return false;
        }
        else{
            this.addFun(-1);
            return true;
        }
    }

    public boolean isAlive() {
        return (this.fun > 0 && this.energy > 0);
    }

    public TamaFrame getMaframe() {
        return this.maframe;
    }

    public void setMaframe(TamaFrame maframe) {
        this.maframe = maframe;
    }

    public boolean vieillit() {
        this.age++;
        return (this.age == getLifeTime());
    }

    public void diminuerPause(int i) {
        this.pause -= i;
    }

    public static void main(String args[]){
        TamagoshiMoi t1  = new TamagoshiMoi("T1");
        System.out.println("energie actuelle est "+t1.getEnergy());
        System.out.println("max energie est "+t1.getMaxEnergy());
        System.out.println(t1.parleNourriture());
        System.out.println(t1.mange());
        System.out.println(t1.consommeEnergie());
    }
}


