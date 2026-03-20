package com.playsync.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.playsync.demo.Entities.CheapSharkLojasApi;

@Repository
public interface CheapSharkLojasApiRepository extends CrudRepository<CheapSharkLojasApi, Long> {

    @Query("SELECT E FROM CheapSharkLojasApi E")
    List<CheapSharkLojasApi> consultarTabela();
}
