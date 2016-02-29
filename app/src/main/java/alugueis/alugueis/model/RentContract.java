package alugueis.alugueis.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@Data
public class RentContract implements Serializable {

    private Long id;
    private String description;
    private UserApp userApp;
    private Place place;
    private Date initialEffective;
    private Date finalEffective;
    private Long quantity;
    private Rating rating;
}
