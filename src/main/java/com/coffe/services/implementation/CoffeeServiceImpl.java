package com.coffe.services.implementation;

import com.coffe.entity.Coffee;
import com.coffe.exception.CoffeeNotFoundException;
import com.coffe.repository.CoffeeRepository;
import com.coffe.services.declaration.CoffeeService;
import com.coffe.shared.enumeration.Dimension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeServiceImpl implements CoffeeService {
    private final CoffeeRepository coffeeRepository;

    @Autowired
    public CoffeeServiceImpl(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @Override
    public Coffee saveCoffee(Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    @Override
    public Coffee getCoffeeByNameAndDimension(String name, String dimension) throws CoffeeNotFoundException {
        return coffeeRepository.findCoffeeByNameIgnoreCaseAndDimension(name, Dimension.valueOf(dimension.toUpperCase())) //
                .orElseThrow(() -> new CoffeeNotFoundException("Coffee not found in our shop!"));
    }

    @Override
    public List<Coffee> getCoffeeByName(String name) throws CoffeeNotFoundException {
        List<Coffee> coffees = coffeeRepository.findCoffeeByNameIgnoreCase(name);
        if (coffees.isEmpty()) {
            throw new CoffeeNotFoundException("Coffee not found by name!");
        }
        return coffees;
    }

    @Override
    public List<Coffee> getBeverages() {
        return (List<Coffee>) this.coffeeRepository.findAll();
    }
}
