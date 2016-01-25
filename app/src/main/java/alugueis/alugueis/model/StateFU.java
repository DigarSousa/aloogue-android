package alugueis.alugueis.model;

import com.orm.SugarRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Pedreduardo on 30/11/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StateFU extends SugarRecord {

    public StateFU() {
    }
    private Long id;
    private String description;




}
