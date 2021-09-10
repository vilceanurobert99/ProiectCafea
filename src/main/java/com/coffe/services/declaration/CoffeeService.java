package com.coffe.services.declaration;

import com.coffe.entity.Coffee;
import com.coffe.exception.CoffeeNotFoundException;

import java.util.List;

public interface CoffeeService {
    Coffee saveCoffee(Coffee coffee);

    Coffee getCoffeeByNameAndDimension(String name, String dimension) throws CoffeeNotFoundException;

    List<Coffee> getCoffeeByName(String name) throws CoffeeNotFoundException;

    List<Coffee> getBeverages();
}
