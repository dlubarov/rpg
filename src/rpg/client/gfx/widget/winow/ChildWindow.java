package rpg.client.gfx.widget.winow;

import rpg.client.gfx.widget.Widget;
import rpg.util.Logger;

public final class ChildWindow extends Window {
  final String caption;
  final Widget content;
  private int x1, y1;

  public ChildWindow(String caption, Widget content, int x1, int y1, WindowButton... buttons) {
    super(buttons);
    this.caption = caption;
    Logger.info("New child window: %s", caption);
    this.content = content;
    moveTo(x1, y1);
    show();
  }

  public ChildWindow(String caption, Widget content, WindowButton... buttons) {
    this(caption, content,
        (RootWindow.singleton.contentW() - content.getMinWidth()) / 2,
        (RootWindow.singleton.contentH() - content.getMinHeight()) / 2,
        buttons);
  }

  public void show() {
    WindowManager.addChild(this);
  }

  @Override public void close() {
    WindowManager.removeChild(this);
  }

  public void moveTo(int x1, int y1) {
    x1 = Math.max(x1, 0);
    y1 = Math.max(y1, BAR_HEIGHT);
    x1 = Math.min(x1, RootWindow.singleton.contentW() - contentW());
    y1 = Math.min(y1, RootWindow.singleton.totalH() - totalH());
    this.x1 = x1;
    this.y1 = y1;
    content.setBounds(bounds());
  }

  @Override public String getCaption() {
    return caption;
  }

  @Override public boolean isFocused() {
    return this == WindowManager.getFocusedWindow();
  }

  @Override public int x1() {
    return x1;
  }

  @Override public int y1() {
    return y1;
  }

  @Override public int contentW() {
    return content.getMinWidth();
  }

  @Override public int contentH() {
    return content.getMinHeight();
  }

  @Override protected void renderContent() {
    content.render();
  }
}
