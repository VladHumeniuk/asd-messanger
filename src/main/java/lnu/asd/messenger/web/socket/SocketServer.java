package lnu.asd.messenger.web.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lnu.asd.messenger.domain.GroupChatMessage;
import lnu.asd.messenger.domain.PrivateChatMessage;
import lnu.asd.messenger.domain.dbentity.Participates;
import lnu.asd.messenger.domain.dbentity.User;
import lnu.asd.messenger.repository.ParticipatesRepository;
import lnu.asd.messenger.repository.UserRepository;
import lnu.asd.messenger.web.entity.socket.LoginEntity;
import lnu.asd.messenger.web.mapper.GroupMessageMapper;
import lnu.asd.messenger.web.mapper.PrivateMessageMapper;
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

    private ParticipatesRepository participatesRepository;

    private PrivateMessageMapper privateMessageMapper;

    private GroupMessageMapper groupMessageMapper;

    @Inject
    public void setPrivateMessageMapper(PrivateMessageMapper privateMessageMapper) {
        this.privateMessageMapper = privateMessageMapper;
    }

    @Inject
    public void setGroupMessageMapper(GroupMessageMapper groupMessageMapper) {
        this.groupMessageMapper = groupMessageMapper;
    }

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Inject
    public void setParticipatesRepository(ParticipatesRepository participatesRepository) {
        this.participatesRepository = participatesRepository;
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
            System.out.println(e.getMessage());
        } finally {
            closeAll();
        }
    }

    public void sendMessage(PrivateChatMessage message) throws JsonProcessingException {
        String messageJson = objectMapper.writeValueAsString(privateMessageMapper.map(message));
        synchronized(connections) {
            for (Connection connection : connections) {
                if (message.getRecipient().getId().equals(connection.getUserId())) {
                    connection.out.println(messageJson);
                }
            }
        }
    }

    public void sendMessage(GroupChatMessage message) throws JsonProcessingException {
        List<Participates> participates = participatesRepository.findById_Group(message.getGroup());
        Set<Long> recipientIds = new HashSet<>();

        for (Participates participate : participates) {
            recipientIds.add(participate.getId().getUser().getId());
        }

        String messageJson = objectMapper.writeValueAsString(groupMessageMapper.map(message));
        synchronized(connections) {
            for (Connection connection : connections) {
                if (recipientIds.contains(connection.getUserId())) {
                    connection.out.println(messageJson);
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
                for (Connection connection : connections) {
                    (connection).close();
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
                System.out.println(e.getMessage());
                close();
            }
        }

        @Override
        public void run() {
            try {
                String loginEntityString = in.readLine();
                boolean login;
                try {
                    LoginEntity loginEntity = objectMapper.readValue(loginEntityString, LoginEntity.class);
                    login = login(loginEntity);
                    userName = loginEntity.getUserName();
                    userId = loginEntity.getUserId();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    login = false;
                }
                if (!login) {
                    out.println("wrong");
                    close();
                } else {
                    out.println("ok");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
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
