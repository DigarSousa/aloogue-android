package alugueis.alugueis.model;

import com.orm.SugarRecord;

/**
 * Created by Pedreduardo on 30/11/2015.
 */
public class Country extends SugarRecord {


    public Country(){}

    private Integer id;
    private String description;

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
