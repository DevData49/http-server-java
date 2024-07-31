package devdata.Handlers;

import devdata.http.IHttpHandler;
import devdata.http.Request;

import java.io.IOException;

public class EmptyHandler implements IHttpHandler {
    @Override
    public boolean handle(Request request) throws IOException {
        if(request.getPath().isEmpty() || request.getPath().equals("/")){
            request.write("HTTP/1.1 200 OK\r\n\r\n");
            return true;
        }
        return false;
    }
}
