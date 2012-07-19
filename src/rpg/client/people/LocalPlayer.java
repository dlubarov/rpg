package rpg.client.people;

import rpg.core.CombatClass;
import rpg.math.Vector3;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class LocalPlayer extends Player {
  private Vector3 position, velocity, direction;

  protected LocalPlayer(CombatClass combatClass) {
    super(combatClass);
  }

  @Override
  public Vector3 getPos() {
    return position;
  }
}
