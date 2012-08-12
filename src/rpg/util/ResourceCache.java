package rpg.util;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ResourceCache<T> {
  private final Map<String, T> map;

  public ResourceCache(int capacity) {
    map = new FixedCapacityLinkedHashMap<String, T>(capacity);
  }

  public synchronized final T get(String descriptor) {
    T resource = map.get(descriptor);
    if (resource == null)
      map.put(descriptor, resource = loadResource(descriptor));
    return resource;
  }

  protected abstract T loadResource(String descriptor);

  private static class FixedCapacityLinkedHashMap<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    private FixedCapacityLinkedHashMap(int capacity) {
      super(capacity / 2, 1, false);
      this.capacity = capacity;
    }

    @Override public boolean removeEldestEntry(Map.Entry<K, V> entry) {
      return size() >= capacity;
    }
  }
}
