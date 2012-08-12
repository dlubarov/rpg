package rpg.math;

import rpg.util.ToStringBuilder;

/**
 * An axis-aligned bounding box.
 */
public class AAB extends Shape3D {
  public final Vector3 min, max;

  public AAB(Vector3 min, Vector3 max) {
    assert min.x < max.x && min.y < max.y && min.z < max.z;
    this.min = min;
    this.max = max;
  }

  public boolean intersects(AAB that) {
    if (min.x > that.max.x || max.x < that.min.x)
      return false;
    if (min.y > that.max.y || max.y < that.min.y)
      return false;
    if (min.z > that.max.z || max.z < that.min.z)
      return false;
    return true;
  }

  @Override public AAB getBoundingBox() {
    return this;
  }

  @Override public String toString() {
    return new ToStringBuilder(this)
        .append("min", min)
        .append("max", max)
        .toString();
  }
}
