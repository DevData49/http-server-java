package devdata.http;

import java.io.IOException;

public interface IHttpHandler {
    public boolean handle(Request request) throws IOException;
}
