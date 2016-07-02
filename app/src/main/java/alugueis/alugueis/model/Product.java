package alugueis.alugueis.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Product implements Serializable {

    private Long id;
    private String code;
    private String description;
    private Place place;
    private Double value;
    private String rentType;
}
