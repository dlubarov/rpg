package rpg.core;

public enum CombatClass {
  WARRIOR("Warrior"),
  ARCHER("Archer"),
  MAGE("Mage"),
  PALADIN("Paladin");

  private final String name;

  private CombatClass(String name) {
    this.name = name;
  }

  @Override public String toString() {
    return name;
  }
}
