package client;

import dto.ProductDto;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import utils.JsonDeserializer;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.List;


/**
 * Http client to connect to main shop application.
 */
@Singleton
@Log4j
public class StandHttpClient {

    @Inject
    private JsonDeserializer deserializer;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    public List<ProductDto> getInitItems() {
        HttpGet request = new HttpGet("http://localhost:8080/stand");

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return deserializer.deserialize(result);
            }
        } catch (IOException e) {
            log.error("Data can not be reach with exception: " + e);
        }

        return Collections.emptyList();
    }
}
