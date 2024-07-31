package devdata.handlers.get;

import devdata.http.IHttpHandler;
import devdata.http.Request;
import devdata.http.Response;

import java.io.IOException;

public class EmptyHandler implements IHttpHandler {
    @Override
    public boolean handle(Request request, Response response) throws IOException {
        if(request.getPath().isEmpty() || request.getPath().equals("/")){
            System.out.println("Handled by EmptyHandler");
            response.setStatus(200).send();
            return true;
        }
        return false;
    }
}
