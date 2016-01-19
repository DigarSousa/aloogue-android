package alugueis.alugueis.model;

import com.orm.SugarRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by Pedreduardo / Eddgar on 02/12/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Place extends SugarRecord {

    public Place() {
    }

    private Long id;
    private String cpfCnpj;
    private String name;
    private User user;
    private AddressApp addressApp;
    private byte[] picture;
    private List<Phone> phones;
    private String businessInitialHour;
    private String businessFinalHour;

    //todo: sobrescrever métodos equals...hash...tostring
}
