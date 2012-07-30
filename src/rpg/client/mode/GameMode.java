package rpg.client.mode;

import rpg.client.gfx.GraphicsMode;
import rpg.math.AAB;
import rpg.math.Vector3;
import rpg.phys.BodyOctree;
import rpg.realm.Realm;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3i;

public class GameMode extends Mode {
  private Realm realm;
  private BodyOctree octree;

  public GameMode(Realm realm) {
    setRealm(realm);
  }

  public Realm getRealm() {
    return realm;
  }

  public void setRealm(Realm realm) {
    this.realm = realm;
    AAB bb = realm.boundingBox;
    Vector3 center = bb.min.averagedWith(bb.max);
    Vector3 size = bb.max.minus(bb.min);
    octree = new BodyOctree(center, size.max() / 2);
  }

  @Override
  public void render() {
    GraphicsMode.end2D();
    drawScene();
    GraphicsMode.start2D();
    drawHUD();
  }

  private void drawScene() {
    glColor3f(1, 0, 0);
    glBegin(GL_TRIANGLES);
    glVertex3i(0, 0, 0);
    glVertex3i(2, 0, 0);
    glVertex3i(0, 0, 2);
    glEnd();
  }

  private void drawHUD() {
  }
}
