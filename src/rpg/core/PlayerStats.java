package rpg.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class PlayerStats {
  private final Map<CombatStat, Long> experienceByStat;

  public PlayerStats() {
    this.experienceByStat = Collections.synchronizedMap(new HashMap<CombatStat, Long>());
    setAllExperiences(0);
  }

  public long getExperience(CombatStat stat) {
    return experienceByStat.get(stat);
  }

  public int getLevel(CombatStat stat) {
    return Levels.getLevel(getExperience(stat));
  }

  public boolean satisfies(LevelRequirements requirements) {
    for (CombatStat stat : requirements.getAffectedStats())
      if (getLevel(stat) < requirements.getRequirementFor(stat))
        return false;
    return true;
  }

  public void setExperience(CombatStat stat, long experience) {
    experienceByStat.put(stat, experience);
  }

  public void addExperience(CombatStat stat, long experience) {
    setExperience(stat, getExperience(stat) + experience);
  }

  private void setAllExperiences(long experience) {
    for (CombatStat stat : CombatStat.values())
      setExperience(stat, experience);
  }
}
