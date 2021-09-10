package com.coffe.shared.definition;

import com.coffe.entity.Coffee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeDefinition {
    private String name;
    private List<Coffee> coffees;
}
