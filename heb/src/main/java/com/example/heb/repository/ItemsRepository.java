package com.example.heb.repository;

import com.example.heb.entity.Items;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends CrudRepository<Items, Long> {


}
