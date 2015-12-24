package alugueis.alugueis.model;

import com.orm.SugarRecord;

/**
 * Created by Pedreduardo on 30/11/2015.
 */
public class City extends SugarRecord {

    public City(){}

    private Long id;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;


    public void setId(Long id) {
        this.id = id;
    }


}
