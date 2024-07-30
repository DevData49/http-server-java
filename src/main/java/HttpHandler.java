import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpHandler implements Runnable{
    private final Socket client;
    public HttpHandler(Socket client) {
        this.client = client;
    }
    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String req = in.readLine();


            if(req != null){
                String path = req.split(" ")[1];;

                System.out.println(path);


                String notFound = "HTTP/1.1 404 Not Found\r\n\r\n";
                if( path.equals("/")){
                    client.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
                }else if(path.equals("/user-agent")){
                    String line;
                    while(!(line = in.readLine()).isEmpty()){
                        if(line.startsWith("User-Agent")){
                            break;
                        }
                    }

                    String msg = "";
                    if(!line.isEmpty()){
                        msg = line.split(": ")[1];
                    }
                    String body = String.format(

                            "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s",

                            msg.length(), msg);

                    client.getOutputStream().write(body.getBytes());
                }else if (path.startsWith("/echo/")){
                    String msg = path.split("/")[2];
                    String body = String.format(

                            "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s",

                            msg.length(), msg);

                    client.getOutputStream().write(body.getBytes());

                }else{
                    client.getOutputStream().write(notFound.getBytes());
                }
            } else{
                client.getOutputStream().write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
            }
            client.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
