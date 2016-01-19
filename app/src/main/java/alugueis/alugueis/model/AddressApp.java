package alugueis.alugueis.model;

import com.orm.SugarRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Pedreduardo on 30/11/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AddressApp extends SugarRecord{

    public AddressApp() {
    }

    private Long id;
    private StateFU stateFU;
    private City city;
    private Street street;
    private String number;
    private String additional;
    private Neighbourhood neighbourhood;
    private Country country;
    private double latitude;
    private double longitute;

    @Override
    public String toString() {
        return
                this.street + ", " +
                        this.number + "- " +
                        this.neighbourhood + "- " +
                        this.city.getDescription() + "- " +
                        this.stateFU.getDescription() + "- " +
                        this.country.getDescription();
    }
    //todo: sobrescrever métodos equals...hash...tostring
}
