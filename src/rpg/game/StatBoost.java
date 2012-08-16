package rpg.game;

import java.util.HashMap;
import java.util.Map;

public final class StatBoost {
  private final Map<Stat, Integer> statBoosts;

  public StatBoost() {
    statBoosts = new HashMap<Stat, Integer>();
  }

  public int getBoostFor(Stat stat) {
    return statBoosts.get(stat);
  }

  public StatBoost setBoost(Stat stat, int boost) {
    statBoosts.put(stat, boost);
    return this;
  }

  @Override public String toString() {
    StringBuilder sb = new StringBuilder("[");
    boolean first = true;
    for (Stat stat : statBoosts.keySet()) {
      if (!first)
        sb.append(", ");
      sb.append(stat).append(" += ").append(getBoostFor(stat));
      first = false;
    }
    return sb.append(']').toString();
  }
}
