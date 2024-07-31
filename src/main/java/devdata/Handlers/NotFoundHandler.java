package devdata.Handlers;

import devdata.http.IRequestHandler;
import devdata.http.Request;

import java.io.IOException;

public class NotFoundHandler implements IRequestHandler {
    @Override
    public boolean handle(Request request) throws IOException {
        System.out.println("Not Found handler");
        request.write("HTTP/1.1 404 Not Found\r\n\r\n");
        return true;
    }
}
