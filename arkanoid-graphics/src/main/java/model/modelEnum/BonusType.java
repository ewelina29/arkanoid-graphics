package model.modelEnum;

public enum BonusType {

    FASTER_BALL (1),
    SLOWER_BALL (2),
    LONGER_PLATFORM (3),
    SHORTER_PLATFORM (4),
    EXTRA_LIFE (5),
    EXTRA_POINTS (6),
    ALL_DIAMOND(7),
    ALL_GLASS(8);

    public int value;

    BonusType(int value){
        this.value = value;
    }
}
