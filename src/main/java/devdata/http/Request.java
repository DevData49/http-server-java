package devdata.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Request {

    private String path;

    private final Map<String, String> headers;


    private final Socket clientSocket;


    public Request(Socket socket) {
        this.clientSocket = socket;
        path = "";
        headers = new HashMap<>();
    }
    public void parse() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String nextLine = reader.readLine();
        if(nextLine == null){
            return;
        }
        path = nextLine.split(" ")[1];

        while ((nextLine = reader.readLine()) != null && !nextLine.isEmpty()){
            String[] splits = nextLine.split(": ");
            headers.put(splits[0], splits[1]);
        }
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
}
