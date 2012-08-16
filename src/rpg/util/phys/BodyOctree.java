package rpg.util.phys;

import java.util.ArrayList;
import java.util.Collection;
import rpg.util.math.AAB;
import rpg.util.math.Vector3;

public final class BodyOctree extends Broadphase {
  private static final int MIN_SIZE = 3, MAX_SIZE = 8;

  private final Vector3 center;
  private final double radius;
  private BodyOctree[] children = null;
  private final Collection<Body> bodies;
  private int size = 0;

  public BodyOctree(Vector3 center, double radius) {
    this.center = center;
    this.radius = radius;
    bodies = new ArrayList<Body>();
  }

  @Override public synchronized void add(Body b) {
    BodyOctree child = childFor(b);
    if (child == null)
      bodies.add(b);
    else
      child.add(b);

    if (++size > MAX_SIZE && !hasSplit())
      split();
  }

  @Override public synchronized void remove(Body b) {
    BodyOctree child = childFor(b);
    if (child == null)
      bodies.remove(b);
    else
      child.remove(b);

    if (--size < MIN_SIZE && hasSplit())
      unsplit();
  }

  @Override public synchronized Collection<Body> getNeighbors(Body src) {
    Collection<Body> result = new ArrayList<Body>();
    addNeighborsTo(src, result);
    return result;
  }

  private boolean hasSplit() {
    return children != null;
  }

  private void split() {
    double x = center.x, y = center.y, z = center.z, r = radius/2;
    children = new BodyOctree[] {
        new BodyOctree(new Vector3(x - r, y - r, z - r), r),
        new BodyOctree(new Vector3(x + r, y - r, z - r), r),
        new BodyOctree(new Vector3(x - r, y + r, z - r), r),
        new BodyOctree(new Vector3(x + r, y + r, z - r), r),
        new BodyOctree(new Vector3(x - r, y - r, z + r), r),
        new BodyOctree(new Vector3(x + r, y - r, z + r), r),
        new BodyOctree(new Vector3(x - r, y + r, z + r), r),
        new BodyOctree(new Vector3(x + r, y + r, z + r), r),
    };
    for (Body b : bodies) {
      BodyOctree child = childFor(b);
      if (child != null)
        child.add(b);
    }
  }

  private void unsplit() {
    for (BodyOctree child : children)
      child.addBodiesTo(bodies);
    children = null;
  }

  private void addBodiesTo(Collection<Body> result) {
    result.addAll(bodies);
    if (hasSplit())
      for (BodyOctree child : children)
        child.addBodiesTo(result);
  }

  private boolean onBoundary(Body b) {
    AAB bb = b.getShape().getBoundingBox();
    for (int d = 0; d < 3; ++d)
      if (bb.min.get(d) <= center.get(d) && center.get(d) <= bb.max.get(d))
        return true;
    return false;
  }

  private BodyOctree childFor(Body b) {
    if (!hasSplit() || onBoundary(b))
      return null;

    AAB bb = b.getShape().getBoundingBox();
    int idx = 0;
    for (int d = 0; d < 3; ++d)
      if (bb.min.get(d) > center.get(d))
        idx |= 1 << d;
    return children[idx];
  }

  private AAB boundingBox() {
    Vector3 rrr = new Vector3(radius, radius, radius);
    return new AAB(center.minus(rrr), center.plus(rrr));
  }

  private void addNeighborsTo(Body src, Collection<Body> result) {
    AAB srcBB = src.getShape().getBoundingBox();
    if (!boundingBox().intersects(srcBB))
      return;

    if (hasSplit())
      for (BodyOctree child : children)
        child.addNeighborsTo(src, result);
    else
      for (Body b : bodies)
        if (b != src && b.getShape().getBoundingBox().intersects(srcBB))
          result.add(b);
  }
}
