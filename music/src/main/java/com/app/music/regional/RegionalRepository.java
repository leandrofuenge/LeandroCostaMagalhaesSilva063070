package com.app.music.regional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RegionalRepository extends JpaRepository<RegionalEntity, Long> {


    List<RegionalEntity> findByAtivoTrue();


    Optional<RegionalEntity> findByExternalIdAndAtivoTrue(Integer externalId);

   
    List<RegionalEntity> findByExternalId(Integer externalId);

    @Modifying
    @Query("""
        UPDATE RegionalEntity r
           SET r.ativo = false
         WHERE r.ativo = true
           AND r.externalId NOT IN :externalIds
    """)
    int inativarRegionaisAusentes(@Param("externalIds") List<Integer> externalIds);

    @Modifying
    @Query("""
        UPDATE RegionalEntity r
           SET r.ativo = false
         WHERE r.externalId = :externalId
           AND r.ativo = true
    """)
    int inativarPorExternalId(@Param("externalId") Integer externalId);
}