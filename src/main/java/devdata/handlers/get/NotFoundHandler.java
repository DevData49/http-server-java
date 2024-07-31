package devdata.handlers.get;

import devdata.http.IHttpHandler;
import devdata.http.Request;
import devdata.http.Response;

import java.io.IOException;

public class NotFoundHandler implements IHttpHandler {
    @Override
    public boolean handle(Request request, Response response) throws IOException {
        System.out.println("Not Found handler");
        response.setStatus(404).send();
        return true;
    }
}
