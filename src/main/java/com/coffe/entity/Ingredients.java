package com.coffe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ingredients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ingredients {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 50)
    private String name;
    @Column(nullable = false)
    @Min(value = 1)
    @Max(value = 30)
    private double price;
    @Column(nullable = false)
    @Min(value = 0)
    @Max(value = 5000)
    private int quantity;

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    @JoinTable(name = "ingredient_recipe", joinColumns = @JoinColumn(name = "ingredient_id"), inverseJoinColumns = @JoinColumn(name = "recipe_id"))
    private Set<Recipes> recipes;

    public void addRecipe(Recipes recipe) {
        if (this.recipes == null) {
            this.recipes = new HashSet<>();
        }
        this.recipes.add(recipe);
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
