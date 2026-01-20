package com.app.music.repository;

import com.app.music.entity.Album;
import com.app.music.projection.AlbumComArtistaProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    // 🔹 LISTAR TODOS COM ARTISTA (SQL LEGADO)
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

    // 🔹 UPDATE LEGADO ✅ (ADICIONADO)
    @Modifying
    @Transactional
    @Query(value = """
        UPDATE albuns
           SET titulo = :titulo,
               ano_lancamento = :anoLancamento,
               tipo = :tipo,
               gravadora = :gravadora,
               descricao = :descricao,
               numero_faixas = :numeroFaixas,
               preco = :preco
         WHERE id = :id
        """, nativeQuery = true)
    void atualizar(
            @Param("id") Long id,
            @Param("titulo") String titulo,
            @Param("anoLancamento") Integer anoLancamento,
            @Param("tipo") String tipo,
            @Param("gravadora") String gravadora,
            @Param("descricao") String descricao,
            @Param("numeroFaixas") Integer numeroFaixas,
            @Param("preco") Double preco
    );
    
    @Query(
    	    value = """
    	        SELECT 
    	            a.id AS id,
    	            a.titulo AS titulo,
    	            a.anoLancamento AS anoLancamento,
    	            a.tipo AS tipo,
    	            a.gravadora AS gravadora,
    	            a.descricao AS descricao,
    	            a.duracaoTotal AS duracaoTotal,
    	            a.numeroFaixas AS numeroFaixas,
    	            a.preco AS preco,
    	            ar.id AS artistaId,
    	            ar.nome AS artistaNome
    	        FROM Album a
    	        JOIN a.artista ar
    	    """,
    	    countQuery = """
    	        SELECT COUNT(a)
    	        FROM Album a
    	    """
    	)
    	Page<AlbumComArtistaProjection> listarPaginado(Pageable pageable
    	
    );
    
    @Query(
    	    value = """
    	        SELECT 
    	            a.id AS id,
    	            a.titulo AS titulo,
    	            a.anoLancamento AS anoLancamento,
    	            a.tipo AS tipo,
    	            a.gravadora AS gravadora,
    	            a.descricao AS descricao,
    	            a.duracaoTotal AS duracaoTotal,
    	            a.numeroFaixas AS numeroFaixas,
    	            a.preco AS preco,
    	            ar.id AS artistaId,
    	            ar.nome AS artistaNome
    	        FROM Album a
    	        JOIN a.artista ar
    	        WHERE UPPER(ar.tipo) = UPPER(:tipo)
    	    """,
    	    countQuery = """
    	        SELECT COUNT(a)
    	        FROM Album a
    	        JOIN a.artista ar
    	        WHERE UPPER(ar.tipo) = UPPER(:tipo)
    	    """
    	)
    	Page<AlbumComArtistaProjection> listarPorTipoArtista(
    	    @Param("tipo") String tipo,
    	    Pageable pageable
    	);

    @Query("""
    	    SELECT 
    	        a.id AS id,
    	        a.titulo AS titulo,
    	        a.anoLancamento AS anoLancamento,
    	        a.tipo AS tipo,
    	        a.gravadora AS gravadora,
    	        a.descricao AS descricao,
    	        a.duracaoTotal AS duracaoTotal,
    	        a.numeroFaixas AS numeroFaixas,
    	        a.preco AS preco,
    	        ar.id AS artistaId,
    	        ar.nome AS artistaNome
    	    FROM Album a
    	    JOIN a.artista ar
    	    WHERE LOWER(ar.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
    	""")
    	Page<AlbumComArtistaProjection> buscarPorNomeArtista(
    	    @Param("nome") String nome,
    	    Pageable pageable
    	);


}   
            