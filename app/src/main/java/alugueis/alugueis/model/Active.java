package alugueis.alugueis.model;

import com.orm.SugarRecord;

/**
 * Created by Pedreduardo on 30/11/2015.
 */
public class Active extends SugarRecord {

    public Active() {
    }

    public Long getActiveCategory() {
        return activeCategory;
    }

    public void setActiveCategory(Long activeCategory) {
        this.activeCategory = activeCategory;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Long id;
    private String code;
    private String description;
    private Long activeCategory;
}
