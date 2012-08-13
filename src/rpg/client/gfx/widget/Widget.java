package rpg.client.gfx.widget;

import rpg.client.gfx.widget.winow.WindowManager;

public abstract class Widget {
  protected Bounds bounds;
  private boolean frozen = false;

  public final boolean isFrozen() {
    return frozen;
  }

  public void setFrozen(boolean frozen) {
    this.frozen = frozen;
  }

  public final Bounds getBounds() {
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
}
