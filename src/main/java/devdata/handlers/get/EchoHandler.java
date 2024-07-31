package devdata.handlers.get;

import devdata.http.IRequestHandler;
import devdata.http.Request;

import java.io.IOException;

public class EchoHandler implements IRequestHandler {
    @Override
    public boolean handle(Request request) throws IOException {
        if(request.getPath().isEmpty() || !request.getPath().startsWith("/echo/")){
            return false;
        }
        System.out.println("Handled by EchoHanlder");
        String msg = request.getPath().split("/")[2];
        String body = "HTTP/1.1 200 OK\r\n";
        body += "Content-Type: text/plain\r\n";

        String encodings = request.getHeaders().get("Accept-Encoding");
        if(encodings != null && encodings.contains("gzip")){
            body += "Content-Encoding: gzip\r\n";
        }
        body += "Content-Length: "+msg.length()+"\r\n\r\n";
        body += msg;

        request.write(body);
        return true;
    }
}
