package rpg.util;

import java.io.PrintStream;

public final class Logger {
  private Logger() {}

  private static final PrintStream ps = System.err;

  private static Severity getDesiredSeverity() {
    return Severity.INFO;
  }

  public static void fatal(Throwable t, String format, Object... args) {
    log(format, args, Severity.FATAL);
    t.printStackTrace(ps);
    System.exit(1);
  }

  public static void error(Exception e, String format, Object... args) {
    log(format, args, Severity.ERROR);
    if (e != null)
      e.printStackTrace(ps);
  }

  public static void error(String format, Object... args) {
    error(null, format, args);
  }

  public static void warning(String format, Object... args) {
    log(format, args, Severity.WARNING);
  }

  public static void info(String format, Object... args) {
    log(format, args, Severity.INFO);
  }

  public static void debug(String format, Object... args) {
    log(format, args, Severity.DEBUG);
  }

  public static void trace(String format, Object... args) {
    log(format, args, Severity.TRACE);
  }

  private static void log(String format, Object[] args, Severity severity) {
    if (isVisible(severity))
      ps.printf("%s: %s\n", severity, String.format(format, args));
  }

  private static boolean isVisible(Severity severity) {
    return severity.ordinal() >= getDesiredSeverity().ordinal();
  }

  private static enum Severity {
    TRACE, DEBUG, INFO, WARNING, ERROR, FATAL
  }
}
