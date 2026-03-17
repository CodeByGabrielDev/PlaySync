package com.playsync.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.playsync.demo.Entities.TotalItensBuscadosRawg;

@Repository
public interface TotalItensBuscadosRawgRepository extends CrudRepository<TotalItensBuscadosRawg,Long>{
    
}
