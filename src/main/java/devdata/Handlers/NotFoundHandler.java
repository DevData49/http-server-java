package devdata.Handlers;

import devdata.http.IHttpHandler;
import devdata.http.Request;

import java.io.IOException;

public class NotFoundHandler implements IHttpHandler {
    @Override
    public boolean handle(Request request) throws IOException {
        request.write("HTTP/1.1 404 Not Found\r\n\r\n");
        return true;
    }
}
