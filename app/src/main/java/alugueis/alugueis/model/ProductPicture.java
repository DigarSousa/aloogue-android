package alugueis.alugueis.model;

import java.io.Serializable;

import lombok.Data;
@Data
public class ProductPicture implements Serializable{

    private Long id;
    private byte[] sourceFile;
    private Place place;
}
