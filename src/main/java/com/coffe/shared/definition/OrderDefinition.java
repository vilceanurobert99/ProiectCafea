package com.coffe.shared.definition;

import com.coffe.shared.enumeration.Delivery;
import com.coffe.shared.enumeration.Dimension;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDefinition {
    @NotNull
    private String customerFN;
    @NotNull
    private String customerLN;
    @NotNull
    private String coffeeName;
    @NotNull
    private Dimension coffeeDimension;
    @NotNull
    private Delivery delivery;
    @NotNull
    private int numberOfCoffees;
}
