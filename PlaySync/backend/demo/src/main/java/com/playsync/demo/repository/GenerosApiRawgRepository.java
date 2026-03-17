package com.playsync.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.playsync.demo.Entities.GenerosApiRawg;
@Repository
public interface GenerosApiRawgRepository extends CrudRepository<GenerosApiRawg,Long>{
    
}
