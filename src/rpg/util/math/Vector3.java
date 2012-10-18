package rpg.util.math;

import java.util.Arrays;

public final class Vector3 {
  public static final Vector3
      ZERO = new Vector3(0, 0, 0),
      UNIT_X = new Vector3(1, 0, 0),
      UNIT_Y = new Vector3(0, 1, 0),
      UNIT_Z = new Vector3(0, 0, 1);

  public final double x, y, z;

  public Vector3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double get(int d) {
    switch (d) {
      case 0: return x;
      case 1: return y;
      case 2: return z;
      default: throw new IllegalArgumentException();
    }
  }

  public double[] components() {
    return new double[] {x, y, z};
  }

  public Vector3 withX(double x) {
    return new Vector3(x, y, z);
  }

  public Vector3 withY(double y) {
    return new Vector3(x, y, z);
  }

  public Vector3 withZ(double z) {
    return new Vector3(x, y, z);
  }

  public Vector3 addX(double dx) {
    return new Vector3(x + dx, y, z);
  }

  public Vector3 addY(double dy) {
    return new Vector3(x, y + dy, z);
  }

  public Vector3 addZ(double dz) {
    return new Vector3(x, y, z + dz);
  }

  public Vector3 scaled(double s) {
    return new Vector3(s * x, s * y, s * z);
  }

  public Vector3 neg() {
    return new Vector3(-x, -y, -z);
  }

  public Vector3 plus(Vector3 that) {
    return new Vector3(x + that.x, y + that.y, z + that.z);
  }

  public Vector3 minus(Vector3 that) {
    return new Vector3(x - that.x, y - that.y, z - that.z);
  }

  public double euclideanDistanceTo(Vector3 that) {
    return minus(that).norm();
  }

  public Vector3 averagedWith(Vector3 that) {
    return plus(that).scaled(0.5);
  }

  public double dot(Vector3 that) {
    return x * that.x + y * that.y + z * that.z;
  }

  public double normSquared() {
    return x * x + y * y + z * z;
  }

  public double norm() {
    return Math.sqrt(normSquared());
  }

  public Vector3 normalize() {
    return scaled(1 / norm());
  }

  public double max() {
    return Math.max(x, Math.max(y, z));
  }

  public double min() {
    return Math.min(x, Math.min(y, z));
  }

  /**
   * Impose a limit on the norm of this vector, shortening it if necessary.
   */
  public Vector3 limitNorm(double maxNorm) {
    double norm = norm();
    if (norm > maxNorm)
      return scaled(maxNorm / norm);
    return this;
  }

  @Override public boolean equals(Object o) {
    if (!(o instanceof Vector3))
      return false;
    Vector3 that = (Vector3) o;
    return x == that.x && y == that.y && z == that.z;
  }

  @Override public int hashCode() {
    return Arrays.hashCode(components());
  }

  @Override public String toString() {
    return String.format("[%.2f, %.2f, %.2f]", x, y, z);
  }
}
