
import devdata.handlers.get.*;
import devdata.handlers.post.FilePostHandler;
import devdata.http.IRequestHandler;
import devdata.http.Request;

import java.net.Socket;
import java.util.Arrays;

public class ConnectionHandler implements Runnable{
    private final Socket clientSocket;
    private final String[] args;
    public ConnectionHandler(Socket clientSocket, String[] args)
    {
        this.clientSocket = clientSocket;
        this.args = args;
    }
    public Socket getClientSocket() {
        return clientSocket;
    }
    @Override
    public void run() {
        try {
            Request request = new Request(clientSocket);
            request.parse();
            IRequestHandler[] handlers = getHandlers(request,args);
            System.out.println(Arrays.toString(handlers));
            for(IRequestHandler handler:handlers){

                boolean isHandled = handler.handle(request);
                if(isHandled){
                    System.out.println("handled by : "+handler);
                    return;
                }
            }
            new NotFoundHandler().handle(request);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private IRequestHandler[] getHandlers(Request request,String[] args) {
        if(request.getMethod().equals("POST"))
            return new IRequestHandler[]{new FilePostHandler(args)};
        else
            return new IRequestHandler[]{
                    new EmptyHandler(),
                    new UserAgentHandler(),
                    new EchoHandler(),
                    new FileHandler(args),

            };
    }
}
