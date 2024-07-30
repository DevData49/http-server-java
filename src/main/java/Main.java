import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {

  public static String  getRequest(Socket client) throws IOException {
    DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
    char datatype = in.readChar();
    int length = in.readInt();

    if(datatype == 's'){
      byte[] messageByte = new byte[length];
      boolean end = false;
      StringBuilder dataString  = new StringBuilder(length);
      int totalBytesRead = 0;
      while(!end){
        int currentBytesRead = in.read(messageByte);
        totalBytesRead += currentBytesRead;
        if(totalBytesRead <= length){
          dataString.append(new String(messageByte,0, currentBytesRead, StandardCharsets.UTF_8));
        }else{
          dataString.append((new String(messageByte,0,length-totalBytesRead+currentBytesRead)));
        }
        if(totalBytesRead>=length){
          end = true;
        }
      }
      return dataString.toString();
    }else{
      return "";
    }
  }
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

       String req = getRequest(client);

       String path = "";
       if(!req.isEmpty()){
         path = req.split(" ")[1];
       }

       String ok = "HTTP/1.1 200 OK\r\n\r\n";
       String notFound = "HTTP/1.1 400 Not Found\r\n\r\n";

       if(path.equals("/")){
         client.getOutputStream().write(ok.getBytes());
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
