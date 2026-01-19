package com.app.music.dto;

import java.time.LocalDate;

public class ArtistaResponse {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataNascimento;
    private String tipo;
    private String paisOrigem;
    private String website;
    private Long regionalId;

    public ArtistaResponse(
            Long id,
            String nome,
            String descricao,
            LocalDate dataNascimento,
            String tipo,
            String paisOrigem,
            String website,
            Long regionalId
    ) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataNascimento = dataNascimento;
        this.tipo = tipo;
        this.paisOrigem = paisOrigem;
        this.website = website;
        this.regionalId = regionalId;
    }

    // getters
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public String getTipo() { return tipo; }
    public String getPaisOrigem() { return paisOrigem; }
    public String getWebsite() { return website; }
    public Long getRegionalId() { return regionalId; }
}
