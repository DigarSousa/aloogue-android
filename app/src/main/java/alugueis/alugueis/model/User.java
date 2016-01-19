package alugueis.alugueis.model;

import com.orm.SugarRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * Created by Pedreduardo on 16/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends SugarRecord {
    public User() {
    }

    private Long id;
    private String name;
    private String email;
    private String password;
    private double latitude;
    private double longitude;
    private AddressApp addressApp;
    private byte[] picture;
    private List<Phone> phones;


}
