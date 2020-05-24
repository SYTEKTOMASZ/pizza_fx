package pizza_fx.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PizzaModel {
    private String name;
    private String ingredients;
    private String type;
    private Double price;
}

