package rpg.client.people;

import rpg.core.CombatClass;

public abstract class Player extends Person {
  public final CombatClass combatClass;

  protected Player(CombatClass combatClass) {
    this.combatClass = combatClass;
  }

  @Override protected double getHeight() {
    return 1.8;
  }

  @Override protected double getRadius() {
    return 0.3;
  }
}
