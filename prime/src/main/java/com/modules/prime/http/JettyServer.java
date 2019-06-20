package com.modules.prime.http;

import com.modules.prime.log.Logger;
import com.modules.prime.log.LoggerFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyServer {
    private static final transient Logger logger = LoggerFactory.getLogger(JettyServer.class);
    private void run() {
        Server jettyServer = new Server(8080);
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");

        contextHandler.addServlet(new ServletHolder(new QrServlet()), "/qr/*");

        jettyServer.setHandler(contextHandler);
        try {
            jettyServer.start();
        } catch (Exception e) {
            logger.error(e);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        new JettyServer().run();
    }
}
