package devdata.Handlers;

import devdata.http.IRequestHandler;
import devdata.http.Request;

import java.io.IOException;

public class FileHandler implements IRequestHandler {
    String filepath = "";
    String[] args;
    public FileHandler(String[] args) {
        this.args = args;
        if(args.length>=2){
            filepath = args[1];
        }
    }

    @Override
    public boolean handle(Request request) throws IOException {
        if(request.getPath().isEmpty() || !request.getPath().startsWith("/files/")){
            return false;
        }
        System.out.println("Handled by fileHandler");
        System.out.println("FilePath :"+ this.filepath);
        return false;
    }
}
