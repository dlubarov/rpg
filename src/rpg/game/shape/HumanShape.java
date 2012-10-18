package rpg.game.shape;

import rpg.util.math.AAB;
import rpg.util.math.Cylinder;
import rpg.util.math.Shape3D;
import rpg.util.math.Sphere;
import rpg.util.math.Vector3;

public final class HumanShape extends Shape3D {
  private static final double HEAD_RADIUS = 10e-2 * 3; // 10 cm = big head!

  private final Vector3 base;
  private final double height;
  private final double radius;
  private final double yaw;

  public HumanShape(Vector3 base, double height, double radius, double yaw) {
    this.base = base;
    this.height = height;
    this.radius = radius;
    this.yaw = yaw;
  }

  public Vector3 getHeadTop() {
    return base.addY(height);
  }

  public Vector3 getHeadCenter() {
    return getHeadTop().addY(-HEAD_RADIUS);
  }

  public Sphere getHead() {
    return new Sphere(getHeadCenter(), HEAD_RADIUS);
  }

  public Vector3 getLeftFoot() {
    return base.plus(getDirLeft().scaled(0.1));
  }

  public Vector3 getRightFoot() {
    return base.plus(getDirRight().scaled(0.1));
  }

  public Vector3 getDirLeft() {
    double yaw = this.yaw + Math.PI / 2;
    double x = Math.cos(yaw), z = -Math.sin(yaw);
    return new Vector3(x, 0, z);
  }

  public Vector3 getDirRight() {
    double yaw = this.yaw - Math.PI / 2;
    double x = Math.cos(yaw), z = -Math.sin(yaw);
    return new Vector3(x, 0, z);
  }

  public AAB getBoundingBox() {
    return new Cylinder(base, height, radius).getBoundingBox();
  }
}
