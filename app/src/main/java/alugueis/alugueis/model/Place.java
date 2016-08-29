package alugueis.alugueis.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Place implements Serializable {

    private Long id;
    private String cpfCnpj;
    private String name;
    private UserApp userApp;

    @JsonManagedReference
    private Address address;
    private String phone;
    private String startHour;
    private String finishHour;
}
