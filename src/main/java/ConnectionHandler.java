
import devdata.handlers.get.*;
import devdata.handlers.post.FilePostHandler;
import devdata.http.IHttpHandler;
import devdata.http.Request;
import devdata.http.Response;

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
            Response response = new Response(clientSocket);
            IHttpHandler[] handlers = getHandlers(request,args);
            System.out.println(Arrays.toString(handlers));
            for(IHttpHandler handler:handlers){

                boolean isHandled = handler.handle(request,response);
                if(isHandled){
                    System.out.println("handled by : "+handler);
                    return;
                }
            }
            new NotFoundHandler().handle(request, response);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private IHttpHandler[] getHandlers(Request request, String[] args) {
        if(request.getMethod().equals("POST"))
            return new IHttpHandler[]{new FilePostHandler(args)};
        else
            return new IHttpHandler[]{
                    new EmptyHandler(),
                    new UserAgentHandler(),
                    new EchoHandler(),
                    new FileHandler(args),

            };
    }
}
