package rpg.math;

import java.util.Arrays;

public final class Vector2 {
  public static final Vector2
      ZERO = new Vector2(0, 0),
      UNIT_X = new Vector2(1, 0),
      UNIT_Y = new Vector2(0, 1);

  public final double x, y;

  public Vector2(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double get(int d) {
    switch (d) {
      case 0: return x;
      case 1: return y;
      default: throw new IllegalArgumentException();
    }
  }

  public double[] components() {
    return new double[] {x, y};
  }

  public Vector2 withX(double x) {
    return new Vector2(x, y);
  }

  public Vector2 withY(double y) {
    return new Vector2(x, y);
  }

  public Vector2 scaled(double s) {
    return new Vector2(s * x, s * y);
  }

  public Vector2 neg() {
    return new Vector2(-x, -y);
  }

  public Vector2 plus(Vector2 that) {
    return new Vector2(x + that.x, y + that.y);
  }

  public Vector2 minus(Vector2 that) {
    return new Vector2(x - that.x, y - that.y);
  }

  public double euclideanDistanceTo(Vector2 that) {
    return minus(that).norm();
  }

  public Vector2 averagedWith(Vector2 that) {
    return plus(that).scaled(0.5);
  }

  public double dot(Vector2 that) {
    return x * that.x + y * that.y;
  }

  public double normSquared() {
    return x * x + y * y;
  }

  public double norm() {
    return Math.sqrt(normSquared());
  }

  public Vector2 normalize() {
    return scaled(1 / norm());
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Vector2))
      return false;
    Vector2 that = (Vector2) o;
    return x == that.x && y == that.y;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(components());
  }

  @Override
  public String toString() {
    return String.format("[%.2f, %.2f]", x, y);
  }
}
