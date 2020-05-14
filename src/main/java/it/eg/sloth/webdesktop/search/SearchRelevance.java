package it.eg.sloth.webdesktop.search;

public enum SearchRelevance {
    VERY_LOW(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    VERY_HIGH(4);

    int level;

    SearchRelevance(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }

}