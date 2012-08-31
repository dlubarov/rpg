package rpg.net;

import java.util.LinkedHashMap;
import java.util.Map;
import rpg.util.Timing;

/**
 * Used to ensure that we don't process duplicate UUIDs.
 */
public final class UUIDCache {
  private UUIDCache() {}

  private static final int CAPACITY = 200;
  private static final double EXPIRATION_DT = 3;

  // A map from UUID to time received.
  private static final Map<Long, Double> store = new LinkedHashMap<Long, Double>(CAPACITY, 1, false) {
    @Override protected boolean removeEldestEntry(Map.Entry<Long, Double> eldest) {
      return size() < CAPACITY;
    }
  };

  public static void addUUID(long uuid) {
    synchronized (store) {
      // LinkedHashMap doesn't reorder upon reinsertion, so we have to remove and then insert.
      store.remove(uuid);
      store.put(uuid, Timing.currentTime());
    }
  }

  public static boolean recentlySawUUID(long uuid) {
    Double timeSeen;
    synchronized (store) {
      timeSeen = store.get(uuid);
    }
    return timeSeen != null && Timing.currentTime() - timeSeen < EXPIRATION_DT;
  }
}
