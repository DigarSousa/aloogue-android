package alugueis.alugueis.model;

import lombok.Data;

@Data
public class Rating {


    private Long id;
    private UserApp offerer;
    private UserApp professional;
    private Long rating;

}
