package com.coffe.services.implementation;

import com.coffe.entity.Recipes;
import com.coffe.repository.RecipeRepository;
import com.coffe.services.declaration.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class RecipesServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipesServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipes saveRecipe(Recipes recipe) {
        return recipeRepository.save(recipe);
    }
}
