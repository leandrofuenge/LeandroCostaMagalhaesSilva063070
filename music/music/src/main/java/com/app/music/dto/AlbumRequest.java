package com.app.music.dto;

import java.math.BigDecimal;

public class AlbumRequest {

    private String titulo;
    private Integer anoLancamento;
    private String tipo;
    private String gravadora;
    private String descricao;
    private Integer numeroFaixas;
    private BigDecimal preco;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGravadora() {
        return gravadora;
    }

    public void setGravadora(String gravadora) {
        this.gravadora = gravadora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getNumeroFaixas() {
        return numeroFaixas;
    }

    public void setNumeroFaixas(Integer numeroFaixas) {
        this.numeroFaixas = numeroFaixas;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
}
