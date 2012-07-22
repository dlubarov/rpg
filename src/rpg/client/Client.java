package rpg.client;

import java.net.DatagramSocket;
import java.net.SocketException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import rpg.client.gfx.TextureReleaser;
import rpg.client.mode.ModeManager;
import rpg.core.Info;
import rpg.net.NetConfig;
import rpg.util.Logger;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;

public final class Client {
  private Client() {}

  public static final DatagramSocket socket;

  static {
    try {
      socket = new DatagramSocket(NetConfig.PORT_C2S, NetConfig.serverAddr);
    } catch (SocketException e) {
      Logger.fatal(e, "Failed to establish socket to server.");
      throw new RuntimeException(e);
    }
  }

  public static void main(String[] args) throws LWJGLException {
    ClientListener.singleton.start();
    TextureReleaser.singleton.start();
    lwjglSetup();
    glSetup();
    mainLoop();
  }

  private static void lwjglSetup() throws LWJGLException {
    Display.setTitle(Info.name);
    Display.setResizable(false);
    Display.setDisplayMode(new DisplayMode(640, 480));
    Display.create();
    Keyboard.create();
    Mouse.setGrabbed(true);
  }

  private static void glSetup() {
    glEnable(GL_DEPTH_TEST);
    glEnable(GL_TEXTURE_2D);
    glClearColor(0, 1, 1, 1);
  }

  private static void mainLoop() {
    while (!Display.isCloseRequested()) {
      FPSManager.newFrame();
      Display.setTitle(getCaption());
      logic();
      render();
      Display.update();
      Display.sync(FPSManager.targetFPS);
    }
  }

  private static String getCaption() {
    return String.format("%s v%s [%d FPS]",
        Info.name, Info.getVersionString(), FPSManager.getFps());
  }

  private static void logic() {
    while (Keyboard.next()) {
      int key = Keyboard.getEventKey();
      boolean down = Keyboard.getEventKeyState();
      switch (key) {
        case Keyboard.KEY_ESCAPE:
          if (down) {
            System.exit(0);
          }
          break;
        case Keyboard.KEY_F4:
          if (Keyboard.isKeyDown(Keyboard.KEY_LMETA) || Keyboard.isKeyDown(Keyboard.KEY_RMETA)) {
            System.exit(0);
          }
          break;
      }
      if (down) {
        ModeManager.getCurrentMode().onKeyDown(key);
      } else {
        ModeManager.getCurrentMode().onKeyUp(key);
      }
    }
    ModeManager.getCurrentMode().logic();
  }

  private static void render() {
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    GLU.gluPerspective(60,
        Display.getWidth() / (float) Display.getHeight(),
        1, 500);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    GLU.gluLookAt(
        5, 5, 5,
        0, 0, 0,
        0, 1, 0);

    ModeManager.getCurrentMode().render();
  }
}
