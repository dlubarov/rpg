package rpg.core;

import rpg.util.ToStringBuilder;

public class CharacterSummary {
  public final String name;
  public final CombatClass combatClass;

  public CharacterSummary(String name, CombatClass combatClass) {
    this.name = name;
    this.combatClass = combatClass;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("name", name)
        .append("combatClass", combatClass)
        .toString();
  }
}
