package alugueis.alugueis.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

import java.io.Serializable;

@Data
public class Place implements Serializable {

    private Long id;
    private String cpfCnpj;
    private String name;
    private UserApp userApp;

    @JsonManagedReference
    private Address address;
    private String phone;
    private String businessInitialHour;
    private String businessFinalHour;

    private byte[] picture;
}
