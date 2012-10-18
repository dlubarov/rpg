package rpg.client.gfx.obj;

import rpg.client.gfx.GLUtil;
import rpg.util.math.Sphere;
import rpg.util.math.Vector3;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;

public class SphereRenderer {
  // TODO: Make sphere, numStacks, numSlices locals.
  private final Sphere sphere;
  private final int numStacks;
  private final int numSlices;
  private final Vector3[] vertices;
  private final int[] indices;

  public SphereRenderer(Sphere sphere, int numStacks, int numSlices) {
    this.sphere = sphere;
    this.numStacks = numStacks;
    this.numSlices = numSlices;
    vertices = generateVertices();
    indices = generateIndices();
  }

  public SphereRenderer(Sphere sphere) {
    this(sphere, 8, 8);
  }

  public Vector3[] generateVertices() {
    Vector3[] vertices = new Vector3[(numStacks + 1) * numSlices];
    int i = 0;
    for (int stack = 0; stack <= numStacks; ++stack) {
      for (int slice = 0; slice < numSlices; ++slice) {
        double theta = stack * (PI / numStacks);
        double phi = slice * 2 * (PI / numSlices);

        double sinTheta = sin(theta);
        double sinPhi = sin(phi);
        double cosTheta = cos(theta);
        double cosPhi = cos(phi);

        double dx = sphere.radius * cosPhi * sinTheta;
        double dy = sphere.radius * sinPhi * sinTheta;
        double dz = sphere.radius * cosTheta;
        vertices[i++] = sphere.center.plus(new Vector3(dx, dy, dz));
      }
    }
    assert i == vertices.length;
    return vertices;
  }

  private int[] generateIndices() {
    int[] indices = new int[numStacks * (numSlices + 1) * 2];
    int i = 0;
    for (int stack = 0; stack < numStacks; ++stack) {
      for (int slice = 0; slice <= numSlices; ++slice) {
        indices[i++] = (stack + numSlices) + (slice % numSlices);
        indices[i++] = ((stack + 1) + numSlices) + (slice % numSlices);
      }
    }
    assert i == indices.length;
    return indices;
  }

  public void render() {
    glBegin(GL_TRIANGLE_STRIP);
    for (int index : indices)
      GLUtil.glVertex(vertices[index]);
    glEnd();
  }
}
