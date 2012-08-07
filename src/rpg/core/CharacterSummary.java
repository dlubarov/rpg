package rpg.core;

import rpg.util.ToStringBuilder;

public class CharacterSummary {
  public final int characterID;
  public final String name;
  public final CombatClass combatClass;

  public CharacterSummary(int characterID, String name, CombatClass combatClass) {
    this.characterID = characterID;
    this.name = name;
    this.combatClass = combatClass;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("characterID", characterID)
        .append("name", name)
        .append("combatClass", combatClass)
        .toString();
  }
}
