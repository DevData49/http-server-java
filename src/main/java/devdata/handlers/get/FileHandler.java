package devdata.handlers.get;

import devdata.http.IHttpHandler;
import devdata.http.Request;
import devdata.http.Response;
import devdata.utils.Compress;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler implements IHttpHandler {
    String filedir = "";
    String[] args;
    public FileHandler(String[] args) {
        this.args = args;
        if(args.length>=2){
            filedir = args[1];
        }
    }

    @Override
    public boolean handle(Request request, Response response) throws IOException {
        if(request.getPath().isEmpty() || !request.getPath().startsWith("/files/")){
            return false;
        }
        System.out.println("Handled by fileHandler");
        System.out.println("FileDir :"+ this.filedir);
        String filename = request.getPath().split("/")[2];
        Path filepath = Paths.get(filedir, filename);

        if(!Files.exists(filepath)) {
            System.out.println(filepath);
            return false;
        }
        String contents = Files.readString(filepath);

        response.setStatus(200)
                .setHeader("Content-Type","application/octet-stream");
        String encodings = request.getHeaders().get("Accept-Encoding");
        if(encodings != null && encodings.contains("gzip")){
            byte[] compressed = Compress.compress(contents);
            response.setHeader("Content-Encoding", "gzip")
                    .setHeader("Content-Length",""+compressed.length)
                    .setByteBody(compressed);

        }else{
            response.setHeader("Content-Length",""+contents.length())
                    .setBody(contents);
        }

        response.send();
        return true;
    }
}
