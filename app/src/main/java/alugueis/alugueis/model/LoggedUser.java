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
public class LoggedUser extends SugarRecord {

    private static LoggedUser logged;

    public LoggedUser() {
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

    public static LoggedUser getInstance() {
        if (logged == null)
            logged = new LoggedUser();
        return logged;
    }
}
