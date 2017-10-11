package model;

public enum BrickDifficulty {
    GLASS (1) ,
    WOOD (2),
    ROCK (3) ,
    METAL (4),
    DIAMOND (5);

    int hits;

    private BrickDifficulty(int hits) {
        this.hits = hits;
    }
}
