package rpg.core;

import java.util.HashMap;
import java.util.Map;

public final class PlayerStats {
  private final Map<Stat, Integer> statExperiences;

  public PlayerStats() {
    statExperiences = new HashMap<Stat, Integer>();
    for (Stat stat : Stat.values())
      setExperience(stat, 0);
  }

  public synchronized int getExperience(Stat stat) {
    return statExperiences.get(stat);
  }

  public synchronized int getLevel(Stat stat) {
    return Levels.experienceToLevel(getExperience(stat));
  }

  public synchronized boolean satisfies(StatRequirements requirements) {
    for (Stat stat : requirements.getAffectedStats())
      if (getLevel(stat) < requirements.getRequirementFor(stat))
        return false;
    return true;
  }

  public synchronized void setExperience(Stat stat, int experience) {
    statExperiences.put(stat, experience);
  }

  public synchronized void addExperience(Stat stat, int experience) {
    setExperience(stat, getExperience(stat) + experience);
  }
}
