
import devdata.Handlers.EchoHandler;
import devdata.Handlers.EmptyHandler;
import devdata.Handlers.NotFoundHandler;
import devdata.Handlers.UserAgentHandler;
import devdata.http.IHttpHandler;
import devdata.http.Request;

import java.net.Socket;

public class ConnectionHandler implements Runnable{
    private final Socket clientSocket;
    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    public Socket getClientSocket() {
        return clientSocket;
    }
    @Override
    public void run() {
        try {
            Request request = new Request(clientSocket);
            request.parse();
            IHttpHandler[] handlers = new IHttpHandler[]{
                    new EmptyHandler(),
                    new UserAgentHandler(),
                    new EchoHandler(),
                    new NotFoundHandler()
            };

            for(IHttpHandler handler:handlers){
                boolean isHandled = handler.handle(request);
                if(isHandled){
                    break;
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
