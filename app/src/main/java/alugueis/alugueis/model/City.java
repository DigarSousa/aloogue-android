package alugueis.alugueis.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class City implements Serializable {

    private Long id;
    private String description;
}
