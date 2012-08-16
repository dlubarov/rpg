package rpg.util.math;

/**
 * A cylinder oriented along the y-axis.
 */
public class Cylinder extends Shape3D {
  public final Vector3 base;
  public final double height, radius;

  public Cylinder(Vector3 base, double height, double radius) {
    assert height > 0 && radius > 0;
    this.base = base;
    this.height = height;
    this.radius = radius;
  }

  public boolean intersects(Cylinder that) {
    if (base.y > that.base.y + that.height)
      return false;
    if (base.y + height < that.base.y)
      return false;
    return xyProjection().intersects(that.xyProjection());
  }

  private Circle xyProjection() {
    return new Circle(new Vector2(base.x, base.y), radius);
  }

  @Override public AAB getBoundingBox() {
    return null;
  }
}
