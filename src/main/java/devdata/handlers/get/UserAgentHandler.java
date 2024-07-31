package devdata.handlers.get;

import devdata.http.IHttpHandler;
import devdata.http.Request;
import devdata.http.Response;
import devdata.utils.Compress;

import java.io.IOException;

public class UserAgentHandler implements IHttpHandler {
    @Override
    public boolean handle(Request request, Response response) throws IOException {
        if(request.getPath().isEmpty() || !request.getPath().equals("/user-agent")){
            return false;
        }
        System.out.println("Handled by user-agent");
        String msg = request.getHeaders().getOrDefault("User-Agent", "");

        response.setStatus(200)
                .setHeader("Content-Type", "text/plain");

        String encodings = request.getHeaders().get("Accept-Encoding");
        if(encodings != null && encodings.contains("gzip")){
            byte[] compressed = Compress.compress(msg);
            response.setHeader("Content-Encoding","gzip")
                    .setHeader("Content-Length",""+compressed.length)
                    .setByteBody(compressed);

        }else{
            response.setHeader("Content-Length",""+msg.length())
                    .setBody(msg);
        }
        response.send();
        return true;
    }
}
