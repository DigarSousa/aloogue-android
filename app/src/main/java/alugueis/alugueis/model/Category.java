package alugueis.alugueis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class Category  {

    private Long id;
    private String description;
}
