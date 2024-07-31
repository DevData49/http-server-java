package devdata.http;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private String statusLine;
    private final Map<String,String> headers;
    private String body;
    private byte[] byteBody;
    private final Socket clientSocket;

    public Response(Socket clientSocket) {
        this.clientSocket = clientSocket;
        headers = new HashMap<>();
        body = "";
        byteBody = null;
    }

    public Response setStatus(int status){
        switch (status){
            case 200:
                statusLine = "HTTP/1.1 200 OK\r\n";
                break;
            case 404:
                statusLine = "HTTP/1.1 404 Not Found\r\n";
                break;
            case 201:
                statusLine = "HTTP/1.1 201 Created\r\n";
                break;
            default:
                statusLine = "";
        }
        return this;
    }
    public Response setHeader(String key, String value){
        headers.put(key,value);
        return this;
    }

    public Response setBody(String body){
        this.body = body;
        return this;
    }
    public Response setByteBody(byte[] byteBody){
        this.byteBody = byteBody;
        return this;
    }

    public void send() throws IOException {
        StringBuilder res = new StringBuilder();
        res.append(statusLine);
        for(Map.Entry<String, String> entry : headers.entrySet()){
            String header = entry.getKey()+": "+entry.getValue()+"\r\n";
            res.append(header);
        }
        res.append("\r\n");
        res.append(body);

        clientSocket.getOutputStream().write(res.toString().getBytes());
        if(byteBody != null)
            clientSocket.getOutputStream().write(byteBody);
    }
}
