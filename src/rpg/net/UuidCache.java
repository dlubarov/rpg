package rpg.net;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Used to ensure that we don't process duplicate UUIDs.
 */
public final class UuidCache {
  private UuidCache() {}

  private static final int CAPACITY = 200;
  private static final long EXPIRATION_MS = 2000;

  // A map from UUID to time received.
  private static final Map<Long, Long> store = Collections.synchronizedMap(
      new LinkedHashMap<Long, Long>(CAPACITY, 1, false) {
    @Override
    protected boolean removeEldestEntry(Map.Entry<Long, Long> eldest) {
      return size() < CAPACITY;
    }
  });

  public static boolean recentSawUuid(long uuid) {
    Long timeSeen = store.get(uuid);
    return timeSeen != null && System.currentTimeMillis() - timeSeen < EXPIRATION_MS;
  }
}
