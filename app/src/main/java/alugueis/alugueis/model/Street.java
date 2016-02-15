package alugueis.alugueis.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Street implements Serializable{

    private Long id;
    private String description;
}
