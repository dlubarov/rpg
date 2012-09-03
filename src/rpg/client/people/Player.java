package rpg.client.people;

import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.util.math.Vector3;

public abstract class Player extends Person {
  public final int id;
  public final String characterName;
  public final CombatClass combatClass;

  protected Player(int id, String characterName, CombatClass combatClass) {
    this.id = id;
    this.characterName = characterName;
    this.combatClass = combatClass;
  }

  public abstract MotionState getMotionState();

  @Override public final Vector3 getPos() {
    return getMotionState().position;
  }

  @Override protected final double getHeight() {
    return 1.8;
  }

  @Override protected final double getRadius() {
    return 0.3;
  }

  protected Vector3 getTip() {
    return getPos().plus(new Vector3(0, getHeight(), 0));
  }

  protected Vector3 getLeftFoot() {
    return getPos().plus(dirLeft().scaled(0.1));
  }

  protected Vector3 getRightFoot() {
    return getPos().plus(dirRight().scaled(0.1));
  }

  public Vector3 dirForward() {
    return getMotionState()
        .withPitch(0)
        .getDirectionVector();
  }

  public Vector3 dirLeft() {
    return getMotionState()
        .withPitch(0)
        .withYaw(getMotionState().yaw + Math.PI / 2)
        .getDirectionVector();
  }

  public Vector3 dirRight() {
    return getMotionState()
        .withPitch(0)
        .withYaw(getMotionState().yaw - Math.PI / 2)
        .getDirectionVector();
  }

  public Vector3 dirUp() {
    return getMotionState()
        .withPitch(getMotionState().pitch + Math.PI / 2)
        .getDirectionVector();
  }
}
