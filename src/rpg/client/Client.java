package rpg.client;

import java.net.DatagramSocket;
import java.net.SocketException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLU;
import rpg.client.gfx.GraphicsMode;
import rpg.client.gfx.TextureReleaser;
import rpg.client.gfx.widget.Widget;
import rpg.client.gfx.widget.winow.RootWindow;
import rpg.client.gfx.widget.winow.WindowManager;
import rpg.client.mode.Mode;
import rpg.client.mode.ModeManager;
import rpg.core.Info;
import rpg.core.Levels;
import rpg.net.NetConfig;
import rpg.util.Logger;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;

public final class Client {
  private Client() {}

  public static final DatagramSocket socket;

  static {
    try {
      Levels.experienceToLevel(50);
      socket = new DatagramSocket(NetConfig.PORT_S2C, NetConfig.serverAddr);
    } catch (SocketException e) {
      Logger.fatal(e, "Failed to establish socket to server.");
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) throws LWJGLException {
    ClientListener.singleton.start();
    lwjglSetup();
    glSetup();
    mainLoop();
  }

  private static void lwjglSetup() throws LWJGLException {
    System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
    System.setProperty("org.lwjgl.input.Mouse.allowNegativeMouseCoords", "true");
    Display.setTitle(Info.name);
    Display.setResizable(false);
    Display.setDisplayMode(new DisplayMode(640, 480));
    Display.create();
    Keyboard.enableRepeatEvents(true);
    //Mouse.setGrabbed(true);
  }

  private static void glSetup() {
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_TEXTURE_2D);
    glEnable(GL_COLOR_MATERIAL);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  private static void mainLoop() {
    while (!Display.isCloseRequested()) {
      FPSManager.newFrame();
      logic();
      render();
      Display.update();
      TextureReleaser.releaseDeadTextures();
      Display.sync(FPSManager.targetFPS);
    }
  }

  private static void logic() {
    keyboardLogic();
    mouseLogic();
    ModeManager.getCurrentMode().logic();
  }

  private static void keyboardLogic() {
    while (Keyboard.next()) {
      boolean down = Keyboard.getEventKeyState();
      if (!down)
        continue;

      int key = Keyboard.getEventKey();
      switch (key) {
        case Keyboard.KEY_ESCAPE:
          System.exit(0);
          break;
        case Keyboard.KEY_F4:
          if (Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA))
            System.exit(0);
          break;
      }
      Widget focusedWidget = WindowManager.getFocusedWidget();
      if (focusedWidget != null)
        focusedWidget.onKeyDown(key);
      ModeManager.getCurrentMode().onKeyDown(key);
    }
  }

  private static void mouseLogic() {
    while (Mouse.next()) {
      boolean down = Mouse.getEventButtonState();
      int button = Mouse.getEventButton();
      int x = Mouse.getEventX(),
          y = Display.getHeight() - Mouse.getEventY();
      if (button == 0)
        if (down)
          WindowManager.onLeftMouseDown(x, y);
        else
          WindowManager.onLeftMouseUp(x, y);
      if (down) {
        Mode mode = ModeManager.getCurrentMode();
        switch (button) {
          case 0:
            mode.onLeftMouse(x, y);
            break;
          case 1:
            mode.onRightMouse(x, y);
            break;
        }
      }
    }
  }

  private static void render() {
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    GLU.gluPerspective(60,
        Display.getWidth() / (float) Display.getHeight(),
        1, 500);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();

    glClear(GL_DEPTH_BUFFER_BIT);
    GraphicsMode.start2D();
    RootWindow.singleton.render();
    GraphicsMode.end2D();
    Util.checkGLError();
  }
}
