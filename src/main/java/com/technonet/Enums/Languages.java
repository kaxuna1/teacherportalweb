package com.technonet.Enums;

import java.util.HashMap;
import java.util.Map;

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
    private static Map<Integer, Languages> map = new HashMap<>();
    static {
        for (Languages legEnum : Languages.values()) {
            map.put(legEnum.CODE, legEnum);
        }
    }

    public static Languages valueOf(int id) {
        return map.get(id);
    }

    public int getCODE() {
        return CODE;
    }
}
