package com.app.music.repository;

import com.app.music.entity.Album;
import com.app.music.projection.AlbumComArtistaProjection;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    // 🔹 LISTAR TODOS COM ARTISTA (SQL LEGADO CORRETO)
    @Query(value = """
        SELECT 
            a.id,
            a.titulo,
            a.ano_lancamento AS anoLancamento,
            a.tipo,
            a.gravadora,
            a.descricao,
            a.duracao_total AS duracaoTotal,
            a.numero_faixas AS numeroFaixas,
            a.preco,
            ar.id AS artistaId,
            ar.nome AS artistaNome
        FROM albuns a
        JOIN artistas ar ON ar.id = a.artista_id
        """, nativeQuery = true)
    List<AlbumComArtistaProjection> listarTodos();

    // 🔹 BUSCAR POR ID
    @Query(value = """
        SELECT 
            a.id,
            a.titulo,
            a.ano_lancamento AS anoLancamento,
            a.tipo,
            a.gravadora,
            a.descricao,
            a.duracao_total AS duracaoTotal,
            a.numero_faixas AS numeroFaixas,
            a.preco,
            ar.id AS artistaId,
            ar.nome AS artistaNome
        FROM albuns a
        JOIN artistas ar ON ar.id = a.artista_id
        WHERE a.id = :id
        """, nativeQuery = true)
    AlbumComArtistaProjection buscarPorId(@Param("id") Long id);

    // 🔹 LISTAR POR ARTISTA
    @Query(value = """
        SELECT 
            a.id,
            a.titulo,
            a.ano_lancamento AS anoLancamento,
            a.tipo,
            a.gravadora,
            a.descricao,
            a.duracao_total AS duracaoTotal,
            a.numero_faixas AS numeroFaixas,
            a.preco,
            ar.id AS artistaId,
            ar.nome AS artistaNome
        FROM albuns a
        JOIN artistas ar ON ar.id = a.artista_id
        WHERE ar.id = :artistaId
        """, nativeQuery = true)
    List<AlbumComArtistaProjection> listarPorArtista(@Param("artistaId") Long artistaId);

    // 🔹 INSERT LEGADO
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO albuns
        (titulo, ano_lancamento, tipo, gravadora, descricao, numero_faixas, preco, artista_id)
        VALUES
        (:titulo, :anoLancamento, :tipo, :gravadora, :descricao, :numeroFaixas, :preco, :artistaId)
        """, nativeQuery = true)
    void inserir(
            @Param("titulo") String titulo,
            @Param("anoLancamento") Integer anoLancamento,
            @Param("tipo") String tipo,
            @Param("gravadora") String gravadora,
            @Param("descricao") String descricao,
            @Param("numeroFaixas") Integer numeroFaixas,
            @Param("preco") Double preco,
            @Param("artistaId") Long artistaId
    );
}
