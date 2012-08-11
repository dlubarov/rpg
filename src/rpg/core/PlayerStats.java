package rpg.core;

import java.util.HashMap;
import java.util.Map;

public final class PlayerStats {
  private final Map<Stat, Integer> statLevels;

  public PlayerStats() {
    statLevels = new HashMap<Stat, Integer>();
    for (Stat stat : Stat.values())
      setLevel(stat, 0);
  }

  public synchronized int getLevel(Stat stat) {
    return statLevels.get(stat);
  }

  public synchronized boolean satisfies(StatRequirements requirements) {
    for (Stat stat : requirements.getAffectedStats())
      if (getLevel(stat) < requirements.getRequirementFor(stat))
        return false;
    return true;
  }

  public synchronized void setLevel(Stat stat, int level) {
    statLevels.put(stat, level);
  }
}
