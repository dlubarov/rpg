package rpg.client.gfx.widget.winow;

import rpg.client.gfx.widget.Bounds;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2i;

public class CloseButton extends WindowButton {
  @Override public void renderContent(Bounds bounds) {
    int pad = 3;
    glColor3d(.5, .5, .5);
    bounds = new Bounds(bounds.x1() + pad, bounds.y1() + pad, bounds.x2() - pad, bounds.y2() - pad);
    glBegin(GL_LINES);
    glVertex2i(bounds.x1(), bounds.y1());
    glVertex2i(bounds.x2(), bounds.y2());
    glVertex2i(bounds.x2(), bounds.y1());
    glVertex2i(bounds.x1(), bounds.y2());
    glEnd();
  }

  @Override public void onClick(Window parent) {
    parent.close();
  }
}
