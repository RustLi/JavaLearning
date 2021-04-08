package utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class FixedCapacityMap {
    private static Map<Long, Long> fixedCapacityCache = Collections.synchronizedMap(new LRUHashMap<>());
    private static final int MAX_CAPACITY = 512;

    public static Long get(long key) {
        return fixedCapacityCache.get(key);
    }

    public static void put(long key, long value) {
        fixedCapacityCache.put(key, value);
    }

    public static boolean contains(long key){
        return fixedCapacityCache.containsKey(key);
    }

    public static Map getCache(){
        return fixedCapacityCache;
    }

    static class LRUHashMap<K, V> extends LinkedHashMap<K, V> {
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > MAX_CAPACITY;
        }
    }
}
