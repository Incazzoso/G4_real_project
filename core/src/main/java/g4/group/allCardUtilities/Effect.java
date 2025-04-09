package g4.group.allCardUtilities;

public class Effect {
    private boolean onFire;
    private boolean penetrated;
    private boolean chaiFire;
    private boolean multiTarget;
    private boolean reinforced;
    private boolean vengence;
    private boolean sinkeing;
    private boolean advanceManouver;
    private boolean interference;
    private boolean airAttack;
    private boolean isOnFire = false;
    private boolean isPenetrated = false;
    private boolean isReinforced = false;
    private boolean isVengence = false;
    private boolean isSinkeing = false;
    private boolean isInterference = false;

    public Effect(boolean onFire, boolean penetrated, boolean chaiFire, boolean multiTarget, boolean reinforced, boolean vengence, boolean sinkeing, boolean advanceManouver, boolean interference, boolean airAttack) {
        this.onFire = onFire;
        this.penetrated = penetrated;
        this.chaiFire = chaiFire;
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
        return penetrated;
    }

    public void setPenetrated(boolean penetrated) {
        this.penetrated = penetrated;
    }

    public boolean isChaiFire() {
        return chaiFire;
    }

    public void setChaiFire(boolean chaiFire) {
        this.chaiFire = chaiFire;
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
        this.isOnFire = isOnFire;
    }

    public boolean isisPenetrated() {
        return isPenetrated;
    }

    public void setisPenetrated(boolean isPenetrated) {
        this.isPenetrated = isPenetrated;
    }

    public boolean isisReinforced() {
        return isReinforced;
    }

    public void setIsReinforced(boolean isReinforced) {
        this.isReinforced = isReinforced;
    }

    public boolean isisVengence() {
        return isVengence;
    }

    public void setisVengence(boolean isVengence) {
        this.isVengence = isVengence;
    }

    public boolean isisSinkeing() {
        return isSinkeing;
    }

    public void setisSinkeing(boolean onFire) {
        this.isSinkeing = isSinkeing;
    }

    public boolean isisInterference() {
        return isInterference;
    }

    public void setisInterference(boolean onFire) {
        this.isInterference = isInterference;
    }
}
