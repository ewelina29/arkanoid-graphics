package model.modelEnum;

public enum BrickDifficulty {

    NONE (0),
    GLASS (1) ,
    WOOD (2),
    ROCK (3) ,
    METAL (4),
    DIAMOND (5);

    public int hits;

    BrickDifficulty(int hits) {
        this.hits = hits;
    }
}
