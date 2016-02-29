package alugueis.alugueis.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserApp implements Serializable {

    private Long id;
    private String name;
    private String email;
    private String password;
}
