package com.coffe.shared.definition;

import com.coffe.shared.enumeration.Delivery;
import com.coffe.shared.enumeration.Dimension;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.List;

@Getter
@Setter
public class CustomCoffeeIngredientsDefinition {
    @NonNull
    private int numberOfShots;
    @NonNull
    private String customerFN;
    @NonNull
    private String customerLN;
    private List<String> ingredients;
    @NonNull
    private Dimension dimension;
    @NonNull
    private Delivery delivery;
    @NonNull
    private int numberOfCoffees;
}
