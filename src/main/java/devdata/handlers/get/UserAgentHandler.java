package devdata.handlers.get;

import devdata.http.IRequestHandler;
import devdata.http.Request;

import java.io.IOException;

public class UserAgentHandler implements IRequestHandler {
    @Override
    public boolean handle(Request request) throws IOException {
        if(request.getPath().isEmpty() || !request.getPath().equals("/user-agent")){
            return false;
        }
        System.out.println("Handled by user-agent");
        String msg = request.getHeaders().getOrDefault("User-Agent", "");
//      String body;
//        = String.format(
//
//                "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s",
//
//                msg.length(), msg);
        String body = "HTTP/1.1 200 OK\r\n";

        String encodings = request.getHeaders().get("Accept-Encoding");
        if(encodings != null && encodings.contains("gzip")){
            body += "Content-Encoding: gzip\r\n";
        }
        body += "Content-Type: text/plain\r\n";
        body += "Content-Length: "+msg.length()+"\r\n\r\n";
        body += msg;
        request.write(body);
        return true;
    }
}
