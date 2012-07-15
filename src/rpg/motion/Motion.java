package rpg.motion;

import rpg.math.Vector3;

public abstract class Motion {
  protected abstract Vector3 getPos(double time);

  public final Vector3 getPos() {
    return getPos(System.currentTimeMillis() * 1e-6);
  }
}
