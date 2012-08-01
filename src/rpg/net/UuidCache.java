package rpg.net;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.lang.System.currentTimeMillis;

/**
 * Used to ensure that we don't process duplicate UUIDs.
 */
public final class UUIDCache {
  private UUIDCache() {}

  private static final int CAPACITY = 200;
  private static final long EXPIRATION_MS = 2000;

  // A map from UUID to time received.
  private static final Map<Long, Long> store = new LinkedHashMap<Long, Long>(CAPACITY, 1, false) {
    @Override
    protected boolean removeEldestEntry(Map.Entry<Long, Long> eldest) {
      return size() < CAPACITY;
    }
  };

  public static void addUUID(long uuid) {
    synchronized (store) {
      // LinkedHashMap doesn't reorder upon reinsertion, so we have to remove and then insert.
      store.remove(uuid);
      store.put(uuid, currentTimeMillis());
    }
  }

  public static boolean recentSawUUID(long uuid) {
    Long timeSeen;
    synchronized (store) {
      timeSeen = store.get(uuid);
    }
    return timeSeen != null && currentTimeMillis() - timeSeen < EXPIRATION_MS;
  }
}
