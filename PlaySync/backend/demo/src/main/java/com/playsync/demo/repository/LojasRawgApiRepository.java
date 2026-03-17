package com.playsync.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.playsync.demo.Entities.LojasRawgApi;
@Repository
public interface LojasRawgApiRepository extends CrudRepository<LojasRawgApi,Long>{
    
}
