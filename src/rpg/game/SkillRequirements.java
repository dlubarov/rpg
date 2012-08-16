package rpg.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class SkillRequirements {
  private final Map<Skill, Integer> levelRequirements;

  public SkillRequirements() {
    levelRequirements = Collections.synchronizedMap(new HashMap<Skill, Integer>());
  }

  public Set<Skill> getAffectedSkills() {
    return levelRequirements.keySet();
  }

  public int getRequirementFor(Skill skill) {
    return levelRequirements.get(skill);
  }

  public SkillRequirements setRequirement(Skill skill, int requiredLevel) {
    levelRequirements.put(skill, requiredLevel);
    return this;
  }

  @Override public String toString() {
    StringBuilder sb = new StringBuilder("[");
    boolean first = true;
    for (Skill skill : levelRequirements.keySet()) {
      if (!first)
        sb.append(", ");
      sb.append(skill).append(" >= ").append(getRequirementFor(skill));
      first = false;
    }
    return sb.append(']').toString();
  }
}
