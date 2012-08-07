package rpg.test;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;

public abstract class Test {
  private static final PrintStream ps = System.err;

  protected final Random rng;

  protected Test() {
    rng = new Random(getClass().getName().hashCode());
  }

  public void setUp() {}
  public void tearDown() {}

  protected abstract void run();

  public final void runInContext() {
    setUp();
    run();
    tearDown();
  }

  protected static void fail(String msg, Object... args) {
    StackTraceElement[] trace = new Exception().getStackTrace();
    for (StackTraceElement elem : trace)
      if (!elem.getClassName().equals(Test.class.getName())) {
        ps.printf("Failed %s.%s (line %d).\n",
            elem.getClassName(), elem.getMethodName(), elem.getLineNumber());
        if (msg != null) {
          ps.printf("    %s\n", String.format(msg, args));
        }
        return;
      }
    throw new AssertionError("Shouldn't get here.");
  }

  protected static void fail() {
    fail(null);
  }

  protected static void assertTrue(boolean b) {
    if (!b) {
      fail();
    }
  }

  protected static void assertFalse(boolean b) {
    if (b) {
      fail();
    }
  }

  protected static void assertEqual(Object a, Object b) {
    if (!a.equals(b)) {
      fail("%s is not equal to %s.", a, b);
    }
  }

  protected static void assertNotEqual(Object a, Object b) {
    if (a.equals(b)) {
      fail("%s is equal to %s.", a, b);
    }
  }

  protected static void assertArraysEqual(Object[] a, Object[] b) {
    if (!Arrays.equals(a, b))
      fail("%s is not equal to %s", Arrays.toString(a), Arrays.toString(b));
  }

  protected static void assertArraysNotEqual(Object[] a, Object[] b) {
    if (Arrays.equals(a, b))
      fail("%s is equal to %s", Arrays.toString(a), Arrays.toString(b));
  }
}
