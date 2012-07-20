package rpg.client;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;
import rpg.core.Info;

import static org.lwjgl.opengl.GL11.*;

public final class Client {
  private Client() {}

  public static void main(String[] args) throws LWJGLException {
    // TODO start client listener
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
    ;
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

    glBegin(GL_TRIANGLES);
    glVertex3i(0, 0, 0);
    glVertex3i(1, 0, 0);
    glVertex3i(0, 0, 1);
    glEnd();
  }
}
