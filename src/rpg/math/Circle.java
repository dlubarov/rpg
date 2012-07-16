package rpg.math;

import rpg.util.ToStringBuilder;

public class Circle extends Shape2D {
  public final Vector2 center;
  public final double radius;

  public Circle(Vector2 center, double radius) {
    assert radius > 0;
    this.center = center;
    this.radius = radius;
  }

  public boolean intersects(Circle that) {
    double combinedRad = radius + that.radius;
    return center.minus(that.center).normSquared() < combinedRad * combinedRad;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
        .append("center", center)
        .append("radius", radius)
        .toString();
  }
}
