package ru.test; /**
 * Created 15.10.16.
 */


import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.PortRange;
import org.glassfish.grizzly.http.server.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HabrWebServer {

    private static final Logger LOGGER = Grizzly.logger(HabrWebServer.class);

    public static void main(String[] args) {
        final HttpServer server = createServer("0.0.0.0", 8080);
        final ServerConfiguration config = server.getServerConfiguration();
        config.addHttpHandler(new HabrHandler(), "/");
        try {
            server.start();
            System.out.println("The server is running. \nPress enter to stop...");
            System.in.read();
        } catch (IOException ioe) {
            LOGGER.log(Level.SEVERE, ioe.toString(), ioe);
        } finally {
            server.shutdownNow();
        }
    }

    public static HttpServer createServer(String host, int port) {
        HttpServer server = new HttpServer();
        NetworkListener listener = new NetworkListener("grizzly", host, new PortRange(port));
        server.addListener(listener);
        return server;
    }

    private static class HabrHandler extends HttpHandler {
        @Override
        public void service(Request request, Response response) throws Exception {
            response.setContentType("text/plain");
            response.getWriter().write("Hello Habrahabr!");
        }
    }
}
