package alugueis.alugueis.model;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by Pedreduardo on 16/10/2015.
 */
public class RentContract extends SugarRecord {

    public RentContract() {
    }

    private Long id;
    private Long description;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(Long activeUser) {
        this.activeUser = activeUser;
    }

    public Long getIdChargeEvent() {
        return idChargeEvent;
    }

    public void setIdChargeEvent(Long idChargeEvent) {
        this.idChargeEvent = idChargeEvent;
    }

    public Date getInitialEffective() {
        return initialEffective;
    }

    public void setInitialEffective(Date initialEffective) {
        this.initialEffective = initialEffective;
    }

    public Date getFinalEffective() {
        return finalEffective;
    }

    public void setFinalEffective(Date finalEffective) {
        this.finalEffective = finalEffective;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    private Long idUser;
    private Long activeUser;
    private Long idChargeEvent;

    private Date initialEffective;
    private Date finalEffective;
    private Long quantity;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getDescription() {
        return description;
    }

    public void setDescription(Long description) {
        this.description = description;
    }
}
