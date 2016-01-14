package alugueis.alugueis.model;

import com.orm.SugarRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by Pedreduardo on 02/12/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Place extends SugarRecord {

    public Place() {
    }

    private Long id;
    private Long idUser;
    private String cpfCnpj;
    private String name;
    private String email;
    private String password;
    private double latitude;
    private double longitude;
    private AddressApp addressApp;
    private byte[] picture;
    private int loggedAs;
    private List<Phone> phones;
    private String businessInitialHour;
    private String businessFinalHour;

}
