package org.example;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.CustomServlet;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) throws Exception {
        CustomServlet customServlet = new CustomServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(customServlet), "/mirror");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
        Logger.getGlobal().info("Server started");
        server.join();
    }
}

