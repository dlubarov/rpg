package rpg.client.gfx.widget;

public class ConstantLabel extends Label {
  private final String content;

  public ConstantLabel(String content) {
    this.content = content;
  }

  @Override
  protected String getContent() {
    return content;
  }
}
