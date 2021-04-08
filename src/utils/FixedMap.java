package utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class FixedMap extends LinkedHashMap {
    private static final int MAX_CAPACITY = 4;

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_CAPACITY;
    }
}
