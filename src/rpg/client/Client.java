package rpg.client;

public class Client {
  private static Client singleton;

  public static Client getSingleton() { return singleton; }

  public static void main(String[] args) {
    singleton = new Client();
  }
}
