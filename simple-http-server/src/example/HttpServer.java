package example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    private final int port;
    private final ServerSocket serverSocket;
    private volatile boolean running = false;

    public HttpServer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        running = true;
        while (true) {
            Socket socket = serverSocket.accept();

            new Thread(() -> new RequestHandler(socket).handleRequest()).start();
        }
    }

    public void stop() throws Exception{
        running = false;
        serverSocket.close();
    }
}
