package alugueis.alugueis.model;

import com.orm.SugarRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Pedreduardo on 16/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Phone extends SugarRecord{

    public Phone() {
    }

    private Long id;
    private String number;
}
