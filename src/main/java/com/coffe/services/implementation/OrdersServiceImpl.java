package com.coffe.services.implementation;

import com.coffe.entity.*;
import com.coffe.exception.*;
import com.coffe.repository.OrdersRepository;
import com.coffe.services.declaration.*;
import com.coffe.shared.definition.BrewingDefinition;
import com.coffe.shared.definition.CustomCoffeeIngredientsDefinition;
import com.coffe.shared.definition.PayOrderDefinition;
import com.coffe.shared.enumeration.Dimension;
import com.coffe.shared.definition.OrderDefinition;
import com.coffe.shared.OrderTotalOutput;
import com.coffe.shared.OrdersTotalOutcome;
import com.coffe.shared.pair.IngredientAmountPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.*;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final CustomerService customerService;
    private final CoffeeService coffeeService;
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final Logger logger = LoggerFactory.getLogger(OrdersServiceImpl.class);

    public OrdersServiceImpl(OrdersRepository ordersRepository,
                             CustomerService customerService,
                             CoffeeService coffeeService,
                             RecipeService recipeService,
                             IngredientService ingredientService) {
        this.ordersRepository = ordersRepository;
        this.coffeeService = coffeeService;
        this.customerService = customerService;
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @Override
    public Orders createOrders(OrderDefinition orderDefinition)
            throws CustomerNotFoundException, CoffeeNotFoundException, NotEnoughIngredientsException {
        Customer customer = customerService.findCustomerByFirstNameAndLastName(orderDefinition.getCustomerFN(), orderDefinition.getCustomerLN());
        Coffee coffee = coffeeService.getCoffeeByNameAndDimension(orderDefinition.getCoffeeName(), "SMALL");
        Recipes recipe = coffee.getRecipe();

        Set<Ingredients> ingredientsList = recipe.getIngredients();
        Dimension dimension = orderDefinition.getCoffeeDimension();
        int extractedQuantity = orderDefinition.getNumberOfCoffees() + dimension.getAdditionalQuantity();
        checkErrorsAndWarnings(ingredientsList, extractedQuantity);

        recipeService.saveRecipe(recipe);

        Orders orders = Orders.builder()
                .customer(customer)
                .delivery(orderDefinition.getDelivery())
                .quantity(orderDefinition.getNumberOfCoffees())
                .totalCost(getPrice(ingredientsList, dimension, orderDefinition.getNumberOfCoffees()))
                .profit(dimension.getAdditionalPrice() * orderDefinition.getNumberOfCoffees())
                .auditDate(LocalDate.now())
                .coffeeName(coffee.getName())
                .build();

        return this.ordersRepository.save(orders);
    }

    @Override
    public OrderTotalOutput showOrders() {
        List<Orders> orders = (List<Orders>) ordersRepository.findAll();
        return buildOrderTotalOutput(orders);
    }

    @Override
    public OrderTotalOutput showOrderByDay(String date) {
        List<Orders> orders = (List<Orders>) ordersRepository.findAll();
        orders = orders.stream().filter(ord -> date.equals(ord.getStringAudit())).collect(toList());
        return buildOrderTotalOutput(orders);
    }

    @Override
    public Orders createCustomIngredientsOrder(CustomCoffeeIngredientsDefinition customDefinition)
            throws IngredientNotFoundException, NotEnoughIngredientsException, CustomerNotFoundException {
        Ingredients espresso = ingredientService.getIngredientByName("espresso");
        Customer customer = customerService.findCustomerByFirstNameAndLastName(customDefinition.getCustomerFN(), customDefinition.getCustomerLN());
        if (espresso.getQuantity() < customDefinition.getNumberOfShots() * customDefinition.getNumberOfCoffees()) {
            throw new NotEnoughIngredientsException("Not enough shots of espresso for the coffee!");
        }
        double profit = customDefinition.getDimension().getAdditionalPrice() * customDefinition.getNumberOfCoffees();
        Orders orders = Orders.builder()
                .auditDate(LocalDate.now())
                .quantity(customDefinition.getNumberOfCoffees())
                .coffeeName(customDefinition.getCustomerFN() + " " + customDefinition.getCustomerLN())
                .customer(customer)
                .delivery(customDefinition.getDelivery())
                .profit(profit)
                .totalCost(profit + espresso.getPrice() * customDefinition.getNumberOfShots())
                .build();
        if (customDefinition.getIngredients() == null || customDefinition.getIngredients().isEmpty()) {
            ordersRepository.save(orders);
            return orders;
        }
        int extractedQuantity = customDefinition.getNumberOfCoffees() * customDefinition.getDimension().getAdditionalQuantity();
        Set<Ingredients> ingredients = convertStringToIngredients(customDefinition.getIngredients());
        checkErrorsAndWarnings(ingredients, extractedQuantity);
        orders.setTotalCost(getPrice(ingredients, customDefinition.getDimension(), customDefinition.getNumberOfCoffees()));
        ordersRepository.save(orders);
        return orders;
    }

    @Override
    public List<Pair<String, String>> checkStock() {
        List<Ingredients> allIngredients = ingredientService.getAllIngredients();
        return allIngredients.stream().map(ingredient -> {
            String name = ingredient.getName();
            int quantity = ingredient.getQuantity();
            String decision = quantity > 9 ? "" + quantity : "Not enough for 3 big coffees!";
            return Pair.of(name, decision);
        }).collect(toList());
    }

    @Override
    public Orders brewCoffee(BrewingDefinition brewingDefinition)
            throws CustomerNotFoundException, IngredientNotFoundException, NotEnoughIngredientsException {
        Customer customer = customerService.findCustomerByFirstNameAndLastName(brewingDefinition.getCustomerFN(), brewingDefinition.getCustomerLN());
        Set<Ingredients> ingredients = convertStringToIngredients(brewingDefinition.getPairs().stream().map(IngredientAmountPair::getIngredient).collect(toList()));
        checkErrorsAndWarnings(ingredients, brewingDefinition.getCoffeeNumbers());
        double profit = brewingDefinition.getDimension().getAdditionalPrice() * brewingDefinition.getCoffeeNumbers();
        Orders orders = Orders.builder()
                .coffeeName(customer.getFirstName() + " " + customer.getLastName())
                .auditDate(LocalDate.now())
                .quantity(brewingDefinition.getCoffeeNumbers())
                .customer(customer)
                .delivery(brewingDefinition.getDelivery())
                .profit(profit)
                .totalCost(getPrice(ingredients, brewingDefinition.getDimension(), brewingDefinition.getCoffeeNumbers()))
                .build();
        ordersRepository.save(orders);
        return orders;
    }

    @Override
    public Orders payOrder(PayOrderDefinition orderDefinition)
            throws NotEnoughIngredientsException, CoffeeNotFoundException, CustomerNotFoundException, IncorrectCardException {
        checkCardDetails(orderDefinition);
        return this.createOrders(orderDefinition.getOrderDefinition());
    }


    private double getPrice(Set<Ingredients> ingredientsList, Dimension dimension, int numberOfCoffees) {
        int quantity = dimension.getAdditionalQuantity();
        double price = ingredientsList.stream().reduce(0.0, (sub, ing) -> sub + ing.getPrice() * quantity, Double::sum) //
                + dimension.getAdditionalPrice();
        price *= numberOfCoffees;
        return Math.round(price * 100.0) / 100.0;
    }

    private OrderTotalOutput buildOrderTotalOutput(List<Orders> orders) {
        Map<String, List<OrdersTotalOutcome>> orderOutcome = orders.stream()
                .collect(groupingBy(Orders::getStringAudit, collectingAndThen(toList(), list -> list.stream()
                        .map(order -> {
                            OrdersTotalOutcome ord = new OrdersTotalOutcome();
                            ord.setCoffeeName(order.getCoffeeName());
                            ord.setTotalPrice(order.getTotalCost());
                            return ord;
                        }).collect(toList()))));
        Map<String, Double> orderProfit = orders.stream()
                .collect(groupingBy(Orders::getStringAudit, collectingAndThen(toList(), list -> list.stream()
                        .reduce(0.0, (totalProfit, ord) -> totalProfit + ord.getProfit(), Double::sum))));

        return OrderTotalOutput.builder()
                .ordersPerDay(orderOutcome)
                .profitPerDay(orderProfit)
                .build();
    }

    private Set<Ingredients> convertStringToIngredients(List<String> ingredients) throws IngredientNotFoundException {
        List<Ingredients> ingredientsList = ingredients.stream()
                .map(ingrName -> {
                    try {
                        return ingredientService.getIngredientByName(ingrName);
                    } catch (IngredientNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(toList());
        if (ingredientsList.contains(null)) {
            throw new IngredientNotFoundException("Custom ingredient not found!");
        }
        return new HashSet<>(ingredientsList);
    }

    private void checkErrorsAndWarnings(Set<Ingredients> ingredientsList, int extractedQuantity) throws NotEnoughIngredientsException {
        boolean errorFlag = ingredientsList.stream().anyMatch((item) -> item.getQuantity() < extractedQuantity);
        if (errorFlag) {
            throw new NotEnoughIngredientsException("Not enough ingredients for the requested order!");
        }

        ingredientsList.forEach(ingredient -> {
            ingredient.setQuantity(ingredient.getQuantity() - extractedQuantity);
        });

        boolean warnFlag = ingredientsList.stream().anyMatch((item) -> item.getQuantity() < 9);
        if (warnFlag) {
            logger.warn("There are not enough ingredients to prepare another 3 large coffees!");
        }
    }

    private void checkCardDetails(PayOrderDefinition orderDefinition) throws IncorrectCardException, CustomerNotFoundException {
        validateCardNumber(orderDefinition.getCardNumber());
        validateCustomer(orderDefinition.getCustomerName());
        validateCIV(orderDefinition.getCiv());
        validateDate(orderDefinition.getExpirationDate());
    }

    private void validateCardNumber(String cardNr) throws IncorrectCardException {
        if (cardNr.length() > 16 || cardNr.length() < 13) {
            throw new IncorrectCardException("Incorrect card number!");
        }
        int luhnSum = 0;
        for (int i = 0; i < cardNr.length(); i++) {
            int luhnSum1 = Integer.parseInt(cardNr.charAt(i) + "");
            if (i % 2 != 0) {
                luhnSum += luhnSum1;
            } else {
                int value = luhnSum1 * 2;
                if (value >= 10) {
                    value = value % 10 + value / 10;
                }
                luhnSum += value;
            }
        }
        if (luhnSum % 10 != 0) {
            throw new IncorrectCardException("Incorrect card number!");
        }
    }

    private void validateCustomer(String customerName) throws CustomerNotFoundException {
        String[] names = customerName.split(" ");
        customerService.findCustomerByFirstNameAndLastName(names[0], names[1]);
    }

    private void validateCIV(String civ) throws IncorrectCardException {
        if (civ.length() != 3) {
            throw new IncorrectCardException("Incorrect CIV!");
        }
    }

    private void validateDate(String date) throws IncorrectCardException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate cardExpirationDate = LocalDate.parse(date, dateTimeFormatter);
        if (cardExpirationDate.isBefore(LocalDate.now())) {
            throw new IncorrectCardException("Card expired!");
        }
    }
}
