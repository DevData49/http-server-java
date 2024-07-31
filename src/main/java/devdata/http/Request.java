package devdata.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private String method;
    private String body;
    private String path;

    private final Map<String, String> headers;


    private final Socket clientSocket;


    public Request(Socket socket) {
        this.clientSocket = socket;
        path = "";
        headers = new HashMap<>();
        method = "";
        body = "";
    }
    public void parse() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String nextLine = reader.readLine();
        if(nextLine == null){
            return;
        }
        String[] status = nextLine.split(" ");
        System.out.println("Status line :" + nextLine);
        method = status[0];
        path = status[1];
        System.out.println("method : " + method);
        System.out.println("path   : " + path);
        while ((nextLine = reader.readLine()) != null && !nextLine.isEmpty()){
            String[] splits = nextLine.split(": ");
            headers.put(splits[0], splits[1]);
            System.out.println(splits[0] + " : "+splits[1]);
        }

        int contentLength = Integer.parseInt(headers.getOrDefault("Content-Length","0"));
        if(contentLength > 0){
            byte[] bodyBytes = new byte[contentLength];
            int offset = 0;
            int bytesRead;
            while(offset<contentLength && (bytesRead = clientSocket.getInputStream().read(bodyBytes,offset, contentLength-offset)) != -1){
                offset += bytesRead;
            }
            body = new String(bodyBytes);
        }
        System.out.println("Body : " + body);
    }

    public String getPath() {
        return path;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void write(String msg) throws IOException {
        clientSocket.getOutputStream().write(msg.getBytes());
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body;
    }
}
