package rpg.client.mode;

import java.awt.Color;
import rpg.client.Client;
import rpg.client.gfx.font.Alignment;
import rpg.client.gfx.widget.Label;
import rpg.client.gfx.widget.Widget;
import rpg.net.ToServerMessageSink;
import rpg.net.msg.c2s.SessionCreationMessage;

public class ConnectingMode extends Mode2D {
  private final Widget content;
  private String message = "";

  public ConnectingMode() {
    content = createContent();
    setContentBounds();
  }

  @Override public void onEnter() {
    message = "Connecting...";
    SessionCreationMessage msg = new SessionCreationMessage(Client.socket.getLocalPort());
    ToServerMessageSink.singleton.sendWithConfirmation(msg, 3,
        new Runnable() {
          @Override public void run() {
            ModeManager.switchTo(new LoginMode());
          }
        },
        new Runnable() {
          @Override public void run() {
            message = "Failed to connect.";
          }
        });
  }

  @Override public Widget getContent() {
    return content;
  }

  private Widget createContent() {
    return new MessageLabel().padFlexible();
  }

  private class MessageLabel extends Label {
    public MessageLabel() {
      super(Alignment.CENTER_ALIGNED, Color.RED);
    }

    @Override protected String getContent() {
      return message;
    }
  }
}
