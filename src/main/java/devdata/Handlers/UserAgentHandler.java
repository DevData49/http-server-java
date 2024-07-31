package devdata.Handlers;

import devdata.http.IHttpHandler;
import devdata.http.Request;

import java.io.IOException;

public class UserAgentHandler implements IHttpHandler {
    @Override
    public boolean handle(Request request) throws IOException {
        if(request.getPath().isEmpty() || !request.getPath().equals("/user-agent")){
            System.out.println("Handled by user-agent");
            return false;
        }
        String msg = request.getHeaders().getOrDefault("User-Agent", "");
        String body = String.format(

                "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s",

                msg.length(), msg);

        request.write(body);
        return true;
    }
}
