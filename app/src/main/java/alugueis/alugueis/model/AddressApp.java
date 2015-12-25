package alugueis.alugueis.model;

import com.orm.SugarRecord;

/**
 * Created by Pedreduardo on 30/11/2015.
 */
public class AddressApp extends SugarRecord {

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StateFU getStateFU() {
        return stateFU;
    }

    public void setStateFU(StateFU stateFU) {
        this.stateFU = stateFU;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(Neighbourhood neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }


    //Concatening all address components
    //----------------------------------------------------------------------
    public String makeAddress() {

        return
                this.street + ", " +
                        this.number + "- " +
                        this.neighbourhood + "- " +
                        this.city.getDescription() + "- " +
                        this.stateFU.getDescription() + "- " +
                        this.country.getDescription();
    }
    //----------------------------------------------------------------------

}
