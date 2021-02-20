package service;

import dto.ProductDto;;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import utils.JsonDeserializer;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;
import java.util.logging.Level;

/**
 * MQ listener class.
 */
@Log4j
@Dependent
public class StandListener implements MessageListener {

    @Inject private JsonDeserializer deserializer;
    @Inject private PopularProductsView popularProductsView;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage msg = (TextMessage) message;
                String text = msg.getText();
                List<ProductDto> items = deserializer.deserialize(text);

                log.info("Message from %s was read" + Reciever.QUEUE_NAME);

                popularProductsView.update(items);
            } else {
                log.error("Not supported message has been received : %s"+ message);
            }
        } catch (JMSException e) {
            log.error( "Exception in ShopListener: " + e.getMessage());
        }
    }
}
