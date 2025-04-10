package g4.group.allCardUtilities;

public class Effect {
    private boolean onFire;
    private boolean piercing;
    private boolean chainFire;
    private boolean multiTarget;
    private boolean reinforced;
    private boolean vengence;
    private boolean sinkeing;
    private boolean advanceManouver;
    private boolean interference;
    private boolean airAttack;
    private boolean isOnFire = false;
    private boolean isSinkeing = false;
    private boolean isInterference = false;

    public Effect(boolean onFire, boolean piercing, boolean chainFire, boolean multiTarget, boolean reinforced, boolean vengence, boolean sinkeing, boolean advanceManouver, boolean interference, boolean airAttack) {
        this.onFire = onFire;
        this.piercing = piercing;
        this.chainFire = chainFire;
        this.multiTarget = multiTarget;
        this.reinforced = reinforced;
        this.vengence = vengence;
        this.sinkeing = sinkeing;
        this.advanceManouver = advanceManouver;
        this.interference = interference;
        this.airAttack = airAttack;
    }

    public boolean isOnFire() {
        return onFire;
    }

    public void setOnFire(boolean onFire) {
        this.onFire = onFire;
    }

    public boolean isPenetrated() {
        return piercing;
    }

    public void setPenetrated(boolean penetrated) {
        this.piercing = penetrated;
    }

    public boolean isChainFire() {
        return chainFire;
    }

    public void setChainFire(boolean chainFire) {
        this.chainFire = chainFire;
    }

    public boolean isMultiTarget() {
        return multiTarget;
    }

    public void setMultiTarget(boolean multiTarget) {
        this.multiTarget = multiTarget;
    }

    public boolean isReinforced() {
        return reinforced;
    }

    public void setReinforced(boolean reinforced) {
        this.reinforced = reinforced;
    }

    public boolean isVengence() {
        return vengence;
    }

    public void setVengence(boolean vengence) {
        this.vengence = vengence;
    }

    public boolean isSinkeing() {
        return sinkeing;
    }

    public void setSinkeing(boolean sinkeing) {
        this.sinkeing = sinkeing;
    }

    public boolean isAdvanceManouver() {
        return advanceManouver;
    }

    public void setAdvanceManouver(boolean advanceManouver) {
        this.advanceManouver = advanceManouver;
    }

    public boolean isInterference() {
        return interference;
    }

    public void setInterference(boolean interference) {
        this.interference = interference;
    }

    public boolean isAirAttack() {
        return airAttack;
    }

    public void setAirAttack(boolean airAttack) {
        this.airAttack = airAttack;
    }
    public boolean isisOnFire() {
        return isOnFire;
    }

    public void setIsOnFire(boolean isOnFire) {
        this.isOnFire=isOnFire;
    }

    public boolean isisSinkeing() {
        return isSinkeing;
    }

    public void setisSinkeing(boolean isSinkeing) {
        this.isSinkeing=isSinkeing;
    }

    public boolean isisInterference() {
        return isInterference;
    }

    public void setisInterference(boolean isInterference) {
        this.isInterference=isInterference;
    }

    @Override
    public String toString() {
        return "Effect{" +
            "onFire=" + onFire +
            ", piercing=" + piercing +
            ", chainFire=" + chainFire +
            ", multiTarget=" + multiTarget +
            ", reinforced=" + reinforced +
            ", vengence=" + vengence +
            ", sinkeing=" + sinkeing +
            ", advanceManouver=" + advanceManouver +
            ", interference=" + interference +
            ", airAttack=" + airAttack +
            ", isOnFire=" + isOnFire +
            ", isSinkeing=" + isSinkeing +
            ", isInterference=" + isInterference +
            '}';
    }
}
