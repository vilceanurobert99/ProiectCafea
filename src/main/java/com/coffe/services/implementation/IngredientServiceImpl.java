package com.coffe.services.implementation;

import com.coffe.entity.Ingredients;
import com.coffe.exception.IngredientNotFoundException;
import com.coffe.repository.IngredientRepository;
import com.coffe.services.declaration.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class IngredientServiceImpl implements IngredientService {
    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredients saveIngredient(Ingredients ingredients) {
        return ingredientRepository.save(ingredients);
    }

    @Override
    public Ingredients getIngredientByName(String name) throws IngredientNotFoundException {
        return this.ingredientRepository.getIngredientsByNameIgnoreCase(name) //
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found by the given name!"));
    }

    @Override
    public List<Ingredients> getAllIngredients() {
        return (List<Ingredients>) ingredientRepository.findAll();
    }
}
