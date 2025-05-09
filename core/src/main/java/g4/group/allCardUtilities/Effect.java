package g4.group.allCardUtilities;

public class Effect {
    private boolean  burnOther;
    private boolean piercing;
    private boolean onFire;

    public Effect(boolean burnOther, boolean piercing){
        this.burnOther = burnOther;
        this.piercing = piercing;
        onFire = false;
    }

    //--------------------------------------getter--------------------------------------

    //restituisce se l'elemento "va a fuoco"
    public boolean isOnFire(){
        return onFire;
    }

    //restituisce se l'elemento "brucia gli altri"
    public boolean canBurnOther(){
        return burnOther;
    }

    //restituisce se l'elemento "trapassa gli altri (doppio danno)"
    public boolean canPiercing(){
        return burnOther;
    }

    //--------------------------------------setter--------------------------------------

    //setta lo stato "sto andando a fuoco"
    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

    //setta lo stato "posso da fuoco ad altri"
    public void setBurnOther(boolean burnOther) {
        this.burnOther = burnOther;
    }

    //setta lo stato "posso fare doppio danno"
    public void setPiercing(boolean piercing) {
        this.piercing = piercing;
    }
}
