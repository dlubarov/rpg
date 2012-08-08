package rpg.test;

import java.util.Arrays;
import java.util.Random;

public abstract class Test {
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
    for (StackTraceElement frame : trace)
      if (!frame.getClassName().equals(Test.class.getName()))
        throw new FailureException(frame, msg == null ? null : String.format(msg, args));
    throw new AssertionError("Shouldn't get here.");
  }

  protected static void fail() {
    fail(null);
  }

  protected static void assertTrue(boolean b) {
    if (!b)
      fail();
  }

  protected static void assertFalse(boolean b) {
    if (b)
      fail();
  }

  protected static void assertEqual(Object a, Object b) {
    if (!a.equals(b))
      fail("%s is not equal to %s.", a, b);
  }

  protected static void assertNotEqual(Object a, Object b) {
    if (a.equals(b))
      fail("%s is equal to %s.", a, b);
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
