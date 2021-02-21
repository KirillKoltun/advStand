package service;

import client.StandHttpClient;
import dto.ProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;
import org.omnifaces.cdi.Startup;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;

/**
 * JSF page data holder class.
 */
@Named
@ApplicationScoped
@NoArgsConstructor
@Getter
@Setter
@Log4j
@Startup
public class PopularProductsView implements Serializable {

    @Inject @Push(channel = "push") PushContext context;
    @Inject private Reciever reciever;
    @Inject private StandHttpClient httpClient;

    private List<ProductDto> items;
    private ProductDto selectedItem = new ProductDto();
    private String hello = "Most Popular Products";



    @PostConstruct
    public void init() {
        reciever.start();
        items = httpClient.getInitItems();
        log.info("Initialization of backing bean complete: " + (items.size() != 0 ? "successfully" : "failed"));
    }

    public void update(List<ProductDto> items) {
        this.items = items;
        context.send("update form");
        log.info("Update request was published to client");
    }
}
