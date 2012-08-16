package rpg.client.people;

import rpg.util.math.Cylinder;
import rpg.util.math.Shape3D;
import rpg.util.phys.Body;
import rpg.util.phys.Positioned;

public abstract class Person implements Positioned, Body {
  protected abstract double getHeight();
  protected abstract double getRadius();

  public final Shape3D getShape() {
    return new Cylinder(getPos(), getHeight(), getRadius());
  }
}
