package service;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;

import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import java.util.logging.Level;

/**
 * MQ Consumer class.
 */
@Singleton
@Log4j
public class Reciever {

    public static final String QUEUE_NAME = "QUEUE";

    @Inject private StandListener listener;

    private ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
    private Connection connection = null;
    private Session session = null;

    public void start() {
        factory.setBrokerURL("failover://tcp://localhost:61616");
        try {
            if (connection == null) {
                connection = factory.createQueueConnection();
                connection.start();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Queue queue = session.createQueue(QUEUE_NAME);

                log.info("ActiveMQ connection established");

                ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer) session.createConsumer(queue);
                consumer.setMessageListener(listener);
            }
        } catch (JMSException e) {
            log.error("Error in init() of ActiveMQ connection, exception: " + e.getMessage());
        }
    }

    @PreDestroy
    public void close() {
        try {
            if (session != null) {
                session.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (JMSException e) {
            log.error("Error while closing Consumer, exception: " + e.getMessage());
        }

    }
}
