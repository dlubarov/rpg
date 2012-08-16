package rpg.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import rpg.util.Logger;
import rpg.util.serialization.ByteSource;

public class ClientListener extends Thread {
  public static final ClientListener singleton = new ClientListener();

  private final Executor executor =
      Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

  private ClientListener() {
    super("Client Listener");
    setDaemon(true);
  }

  @Override public void run() {
    byte[] receiveData = new byte[1024];
    for (;;) {
      DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
      try {
        Client.socket.receive(packet);
      } catch (IOException e) {
        Logger.error(e, "Failed to receive packet.");
      }
      byte[] data = new byte[packet.getLength()];
      System.arraycopy(packet.getData(), packet.getOffset(), data, 0, data.length);
      Logger.debug("Received data: %s.", Arrays.toString(data));
      ByteSource source = new ByteSource(data);
      executor.execute(new MessageDispatcher(source));
    }
  }
}
