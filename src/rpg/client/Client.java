package rpg.client;

import java.net.BindException;
import java.net.DatagramSocket;
import java.net.SocketException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.Util;
import org.lwjgl.util.glu.GLU;
import rpg.client.gfx.GraphicsMode;
import rpg.client.gfx.TextureReleaser;
import rpg.client.gfx.widget.winow.RootWindow;
import rpg.client.mode.ModeManager;
import rpg.game.Info;
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

  public static final DatagramSocket socket = getSocket();

  public static void main(String[] args) throws LWJGLException {
    lwjglSetup();
    glSetup();

    ClientListener.singleton.start();
    Logger.info("Client listening on port %d.", socket.getLocalPort());

    mainLoop();
  }

  private static DatagramSocket getSocket() {
    for (int port = NetConfig.PORT_S2C_MIN; port <= NetConfig.PORT_S2C_MAX; ++port)
      try {
        return new DatagramSocket(port, NetConfig.serverAddr);
      } catch (BindException e) {
        // Try the next port.
      } catch (SocketException e) {
        throw new RuntimeException("Failed to bind socket.", e);
      }
    throw new RuntimeException("No ports available.");
  }

  private static void lwjglSetup() throws LWJGLException {
    // TODO: Using decorated window until this bug is resolved:
    // http://lwjgl.org/forum/index.php/topic,5279.0.html
    //System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
    System.setProperty("org.lwjgl.input.Mouse.allowNegativeMouseCoords", "true");
    Display.setTitle(Info.name);
    Display.setResizable(false);
    Display.setDisplayMode(new DisplayMode(640, 480));
    Keyboard.enableRepeatEvents(true);
    Display.create();
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
      double dt = FPSManager.newFrame();
      logic(dt);
      render();
      Display.update();
      TextureReleaser.releaseDeadTextures();
      Display.sync(FPSManager.targetFPS);
    }
  }

  private static void logic(double dt) {
    KeyboardManager.processEvents();
    MouseManager.processEvents();
    ModeManager.getCurrentMode().logic(dt);
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
