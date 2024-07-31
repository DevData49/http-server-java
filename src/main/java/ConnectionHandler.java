
import devdata.Handlers.EchoHandler;
import devdata.Handlers.EmptyHandler;
import devdata.Handlers.NotFoundHandler;
import devdata.Handlers.UserAgentHandler;
import devdata.http.IRequestHandler;
import devdata.http.Request;

import java.net.Socket;

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
            IRequestHandler[] handlers = new IRequestHandler[]{
                    new EmptyHandler(),
                    new UserAgentHandler(),
                    new EchoHandler(),
                    new NotFoundHandler()
            };

            for(IRequestHandler handler:handlers){
                boolean isHandled = handler.handle(request);
                if(isHandled){
                    System.out.println(handler);
                    break;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
