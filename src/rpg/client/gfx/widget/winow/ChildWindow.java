package rpg.client.gfx.widget.winow;

import rpg.client.gfx.widget.Bounds;
import rpg.client.gfx.widget.Widget;
import rpg.util.Logger;

public final class ChildWindow extends Window {
  private final Widget content;
  private int x1, y1;

  public ChildWindow(String caption, Widget content, int x1, int y1, Button... buttons) {
    super(caption, buttons);
    Logger.info("New child window: %s", caption);
    this.content = content;
    this.x1 = x1;
    this.y1 = y1;
    content.setBounds(new Bounds(x1(), y2(), x2(), y3()));
    show();
  }

  public ChildWindow(String caption, Widget content, Button... buttons) {
    this(caption, content,
        (RootWindow.singleton.contentW() - content.getMinWidth()) / 2,
        (RootWindow.singleton.contentH() - content.getMinHeight()) / 2,
        buttons);
  }

  public void show() {
    ChildManager.singleton.addChild(this);
  }

  public void hide() {
    ChildManager.singleton.removeChild(this);
  }

  @Override
  protected int x1() {
    return x1;
  }

  @Override
  protected int y1() {
    return y1;
  }

  @Override
  protected int contentW() {
    return content.getMinWidth();
  }

  @Override
  protected int contentH() {
    return content.getMinHeight();
  }

  @Override
  protected void renderContent() {
    content.render();
  }
}
