package rpg.client.gfx.widget;

import rpg.client.gfx.widget.winow.WindowManager;

import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

public abstract class Widget {
  protected Bounds bounds;

  public Bounds getBounds() {
    return bounds;
  }

  public void setBounds(Bounds bounds) {
    this.bounds = bounds;
  }

  /**
   * The minimum width of the widget.
   */
  public abstract int getMinWidth();

  /**
   * The minimum height of the widget.
   */
  public abstract int getMinHeight();

  /**
   * Whether the widget LIKES to stretch horizontally. Note that this is only a preference;
   * a widget may always be forced to stretch in certain layouts.
   */
  public abstract boolean stretchHorizontally();

  /**
   * Whether the widget LIKES to stretch vertically. Note that this is only a preference;
   * a widget may always be forced to stretch in certain layouts.
   */
  public abstract boolean stretchVertically();

  public void onClick(int x, int y) {}

  public void onKeyDown(int key) {
    // This should only be called for FocusableWidget.
    throw new UnsupportedOperationException();
  }

  public Widget getWidget(String name) {
    return null;
  }

  public String getValue(String name) {
    return null;
  }

  public abstract void render();

  public Widget pad(int amount) {
    return new FixedPadding(this, amount);
  }

  public Widget padFlexible() {
    return new FlexiblePadding(this);
  }

  public Widget padSidesFlexible() {
    return new HBox(FlexibleSpace.singleton, this, FlexibleSpace.singleton);
  }

  public boolean isFocused() {
    return WindowManager.getFocusedWidget() == this;
  }

  public void makeFocused() {
    WindowManager.makeFocused(this);
  }

  public void renderBounds() {
    glDisable(GL_TEXTURE_2D);
    glColor3d(0, 1, 0);
    glBegin(GL_LINE_LOOP);
    glVertex2d(bounds.x1() + .5, bounds.y1() + .5);
    glVertex2d(bounds.x1() + .5, bounds.y2() - .5);
    glVertex2d(bounds.x2() - .5, bounds.y2() - .5);
    glVertex2d(bounds.x2() - .5, bounds.y1() + .5);
    glEnd();
    glEnable(GL_TEXTURE_2D);
  }
}
