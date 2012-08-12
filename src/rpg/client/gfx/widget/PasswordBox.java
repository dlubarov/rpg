package rpg.client.gfx.widget;

import rpg.util.StringUtil;

public class PasswordBox extends TextBox {
  public PasswordBox() {}

  @Override public String getContentToDraw() {
    return StringUtil.repeat('*', content.length());
  }
}
