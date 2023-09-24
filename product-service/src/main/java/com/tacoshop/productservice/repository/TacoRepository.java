package com.tacoshop.productservice.repository;

import com.tacoshop.productservice.model.Taco;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacoRepository extends MongoRepository<Taco, Integer> {

}
