package android.util;

import java.util.HashMap;

public class SparseArray<V> extends HashMap<Integer, V> {
    public V get(int key) {
        return super.get(key);
    }

    public void put(int key, V value) {
        super.put(key, value);
    }
}
