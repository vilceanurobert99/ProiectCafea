package com.coffe.shared.definition;

import com.coffe.shared.enumeration.Delivery;
import com.coffe.shared.enumeration.Dimension;
import com.coffe.shared.pair.IngredientAmountPair;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrewingDefinition {
    @NonNull
    private List<IngredientAmountPair> pairs;
    @NonNull
    private Delivery delivery;
    @NonNull
    private Dimension dimension;
    @NonNull
    private String customerFN;
    @NonNull
    private String customerLN;
    @NonNull
    private int coffeeNumbers;
}
