package rpg.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import rpg.util.Logger;
import rpg.serialization.ByteSource;

public class ServerListener extends Thread {
  private static final int EXECUTOR_THREADS = Runtime.getRuntime().availableProcessors();

  private final Executor executor = Executors.newFixedThreadPool(EXECUTOR_THREADS);

  private final DatagramSocket socket;

  ServerListener(DatagramSocket socket) {
    super("Server Listener");
    this.socket = socket;
  }

  @Override
  public void run() {
    byte[] receiveData = new byte[1024];
    for (;;) {
      DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
      try {
        socket.receive(packet);
      } catch (IOException e) {
        Logger.error(e, "Failed to receive packet.");
      }
      InetAddress sender = packet.getAddress();
      byte[] data = new byte[packet.getLength()];
      System.arraycopy(packet.getData(), packet.getOffset(), data, 0, data.length);
      ByteSource source = new ByteSource(data);
      executor.execute(new MessageDispatcher(source, sender));
    }
  }
}
