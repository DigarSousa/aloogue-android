package alugueis.alugueis.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Product implements Serializable {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Place place;
    private Double price;
    private String rentType;
}
