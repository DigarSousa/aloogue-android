package alugueis.alugueis.model;

import java.io.Serializable;

import lombok.Data;
@Data
public class PictureFile implements Serializable{

    private Long id;
    private byte[] sourceFile;
    private UserApp userApp;
}
