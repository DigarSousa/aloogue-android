package alugueis.alugueis.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserApp implements Serializable {

    private  Long id;
    private  String name;
    private  String email;
    private  String password;
}
