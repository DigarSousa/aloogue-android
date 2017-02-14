package alugueis.alugueis.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address implements Serializable {
    private Long id;
    private String country;
    private String stateFU;
    private String city;
    private String neighbourhood;
    private String street;
    private String zipCode;
    private Long number;
    private Double latitude;
    private Double longitude;

    @JsonBackReference
    private Place place;

    public String toString() {
        return street + ", " +
                number + " - " +
                neighbourhood + " - " +
                city + " - " +
                stateFU + " - " +
                country;
    }
}
