package Controller;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class ConnectionController {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8989), 0);
        server.createContext("/api", new ApiHandler());
        server.start();
        System.out.println("Server started");
    }
}