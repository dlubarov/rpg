package rpg.test;

import java.io.PrintStream;
import rpg.test.serialization.IntegerSerializerTest;
import rpg.test.serialization.LongSerializerTest;
import rpg.test.serialization.StringSerializerTest;
import rpg.test.serialization.Vector3SerializerTest;

public class TestSuite {
  private static final PrintStream ps = System.err;

  private static final Test[] allTests = {
      new IntegerSerializerTest(),
      new LongSerializerTest(),
      new StringSerializerTest(),
      new Vector3SerializerTest(),
  };

  public static void main(String[] args) {
    int numPassed = 0, numFailed = 0;

    for (Test test : allTests)
      try {
        test.runInContext();
        ++numPassed;
      } catch (FailureException e) {
        ps.printf("Failure: %s (line %d)\n",
            e.frame.getClassName(),
            e.frame.getLineNumber());
        if (e.message != null)
          ps.printf("    %s\n", e.message);
        ++numFailed;
      }

    int numTotal = numPassed + numFailed;
    if (numFailed == 0)
      ps.printf("All %d tests passed.", numTotal);
    else
      ps.printf("%d of %d tests failed.", numFailed, numTotal);
  }
}
