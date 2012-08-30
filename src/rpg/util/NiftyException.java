package rpg.util;

public class NiftyException extends RuntimeException {
  public NiftyException(Throwable cause, String format, Object... args) {
    super(String.format(format, args), cause);
  }

  public NiftyException(String format, Object... args) {
    this(null, format, args);
  }
}
