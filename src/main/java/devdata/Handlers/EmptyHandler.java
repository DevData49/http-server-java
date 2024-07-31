package devdata.Handlers;

import devdata.http.IRequestHandler;
import devdata.http.Request;

import java.io.IOException;

public class EmptyHandler implements IRequestHandler {
    @Override
    public boolean handle(Request request) throws IOException {
        if(request.getPath().isEmpty() || request.getPath().equals("/")){
            System.out.println("Handled by EmptyHandler");
            request.write("HTTP/1.1 200 OK\r\n\r\n");
            return true;
        }
        return false;
    }
}
