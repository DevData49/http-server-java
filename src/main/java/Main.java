import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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

       Socket client = serverSocket.accept(); // Wait for connection from client.
       System.out.println("accepted new connection");

       String ok = "HTTP/1.1 200 OK\r\n\r\n";
       String notFound = "HTTP/1.1 400 Not Found\r\n\r\n";

       ObjectInputStream ois= new ObjectInputStream(client.getInputStream());
       String req = (String) ois.readObject();
       String path = req.split(" ")[1];
       System.out.println(req);
       System.out.println(path);
       if(path.equals("/"))
         client.getOutputStream().write(ok.getBytes());
       else
         client.getOutputStream().write(notFound.getBytes());
       ois.close();
       client.close();
       serverSocket.close();
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     } catch (ClassNotFoundException e) {
         throw new RuntimeException(e);
     }
  }
}
