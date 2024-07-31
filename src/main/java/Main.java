import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
     System.out.println("Logs from your program will appear here!");
     try(ExecutorService executorService = Executors.newCachedThreadPool();
         ServerSocket serverSocket = new ServerSocket(4221)){
        serverSocket.setReuseAddress(true);
        while(true){
            Socket client = serverSocket.accept();
            executorService.submit(new ConnectionHandler(client, args));
        }
     } catch (IOException e) {
         throw new RuntimeException(e);
     }
  }
}
