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

       String path = "";
       if(req != null && !req.isEmpty()){
         path = req.split(" ")[1];
       }

       System.out.println("Req  : " + req);
       System.out.println("Path : "+path);

       String ok = "HTTP/1.1 200 OK\r\n\r\n";
       String notFound = "HTTP/1.1 404 Not Found\r\n\r\n";
       if(path.isEmpty()){
         client.getOutputStream().write(notFound.getBytes());
       }else{
         String[] paths = path.split("/");
         if(paths.length < 2 || !paths[1].equals("echo")){
           client.getOutputStream().write(notFound.getBytes());
         }else{
            String body = "";
            if(paths.length>=3){
              body = paths[2];
            }
            ok += "Content-Type: text/plain\r\n\r\n";
            ok += "Content-Length: "+body.length()+"\r\n\r\n";
            ok += body;
            client.getOutputStream().write(ok.getBytes());
         }
       }

       client.close();
       serverSocket.close();
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
