package alugueis.alugueis.model;

import com.orm.SugarRecord;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Pedreduardo on 30/11/2015.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Product extends SugarRecord {

    public Product() {
    }

    private Long id;
    private String code;
    private String description;
    private Long activeCategory;
}