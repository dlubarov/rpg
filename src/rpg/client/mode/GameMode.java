package rpg.client.mode;

import org.lwjgl.util.glu.GLU;
import rpg.client.gfx.GraphicsMode;
import rpg.client.people.LocalPlayer;
import rpg.game.realm.Realm;
import rpg.net.msg.s2c.WelcomeMessage;
import rpg.util.math.AAB;
import rpg.util.math.Vector3;
import rpg.util.phys.BodyOctree;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex3i;

public class GameMode extends Mode {
  private static final double EYE_HEIGHT = 8;

  private final LocalPlayer localPlayer;
  private Realm realm;
  private BodyOctree octree;

  public GameMode(WelcomeMessage welcome) {
    localPlayer = new LocalPlayer(welcome.characterID, welcome.combatClass, welcome.motionState);
    setRealm(welcome.realm);
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

  @Override public void logic() {
    localPlayer.logic();
  }

  @Override public void render() {
    GraphicsMode.end2D();
    drawScene();
    GraphicsMode.start2D();
    drawHUD();
  }

  private void drawScene() {
    Vector3 eye = localPlayer.getPos().plus(Vector3.UNIT_Y.scaled(EYE_HEIGHT)),
            center = eye.plus(localPlayer.getMotionState().getDirectionVector());
    GLU.gluLookAt(
        (float) eye.x, (float) eye.y, (float) eye.z,
        (float) center.x, (float) center.y, (float) center.z,
        0, 1, 0);
    drawAxes();
    glDisable(GL_TEXTURE_2D);
    glColor3f(1, 0, 0);
    glBegin(GL_TRIANGLES);
    glVertex3i(0, -2, 0);
    glVertex3i(200, -2, 0);
    glVertex3i(0, -2, 200);
    glEnd();
    glEnable(GL_TEXTURE_2D);
  }

  private void drawHUD() {
    // TODO: Draw HUD.
  }

  private void drawAxes() {
    glDisable(GL_TEXTURE_2D);
    glBegin(GL_LINES);
    // x axis in red
    glColor3f(1, 0, 0);
    glVertex3i(0, 0, 0);
    glVertex3i(10, 0, 0);
    // y axis in green
    glColor3f(0, 1, 0);
    glVertex3i(0, 0, 0);
    glVertex3i(0, 10, 0);
    // z axis in blue
    glColor3f(0, 0, 1);
    glVertex3i(0, 0, 0);
    glVertex3i(0, 0, 10);
    glEnd();
    glEnable(GL_TEXTURE_2D);
  }
}
