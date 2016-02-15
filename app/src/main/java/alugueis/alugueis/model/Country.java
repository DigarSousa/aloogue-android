package alugueis.alugueis.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Country implements Serializable{

    private Long id;
    private String description;
}
