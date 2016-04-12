package alugueis.alugueis.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class AddressApp implements Serializable{
    
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
    private String zipCode;

    @Override
    public String toString() {
        return
                this.street.getDescription() + ", " +
                this.number + " - " +
                this.neighbourhood.getDescription() + " - " +
                this.city.getDescription() + " - " +
                this.stateFU.getDescription() + " - " +
                this.country.getDescription();
    }
    //todo: sobrescrever métodos equals...hash...tostring
}
