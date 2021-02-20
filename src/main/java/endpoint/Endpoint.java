package endpoint;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.logging.Level;

/**
 * App's main endpoint class.
 */
@ServerEndpoint("/push")
@Log4j
public class Endpoint {

    @OnMessage
    public String onMessage(Session session, String message) {
        log.info("Socket received message : " + message);
        return message;
    }

    @OnOpen
    public void onOpen(Session session) {
        log.info("Server socket connected");
    }

    @OnClose
    public void onClose() {
        log.info("Server socket closed");
    }

    @OnError
    public void onError(Throwable e) {
        log.info("Exception in Server Socket : " + e.getMessage());
    }
}
