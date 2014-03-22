package splumb.common.logging;

import com.google.common.collect.BiMap;

import static com.google.common.collect.ImmutableBiMap.of;

public enum Level {
    DEBUG,
    INFO,
    ERROR;

    private static final BiMap<String, Level> values = of("DEBUG", DEBUG, "INFO", INFO, "ERROR", ERROR);

    public static Level fromStr(String val) {
        return values.get(val);
    }
}
