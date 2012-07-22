package rpg.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class PlayerStats {
  private final Map<Stat, Long> experienceByStat;

  public PlayerStats() {
    this.experienceByStat = Collections.synchronizedMap(new HashMap<Stat, Long>());
    setAllExperiences(0);
  }

  public long getExperience(Stat stat) {
    return experienceByStat.get(stat);
  }

  public int getLevel(Stat stat) {
    return Levels.getLevel(getExperience(stat));
  }

  public boolean satisfies(LevelRequirements requirements) {
    for (Stat stat : requirements.getAffectedStats())
      if (getLevel(stat) < requirements.getRequirementFor(stat))
        return false;
    return true;
  }

  public void setExperience(Stat stat, long experience) {
    experienceByStat.put(stat, experience);
  }

  public void addExperience(Stat stat, long experience) {
    setExperience(stat, getExperience(stat) + experience);
  }

  private void setAllExperiences(long experience) {
    for (Stat stat : Stat.values())
      setExperience(stat, experience);
  }
}
