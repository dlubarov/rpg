package rpg.client.people;

import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.game.shape.HumanShape;
import rpg.util.math.Vector3;

public abstract class Player extends Creature {
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

  @Override public final double getHeight() {
    return 1.8;
  }

  @Override public final double getRadius() {
    return 0.3;
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

  public HumanShape getHumanShape() {
    return new HumanShape(getPos(), getHeight(), getRadius(), getMotionState().yaw);
  }
}
