package alugueis.alugueis.model;

import com.orm.SugarRecord;

/**
 * Created by Pedreduardo on 02/12/2015.
 */
public class Street extends SugarRecord{
    public Street(){}
    private Long id;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    private String description;
}
