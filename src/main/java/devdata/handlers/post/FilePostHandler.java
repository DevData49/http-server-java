package devdata.handlers.post;

import devdata.http.IHttpHandler;
import devdata.http.Request;
import devdata.http.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePostHandler implements IHttpHandler {
    String fileDir = "";

    public FilePostHandler(String[] args) {
        if(args.length>=2){
            this.fileDir = args[1];
        }
    }

    @Override
    public boolean handle(Request request, Response response) throws IOException {
        if(request.getPath().isEmpty() || !request.getPath().startsWith("/files/")){
            return false;
        }
        System.out.println("Handled by File post handler");
        String filename = request.getPath().split("/")[2];
        if(filename.isEmpty()){
            return false;
        }
        Path filepath = Paths.get(fileDir, filename);
        Path parentDir = filepath.getParent();
        if(parentDir != null && !Files.exists(parentDir)){
            Files.createDirectories(parentDir);
        }

        Path created = Files.createFile(filepath);
        Files.write(created,request.getBody().getBytes());
        request.write("HTTP/1.1 201 Created\r\n\r\n");
        return true;
    }
}
