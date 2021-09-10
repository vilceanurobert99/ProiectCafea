package com.coffe.controller;

import com.coffe.entity.Coffee;
import com.coffe.entity.Ingredients;
import com.coffe.entity.Recipes;
import com.coffe.exception.CoffeeNotFoundException;
import com.coffe.exception.IngredientNotFoundException;
import com.coffe.services.declaration.CoffeeService;
import com.coffe.services.declaration.IngredientService;
import com.coffe.services.declaration.RecipeService;
import com.coffe.shared.definition.RecipeDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;
    private final CoffeeService coffeeService;
    private final IngredientService ingredientService;

    @Autowired
    public RecipeController(RecipeService recipeService,
                            CoffeeService coffeeService,
                            IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.coffeeService = coffeeService;
        this.ingredientService = ingredientService;
    }

    @PostMapping("/save")
    public ResponseEntity<Recipes> saveRecipe(@RequestBody RecipeDefinition recipeDefinition) throws CoffeeNotFoundException, IngredientNotFoundException {
        Coffee coffee = coffeeService.getCoffeeByNameAndDimension(recipeDefinition.getCoffeeName(), "SMALL");
        Set<Ingredients> ingredients = recipeDefinition.getIngredientsName()
                .stream()
                .map(ingredient -> {
                    try {
                        return ingredientService.getIngredientByName(ingredient);
                    } catch (IngredientNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                }).collect(toSet());
        if (ingredients.contains(null)) {
            throw new IngredientNotFoundException("Ingredient not found with the given name!");
        }
        Recipes recipe = Recipes.builder()
                .coffee(coffee)
                .ingredients(ingredients)
                .build();
        coffee.setRecipe(recipe);

        return ResponseEntity.ok(recipeService.saveRecipe(recipe));
    }
}
