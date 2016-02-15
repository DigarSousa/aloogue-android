package alugueis.alugueis.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Phone implements Serializable{

    private Long id;
    private String number;
}
