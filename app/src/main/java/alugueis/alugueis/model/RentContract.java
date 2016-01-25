package alugueis.alugueis.model;

import com.orm.SugarRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * Created by Pedreduardo on 16/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RentContract extends SugarRecord {

    public RentContract() {
    }

    private Long id;
    private String description;
    private UserApp userApp;
    private Place place;
    //todo: Criar model ... private Long chargeEvent;
    private Date initialEffective;
    private Date finalEffective;
    private Long quantity;
    private Rating rating;
}
