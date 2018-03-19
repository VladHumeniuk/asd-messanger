package lnu.asd.messenger;


import lnu.asd.messenger.web.socket.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class BackendApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BackendApplication.class, args);

        //context.getBean(SocketServer.class).start();
    }

}
