package com.coffe.repository;

import com.coffe.entity.Coffee;
import com.coffe.shared.enumeration.Dimension;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
    Optional<Coffee> findCoffeeByNameIgnoreCaseAndDimension(String name, Dimension dimension);

    List<Coffee> findCoffeeByNameIgnoreCase(String name);
}
