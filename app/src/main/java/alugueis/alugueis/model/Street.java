package alugueis.alugueis.model;

import com.orm.SugarRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Pedreduardo on 02/12/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Street extends SugarRecord{
    public Street() {
    }

    private Long id;
    private String description;
}
