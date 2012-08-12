package rpg.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class StatRequirements {
  private final Map<Stat, Integer> levelRequirements;

  public StatRequirements() {
    levelRequirements = Collections.synchronizedMap(new HashMap<Stat, Integer>());
  }

  public Set<Stat> getAffectedStats() {
    return levelRequirements.keySet();
  }

  public int getRequirementFor(Stat stat) {
    return levelRequirements.get(stat);
  }

  public StatRequirements setRequirement(Stat stat, int requiredLevel) {
    levelRequirements.put(stat, requiredLevel);
    return this;
  }

  @Override public String toString() {
    StringBuilder sb = new StringBuilder("[");
    boolean first = true;
    for (Stat stat : levelRequirements.keySet()) {
      if (!first)
        sb.append(", ");
      sb.append(stat).append(" >= ").append(getRequirementFor(stat));
      first = false;
    }
    return sb.append(']').toString();
  }
}
