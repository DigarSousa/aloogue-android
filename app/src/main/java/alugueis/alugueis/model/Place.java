package alugueis.alugueis.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
public class Place implements Serializable {

    private Long id;
    private String cpfCnpj;
    private String name;
    private UserApp userApp;
    private AddressApp addressApp;
    private List<Phone> phones;
    private String businessInitialHour;
    private String businessFinalHour;
}
