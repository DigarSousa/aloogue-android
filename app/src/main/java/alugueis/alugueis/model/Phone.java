package alugueis.alugueis.model;

import com.orm.SugarRecord;

/**
 * Created by Pedreduardo on 16/10/2015.
 */
public class Phone extends SugarRecord{

    public Phone(){}

    private Long id;
    private String number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
