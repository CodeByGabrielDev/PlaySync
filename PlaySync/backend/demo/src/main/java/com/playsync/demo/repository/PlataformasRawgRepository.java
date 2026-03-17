package com.playsync.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.playsync.demo.Entities.PlataformasRawg;
@Repository
public interface PlataformasRawgRepository extends CrudRepository<PlataformasRawg,Long>{
    
}
