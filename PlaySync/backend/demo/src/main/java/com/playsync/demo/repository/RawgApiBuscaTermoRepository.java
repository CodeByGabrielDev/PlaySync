package com.playsync.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.playsync.demo.Entities.ItensBuscadorPeloTermo;
import com.playsync.demo.Entities.RawgApiBuscaTermo;
import com.playsync.demo.Entities.TotalItensBuscadosRawg;

@Repository
public interface RawgApiBuscaTermoRepository extends CrudRepository<RawgApiBuscaTermo,Long>{
    @Query("SELECT E FROM RawgApiBuscaTermo E WHERE LOWER(E.nome) LIKE %:term%")
	List<RawgApiBuscaTermo> selectByName(@Param("term") String term);
}
