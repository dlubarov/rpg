package rpg.motion;

import rpg.math.Vector3;

public class PathMotion extends Motion {
  private final Vector3[] vertices;
  private final double startTime;
  private final double speed;

  public PathMotion(Vector3[] vertices, double startTime, double speed) {
    assert vertices.length >= 2;
    this.vertices = vertices;
    this.startTime = startTime;
    this.speed = speed;
  }

  @Override
  public Vector3 getPos(double time) {
    double distToTravel = (time - startTime) * speed;
    Vector3 vertex = vertices[0];
    for (int i = 1; i < vertices.length; ++i) {
      Vector3 next = vertices[i];
      double dist = vertex.euclideanDistanceTo(next);
      if (dist > distToTravel) {
        double progress = distToTravel / dist;
        return vertex.scaled(progress).plus(next.scaled(1 - progress));
      } else {
        distToTravel -= dist;
      }
      vertex = next;
    }
    // Completed the path!
    return vertex;
  }
}
