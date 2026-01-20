package com.app.music.dto;

import java.math.BigDecimal;

import com.app.music.entity.Album;
import com.app.music.entity.Artista;

public class AlbumResponse {

    private Long id;
    private String titulo;
    private Integer anoLancamento;
    private String tipo;
    private String gravadora;
    private String descricao;
    private Integer duracaoTotal;
    private Integer numeroFaixas;
    private BigDecimal preco;
    private Long artistaId;
    private String artistaNome;

    public AlbumResponse() {}

    public AlbumResponse(
            Long id,
            String titulo,
            Integer anoLancamento,
            String tipo,
            String gravadora,
            String descricao,
            Integer duracaoTotal,
            Integer numeroFaixas,
            BigDecimal preco,
            Long artistaId,
            String artistaNome
    ) {
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.tipo = tipo;
        this.gravadora = gravadora;
        this.descricao = descricao;
        this.duracaoTotal = duracaoTotal;
        this.numeroFaixas = numeroFaixas;
        this.preco = preco;
        this.artistaId = artistaId;
        this.artistaNome = artistaNome;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public String getTipo() {
        return tipo;
    }

    public String getGravadora() {
        return gravadora;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getDuracaoTotal() {
        return duracaoTotal;
    }

    public Integer getNumeroFaixas() {
        return numeroFaixas;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Long getArtistaId() {
        return artistaId;
    }

    public String getArtistaNome() {
        return artistaNome;
    }
    
    public static AlbumResponse fromEntity(Album album) {
        Artista artista = album.getArtista();

        return new AlbumResponse(
                album.getId(),
                album.getTitulo(),
                album.getAnoLancamento(),
                album.getTipo(),
                album.getGravadora(),
                album.getDescricao(),
                album.getDuracaoTotal(),
                album.getNumeroFaixas(),
                album.getPreco(),
                artista != null ? artista.getId() : null,
                artista != null ? artista.getNome() : null
        );
    }
   
}
