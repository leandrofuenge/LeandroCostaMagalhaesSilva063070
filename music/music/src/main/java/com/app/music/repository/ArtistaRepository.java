package com.app.music.repository;

import com.app.music.entity.Artista;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    // 🔹 LISTAR TODOS
    @Query(value = "SELECT * FROM artistas", nativeQuery = true)
    List<Artista> listarTodos();

    // 🔹 BUSCAR POR ID
    @Query(value = "SELECT * FROM artistas WHERE id = :id", nativeQuery = true)
    Artista buscarPorId(@Param("id") Long id);

    // 🔹 INSERT LEGADO
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO artistas
        (nome, descricao, data_nascimento, tipo, pais_origem, website, regional_id)
        VALUES
        (:nome, :descricao, :dataNascimento, :tipo, :paisOrigem, :website, :regionalId)
        """, nativeQuery = true)
    void inserir(
            @Param("nome") String nome,
            @Param("descricao") String descricao,
            @Param("dataNascimento") java.time.LocalDate dataNascimento,
            @Param("tipo") String tipo,
            @Param("paisOrigem") String paisOrigem,
            @Param("website") String website,
            @Param("regionalId") Long regionalId
    );

    // 🔹 UPDATE LEGADO
    @Modifying
    @Transactional
    @Query(value = """
        UPDATE artistas SET
            nome = :nome,
            descricao = :descricao,
            data_nascimento = :dataNascimento,
            tipo = :tipo,
            pais_origem = :paisOrigem,
            website = :website
        WHERE id = :id
        """, nativeQuery = true)
    void atualizar(
            @Param("id") Long id,
            @Param("nome") String nome,
            @Param("descricao") String descricao,
            @Param("dataNascimento") java.time.LocalDate dataNascimento,
            @Param("tipo") String tipo,
            @Param("paisOrigem") String paisOrigem,
            @Param("website") String website
    );
}
