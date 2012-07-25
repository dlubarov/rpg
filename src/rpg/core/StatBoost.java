package rpg.core;

import java.util.HashMap;
import java.util.Map;

public final class StatBoost {
  private final Map<CombatStat, Integer> statBoosts;

  public StatBoost() {
    statBoosts = new HashMap<CombatStat, Integer>();
  }

  public int getBoostFor(CombatStat stat) {
    return statBoosts.get(stat);
  }

  public StatBoost setBoost(CombatStat stat, int boost) {
    statBoosts.put(stat, boost);
    return this;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    boolean first = true;
    for (CombatStat stat : statBoosts.keySet()) {
      if (!first)
        sb.append(", ");
      sb.append(stat).append(" += ").append(getBoostFor(stat));
      first = false;
    }
    return sb.append(']').toString();
  }
}
