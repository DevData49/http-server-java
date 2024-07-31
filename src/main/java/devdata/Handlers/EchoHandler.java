package devdata.Handlers;

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
        String body = String.format(

                "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\nContent-Length: %d\r\n\r\n%s",
                msg.length(), msg);
        request.write(body);
        return true;
    }
}
