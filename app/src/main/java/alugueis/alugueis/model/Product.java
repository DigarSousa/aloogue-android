package alugueis.alugueis.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties({"selected"})
public class Product implements Serializable {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Place place;
    private Double price;
    private String rentType;


    private Boolean selected;

    {
        selected = false;
    }
}
