package com.coffe.controller;

import com.coffe.entity.Ingredients;
import com.coffe.services.declaration.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/save")
    public ResponseEntity<Ingredients> saveIngredients(@Valid @RequestBody Ingredients ingredient) {
        return ResponseEntity.ok(ingredientService.saveIngredient(ingredient));
    }
}
