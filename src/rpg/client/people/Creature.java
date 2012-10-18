package rpg.client.people;

import rpg.util.math.Cylinder;
import rpg.util.math.Shape3D;
import rpg.util.phys.Body;
import rpg.util.phys.Positioned;

public abstract class Creature implements Positioned, Body {
  public abstract double getHeight();
  public abstract double getRadius();

  public final Shape3D getShape() {
    return new Cylinder(getPos(), getHeight(), getRadius());
  }
}
