package com.technonet.Enums;

/**
 * Created by vakhtanggelashvili on 3/26/17.
 */
public enum Languages {
    ENG(1),
    GEO(2),
    RUS(3);

    private int CODE;

    Languages(int i) {
        this.CODE = i;
    }

    public int getCODE() {
        return CODE;
    }
}
