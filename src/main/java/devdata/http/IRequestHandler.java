package devdata.http;

import java.io.IOException;

public interface IRequestHandler {
    public boolean handle(Request request) throws IOException;
}
