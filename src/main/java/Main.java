import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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

       BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
       String req = in.readLine();

       String path = req.split(" ")[1];;

       System.out.println(path);


       String notFound = "HTTP/1.1 404 Not Found\r\n\r\n";
       if(path.equals("/")){
         client.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
       }else if (path.startsWith("/echo/")){
            String msg = path.split("/")[2];
            String body = String.format(

                    "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s",

                    msg.length(), msg);

            client.getOutputStream().write(body.getBytes());

       }else{
           client.getOutputStream().write(notFound.getBytes());
       }

       client.close();
       serverSocket.close();
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
