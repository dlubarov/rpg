package rpg.client.gfx.widget;

import rpg.client.gfx.font.Alignment;

public class ConstantLabel extends Label {
  private final String content;

  public ConstantLabel(String content, Alignment alignment) {
    super(alignment);
    this.content = content;
  }

  public ConstantLabel(String content) {
    this(content, Alignment.LEFT_ALIGNED);
  }

  @Override
  protected String getContent() {
    return content;
  }
}
