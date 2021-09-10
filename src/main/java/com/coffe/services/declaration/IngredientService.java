package com.coffe.services.declaration;

import com.coffe.entity.Ingredients;
import com.coffe.exception.IngredientNotFoundException;

import java.util.List;

public interface IngredientService {
    Ingredients saveIngredient(Ingredients ingredients);
    Ingredients getIngredientByName(String name) throws IngredientNotFoundException;
    List<Ingredients> getAllIngredients();
}
