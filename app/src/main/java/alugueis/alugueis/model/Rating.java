package alugueis.alugueis.model;

import com.orm.SugarRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Pedreduardo on 16/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Rating extends SugarRecord {

    public Rating() {
    }

    private Long id;
    private UserApp offerer;
    private UserApp professional;
    private Long rating;

}
