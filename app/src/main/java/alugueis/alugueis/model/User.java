package alugueis.alugueis.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User implements Serializable {
    private  Long id;
    private  String name;
    private  String email;
    private  String password;
}
