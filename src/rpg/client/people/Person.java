package rpg.client.people;

import rpg.math.Cylinder;
import rpg.math.Shape3D;
import rpg.phys.Body;
import rpg.phys.Positioned;

public abstract class Person implements Positioned, Body {
  protected abstract double getHeight();
  protected abstract double getRadius();

  public final Shape3D getShape() {
    return new Cylinder(getPos(), getHeight(), getRadius());
  }
}
