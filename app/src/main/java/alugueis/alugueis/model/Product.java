package alugueis.alugueis.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Product implements Serializable {

    private Long id;
    private String code;
    private String description;
    private Place place;
}
