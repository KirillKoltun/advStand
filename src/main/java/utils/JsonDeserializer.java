package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ProductDto;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

import javax.ejb.Singleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * JSON deserializer class from {@link String} to list of {@link ProductDto} objects.
 */
@Singleton
@Log4j
public class JsonDeserializer {

    private ObjectMapper mapper = new ObjectMapper();

    public List<ProductDto> deserialize(String jsonString) {
        List<ProductDto> items = new ArrayList<>();
        try {
            items = mapper.readValue(jsonString, new TypeReference<List<ProductDto>>() {
            });
        } catch (JsonProcessingException e) {
            log.error(String.format("Error while converting string %s to object", jsonString));
        }
        return items;
    }

    public String serialize(List<ProductDto> list) {
        String resultStr = "";
        try {
            resultStr = mapper.writeValueAsString(list);
        } catch (IOException e) {
            log.error(String.format("Error while converting object %s to JSON string", list));
        }
        return resultStr;
    }
}
