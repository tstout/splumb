package splumb.common.logging;

import com.google.common.collect.BiMap;

import static com.google.common.collect.ImmutableBiMap.of;

public enum Level {
    DEBUG,
    INFO,
    ERROR;

    private static final BiMap<Integer, Level> values = of(0, DEBUG, 1, INFO, 2, ERROR);

    public static Level fromInt(int val) {
        return values.get(val);
    }
}
