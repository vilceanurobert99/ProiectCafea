package com.coffe.controller;

import com.coffe.entity.Orders;
import com.coffe.exception.*;
import com.coffe.services.declaration.OrdersService;
import com.coffe.shared.definition.BrewingDefinition;
import com.coffe.shared.definition.CustomCoffeeIngredientsDefinition;
import com.coffe.shared.definition.OrderDefinition;
import com.coffe.shared.OrderTotalOutput;
import com.coffe.shared.definition.PayOrderDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping("/save")
    public ResponseEntity<Orders> saveOrder(@Valid @RequestBody OrderDefinition orderDefinition)
            throws NotEnoughIngredientsException, CoffeeNotFoundException, CustomerNotFoundException {
        return ResponseEntity.ok(this.ordersService.createOrders(orderDefinition));
    }

    @PostMapping("/pay")
    public ResponseEntity<Orders> payOrder(@Valid @RequestBody PayOrderDefinition orderDefinition)
            throws CoffeeNotFoundException, IncorrectCardException, CustomerNotFoundException, NotEnoughIngredientsException {
        return ResponseEntity.ok(this.ordersService.payOrder(orderDefinition));
    }

    @PostMapping("/customIngredients")
    public ResponseEntity<Orders> saveCustomIngredientsOrder(@RequestBody CustomCoffeeIngredientsDefinition customDefinition)
            throws IngredientNotFoundException, NotEnoughIngredientsException, CustomerNotFoundException {
        return ResponseEntity.ok(ordersService.createCustomIngredientsOrder(customDefinition));
    }

    @PostMapping("/brewing")
    public ResponseEntity<Orders> brewCustomCoffee(@Valid @RequestBody BrewingDefinition brewingDefinition)
            throws CustomerNotFoundException, NotEnoughIngredientsException, IngredientNotFoundException {
        return ResponseEntity.ok(ordersService.brewCoffee(brewingDefinition));
    }

    @GetMapping("/date/{data}")
    public ResponseEntity<OrderTotalOutput> showOutputByDay(@PathVariable String data) {
        return ResponseEntity.ok(ordersService.showOrderByDay(data));
    }

    @GetMapping("/totalOutcome")
    public ResponseEntity<OrderTotalOutput> showTotalOutput() {
        return ResponseEntity.ok(ordersService.showOrders());
    }

    @GetMapping("/checkStock")
    public ResponseEntity<List<Pair<String, String>>> checkStock() {
        return ResponseEntity.ok(ordersService.checkStock());
    }
}
