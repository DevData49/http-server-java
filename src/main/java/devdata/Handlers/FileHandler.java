package devdata.Handlers;

import devdata.http.IRequestHandler;
import devdata.http.Request;

import java.io.IOException;

public class FileHandler implements IRequestHandler {
    String filepath = "";
    String[] args;
    public FileHandler(String[] args) {
        this.args = args;
    }

    @Override
    public boolean handle(Request request) throws IOException {
        if(request.getPath().isEmpty() || !request.getPath().startsWith("/file/")){
            return false;
        }
        for(String arg : args){
            System.out.println(arg);
        }
        return false;
    }
}
