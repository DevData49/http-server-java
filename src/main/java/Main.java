import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
  public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
    System.out.println("Logs from your program will appear here!");

    // Uncomment this block to pass the first stage
    //
     try {
       ServerSocket serverSocket = new ServerSocket(4221);

       // Since the tester restarts your program quite often, setting SO_REUSEADDR
       // ensures that we don't run into 'Address already in use' errors
       serverSocket.setReuseAddress(true);
        while(true){
            Socket client = serverSocket.accept(); // Wait for connection from client.
            System.out.println("accepted new connection");
            new
        }
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
      ;
     try(ExecutorService executorService = Executors.newCachedThreadPool();
         ServerSocket serverSocket = new ServerSocket(4221)){
        serverSocket.setReuseAddress(true);
        while(true){
            Socket client = serverSocket.accept();
            executorService.submit(new HttpHandler(client));
        }
     } catch (IOException e) {
         throw new RuntimeException(e);
     }
  }
}
