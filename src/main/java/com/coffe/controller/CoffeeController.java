package com.coffe.controller;

import com.coffe.entity.Coffee;
import com.coffe.exception.CoffeeNotFoundException;
import com.coffe.services.declaration.CoffeeService;
import com.coffe.shared.definition.CoffeeDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/coffee")
public class CoffeeController {
    private final CoffeeService coffeeService;

    @Autowired
    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @PostMapping("/save")
    public ResponseEntity<Coffee> saveCoffee(@Valid @RequestBody Coffee coffee) {
        return ResponseEntity.ok(coffeeService.saveCoffee(coffee));
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Coffee>> getCoffeeByName(@PathVariable String name) throws CoffeeNotFoundException {
        return ResponseEntity.ok(this.coffeeService.getCoffeeByName(name));
    }

    @GetMapping("/{name}/{dimension}")
    public ResponseEntity<Coffee> getCoffeeByNameAndDimension(@PathVariable String name, @PathVariable String dimension) throws CoffeeNotFoundException {
        return ResponseEntity.ok(this.coffeeService.getCoffeeByNameAndDimension(name, dimension));
    }

    @GetMapping("/beverage")
    public ResponseEntity<CoffeeDefinition> getCoffeeBeverages() {
        CoffeeDefinition definition = CoffeeDefinition.builder()
                .name("CoffeeShop")
                .coffees(coffeeService.getBeverages())
                .build();

        return ResponseEntity.ok(definition);
    }
}
