package rpg.game;

import java.util.HashMap;
import java.util.Map;

public final class PlayerSkills {
  private final Map<Skill, Integer> skillExperiences;

  public PlayerSkills() {
    skillExperiences = new HashMap<Skill, Integer>();
    for (Skill skill : Skill.values())
      setExperience(skill, 0);
  }

  public synchronized int getExperience(Skill skill) {
    return skillExperiences.get(skill);
  }

  public synchronized int getLevel(Skill skill) {
    return Levels.experienceToLevel(getExperience(skill));
  }

  public synchronized boolean satisfies(SkillRequirements requirements) {
    for (Skill skill : requirements.getAffectedSkills())
      if (getLevel(skill) < requirements.getRequirementFor(skill))
        return false;
    return true;
  }

  public synchronized void setExperience(Skill skill, int experience) {
    skillExperiences.put(skill, experience);
  }

  public synchronized void addExperience(Skill skill, int experience) {
    setExperience(skill, getExperience(skill) + experience);
  }
}
