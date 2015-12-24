package alugueis.alugueis.model;

import com.orm.SugarRecord;

/**
 * Created by Pedreduardo on 30/11/2015.
 */
public class Payment extends SugarRecord {

    private Long id;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String description;
}
