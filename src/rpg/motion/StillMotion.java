package rpg.motion;

import rpg.math.Vector3;

public class StillMotion extends Motion {
  private final Vector3 position;

  public StillMotion(Vector3 position) {
    this.position = position;
  }

  @Override
  public Vector3 getPos(double time) {
    return position;
  }
}
