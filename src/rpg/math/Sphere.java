package rpg.math;

import rpg.util.ToStringBuilder;

public class Sphere extends Shape3D {
  public final Vector3 center;
  public final double radius;

  public Sphere(Vector3 center, double radius) {
    this.center = center;
    this.radius = radius;
  }

  public boolean intersects(Sphere that) {
    double combinedRad = radius + that.radius;
    return center.minus(that.center).normSquared() < combinedRad * combinedRad;
  }

  @Override
  public AAB getBoundingBox() {
    Vector3 rrr = new Vector3(radius, radius, radius);
    return new AAB(center.minus(rrr), center.plus(rrr));
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("center", center)
        .append("radius", radius)
        .toString();
  }
}
