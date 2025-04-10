package g4.group.allCardUtilities;

public class Unit extends Card {
    private Effect ef;
    private int imcount = 3;
    private int firecount = 3;
    private int intcont = 3;

    Unit(String name, int health, int damage, int cost, Effect ef){
        super(name, health, damage, cost);
        this.ef=ef;
    }
    Unit(String name, int health, int damage, int cost, String description, Effect ef) {
        super(name, health, damage, cost, description);
        this.ef=ef;
    }
    public Effect getEf() {
        return ef;
    }
    public String getEff() {
        return ef.toString();
    }
    public void setEf(Effect ef) {
        this.ef = ef;
    }
    public int getImcount() {
        return imcount;
    }
    public void setImcount(int imcount) {
        this.imcount = imcount;
    }
    public int getFirecount() {
        return firecount;
    }
    public void setFirecount(int firecount) {
        this.firecount = firecount;
    }
    public int getIntcont() {
        return intcont;
    }
    public void setIntcont(int intcont) {
        this.intcont = intcont;
    }
}
