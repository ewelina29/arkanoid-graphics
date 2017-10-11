package model;

public enum PlatformLength {

    SHORT(1),
    MEDIUM(2),
    LONG(3);

    int length;

    private PlatformLength(int length) {
        this.length = length;
    }

}
