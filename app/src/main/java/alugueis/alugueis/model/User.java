package alugueis.alugueis.model;

import com.orm.SugarRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;



/**
 * Created by Pedreduardo on 16/10/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User  extends SugarRecord{
    public User() {
    }

    private String name;
    private String email;
}
