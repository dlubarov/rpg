package rpg.client.gfx.widget;

import java.awt.Color;
import rpg.client.gfx.font.Alignment;

public class ConstantLabel extends Label {
  private final String content;

  public ConstantLabel(String content, Alignment alignment, Color color) {
    super(alignment, color);
    this.content = content;
  }

  public ConstantLabel(String content, Alignment alignment) {
    super(alignment);
    this.content = content;
  }

  public ConstantLabel(String content) {
    super();
    this.content = content;
  }

  @Override protected String getContent() {
    return content;
  }
}
