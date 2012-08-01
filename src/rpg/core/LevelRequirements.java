package rpg.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class LevelRequirements {
  private final Map<CombatStat, Integer> levelRequirements;

  public LevelRequirements() {
    levelRequirements = Collections.synchronizedMap(new HashMap<CombatStat, Integer>());
  }

  public Set<CombatStat> getAffectedStats() {
    return levelRequirements.keySet();
  }

  public int getRequirementFor(CombatStat stat) {
    return levelRequirements.get(stat);
  }

  public LevelRequirements setRequirement(CombatStat stat, int requiredLevel) {
    levelRequirements.put(stat, requiredLevel);
    return this;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    boolean first = true;
    for (CombatStat stat : levelRequirements.keySet()) {
      if (!first)
        sb.append(", ");
      sb.append(stat).append(" >= ").append(getRequirementFor(stat));
      first = false;
    }
    return sb.append(']').toString();
  }
}
