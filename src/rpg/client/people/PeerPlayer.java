package rpg.client.people;

import rpg.client.gfx.GLUtil;
import rpg.game.CombatClass;
import rpg.game.MotionState;
import rpg.net.msg.s2c.PeerIntroductionMessage;
import rpg.util.Timing;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;

public final class PeerPlayer extends Player {
  private MotionState motionSnapshot;
  private double motionSnapshotTime;

  public PeerPlayer(int id, String characterName,
      CombatClass combatClass, MotionState motionState) {
    super(id, characterName, combatClass);
    setMotionSnapshot(motionState);
  }

  public PeerPlayer(PeerIntroductionMessage.Part introduction) {
    this(introduction.id, introduction.characterName,
        introduction.combatClass, introduction.motionState);
  }

  public void setMotionSnapshot(MotionState motionSnapshot) {
    this.motionSnapshot = motionSnapshot;
    motionSnapshotTime = Timing.currentTime();
  }

  @Override public MotionState getMotionState() {
    // TODO: dead reckoning
    return motionSnapshot;
  }

  public void render() {
    glDisable(GL_TEXTURE_2D);
    glColor3f(0, 0, 0);
    glBegin(GL_TRIANGLES);
    GLUtil.glVertex(getLeftFoot());
    GLUtil.glVertex(getRightFoot());
    GLUtil.glVertex(getTip());
    glEnd();
    glEnable(GL_TEXTURE_2D);
  }
}
