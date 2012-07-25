package rpg.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class LevelRequirements {
  private final Map<CombatStat, Integer> levelRequiremens;

  public LevelRequirements() {
    levelRequiremens = Collections.synchronizedMap(new HashMap<CombatStat, Integer>());
  }

  public Set<CombatStat> getAffectedStats() {
    return levelRequiremens.keySet();
  }

  public int getRequirementFor(CombatStat stat) {
    return levelRequiremens.get(stat);
  }

  public LevelRequirements setRequirement(CombatStat stat, int requiredLevel) {
    levelRequiremens.put(stat, requiredLevel);
    return this;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    boolean first = true;
    for (CombatStat stat : levelRequiremens.keySet()) {
      if (!first)
        sb.append(", ");
      sb.append(stat).append(" >= ").append(getRequirementFor(stat));
      first = false;
    }
    return sb.append(']').toString();
  }
}
