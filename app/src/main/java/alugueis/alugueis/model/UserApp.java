package alugueis.alugueis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Created by Pedreduardo on 16/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserApp {
    public UserApp() {
    }

    private Long id;
    private String name;
    private String email;
    private String password;
    //private double latitude;
    //private double longitude;
    //private AddressApp addressApp;
    private byte[] picture;
    //private List<Phone> phones;


}
