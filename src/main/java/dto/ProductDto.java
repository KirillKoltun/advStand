package dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO class.
 */
@Data
public class ProductDto implements Serializable {
    private Long id;
    private String name;
    private String productGroup;
    private Double price;
    private String description;
    private int stock;
    private String image;

}
