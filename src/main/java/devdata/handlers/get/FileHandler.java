package devdata.handlers.get;

import devdata.http.IRequestHandler;
import devdata.http.Request;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler implements IRequestHandler {
    String filedir = "";
    String[] args;
    public FileHandler(String[] args) {
        this.args = args;
        if(args.length>=2){
            filedir = args[1];
        }
    }

    @Override
    public boolean handle(Request request) throws IOException {
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
//        String body = String.format(
//                "HTTP/1.1 200 OK\r\nContent-Type: application/octet-stream\r\nContent-Length: %d\r\n\r\n%s",
//                contents.length(),contents);

        String body = "HTTP/1.1 200 OK\r\n";
        body += "Content-Type: application/octet-stream\r\n";
        String encodings = request.getHeaders().get("Accept-Encoding");
        if(encodings != null && encodings.contains("gzip")){
            body += "Content-Encoding: gzip\r\n";
        }
        body += "Content-Length: "+contents.length()+"\r\n\r\n";

        body += contents;
        request.write(body);
        return true;
    }
}
