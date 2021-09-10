package com.coffe.repository;

import com.coffe.entity.Ingredients;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredients, Long> {
    Optional<Ingredients> getIngredientsByNameIgnoreCase(String name);
}
