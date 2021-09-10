package com.coffe.services.declaration;

import com.coffe.entity.Orders;
import com.coffe.exception.*;
import com.coffe.shared.definition.BrewingDefinition;
import com.coffe.shared.definition.CustomCoffeeIngredientsDefinition;
import com.coffe.shared.definition.OrderDefinition;
import com.coffe.shared.OrderTotalOutput;
import com.coffe.shared.definition.PayOrderDefinition;
import org.springframework.data.util.Pair;

import java.util.List;

public interface OrdersService {
    Orders createOrders(OrderDefinition orderDefinition) throws CustomerNotFoundException, CoffeeNotFoundException, NotEnoughIngredientsException;

    OrderTotalOutput showOrders();

    OrderTotalOutput showOrderByDay(String date);

    Orders createCustomIngredientsOrder(CustomCoffeeIngredientsDefinition customDefinition) throws IngredientNotFoundException, NotEnoughIngredientsException, CustomerNotFoundException;

    List<Pair<String, String>> checkStock();

    Orders brewCoffee(BrewingDefinition brewingDefinition) throws CustomerNotFoundException, IngredientNotFoundException, NotEnoughIngredientsException;

    Orders payOrder(PayOrderDefinition orderDefinition) throws NotEnoughIngredientsException, CoffeeNotFoundException, CustomerNotFoundException, IncorrectCardException;
}
