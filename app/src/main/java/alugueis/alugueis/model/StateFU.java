package alugueis.alugueis.model;

import com.orm.SugarRecord;

/**
 * Created by Pedreduardo on 30/11/2015.
 */
public class StateFU extends SugarRecord {

    public StateFU(){}

    private Long id;
    private String description;

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
