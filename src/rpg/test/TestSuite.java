package rpg.test;

import rpg.test.serialization.IntegerSerializerTest;
import rpg.test.serialization.StringSerializerTest;

public class TestSuite {
  public static void main(String[] args) {
    new IntegerSerializerTest().runInContext();
    new StringSerializerTest().runInContext();
    System.err.println("Tests completed.");
  }
}
