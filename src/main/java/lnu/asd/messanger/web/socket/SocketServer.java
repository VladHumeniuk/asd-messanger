package lnu.asd.messanger.web.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lnu.asd.messanger.domain.PrivateChatMessage;
import lnu.asd.messanger.domain.User;
import lnu.asd.messanger.repository.UserRepository;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

@Component
@Singleton
public class SocketServer {

    private List<Connection> connections =
            Collections.synchronizedList(new ArrayList<Connection>());
    private ServerSocket server;

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserRepository userRepository;

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void start() {
        try {
            server = new ServerSocket(8283);

            while (true) {
                Socket socket = server.accept();

                Connection con = new Connection(socket);
                connections.add(con);

                con.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    public void sendMessage(PrivateChatMessage message) throws JsonProcessingException {
        synchronized(connections) {
            Iterator<Connection> iter = connections.iterator();
            while(iter.hasNext()) {
                Connection connection = iter.next();
                if (message.getRecipient().getId().equals(connection.getUserId())) {
                    connection.out.println(objectMapper.writeValueAsString(message));
                }
            }
        }
    }

    private boolean login(LoginEntity loginEntity) {
        User user = userRepository.findOne(loginEntity.getUserId());
        return user != null && user.getUserName().equals(loginEntity.getUserName());
    }

    private void closeAll() {
        try {
            server.close();

            synchronized(connections) {
                Iterator<Connection> iter = connections.iterator();
                while(iter.hasNext()) {
                    ((Connection) iter.next()).close();
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;

        private long userId;
        private String userName;

        public Connection(Socket socket) {
            this.socket = socket;

            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }

        @Override
        public void run() {
            try {
                String loginEntityString = in.readLine();
                boolean login = true;
                try {
                    LoginEntity loginEntity = objectMapper.readValue(loginEntityString, LoginEntity.class);
                    login = login(loginEntity);
                    userName = loginEntity.getUserName();
                    userId = loginEntity.getUserId();
                } catch (IOException e) {
                    e.printStackTrace();
                    login = false;
                }
                if (!login) {
                    out.println("wrong");
                    close();
                } else {
                    out.println("ok");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        public long getUserId() {
            return userId;
        }

        public String getUserName() {
            return userName;
        }

        public void close() {
            try {
                out.println(".server_exit");
                in.close();
                out.close();
                socket.close();

                connections.remove(this);
                if (connections.size() == 0) {
                    //Server.this.closeAll();
                    //System.exit(0);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
