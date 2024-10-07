package edu.school21.sockets.application;

import edu.school21.sockets.config.Config;
import edu.school21.sockets.server.Server;
import lombok.val;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        int port = 2345;
        for (String arg : args) {
            if (arg.startsWith("--port=")) {
                port = Integer.parseInt(arg.substring("--port=".length()));
            }
        }

        System.setProperty("server.port", String.valueOf(port));

        val context = new AnnotationConfigApplicationContext(Config.class);

        val server = context.getBean(Server.class);
        server.startServer();
    }
}