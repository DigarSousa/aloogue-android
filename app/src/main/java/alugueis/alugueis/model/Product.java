package alugueis.alugueis.model;

import android.os.Parcelable;
import android.support.design.internal.ParcelableSparseArray;

import lombok.Data;

import java.io.Serializable;

@Data
public class Product implements Serializable {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Place place;
    private Double value;
    private String rentType;
}
