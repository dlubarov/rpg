package rpg.test;

public class FailureException extends RuntimeException {
  public final StackTraceElement frame;
  public final String message;

  public FailureException(StackTraceElement frame, String message) {
    this.frame = frame;
    this.message = message;
  }
}
