package rpg.util;

public final class ArrayUtil {
  private ArrayUtil() {}

  public static boolean contains(Object[] arr, Object value) {
    for (Object elem : arr)
      if (elem.equals(value))
        return true;
    return false;
  }

  public static String implode(String glue, Object[] arr) {
    StringBuilder sb = new StringBuilder();
    for (Object elem : arr) {
      if (sb.length() > 0)
        sb.append(glue);
      sb.append(elem);
    }
    return sb.toString();
  }

  public static String implode(char glue, Object[] arr) {
    StringBuilder sb = new StringBuilder();
    for (Object elem : arr) {
      if (sb.length() > 0)
        sb.append(glue);
      sb.append(elem);
    }
    return sb.toString();
  }
}
