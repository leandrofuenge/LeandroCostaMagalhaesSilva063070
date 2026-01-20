package com.app.music.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class AlbumRequest {

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotNull(message = "Ano de lançamento é obrigatório")
    @Min(value = 1900, message = "Ano de lançamento inválido")
    private Integer anoLancamento;

    @NotBlank(message = "Tipo é obrigatório")
    private String tipo;

    @Size(max = 100, message = "Gravadora deve ter no máximo 100 caracteres")
    private String gravadora;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotNull(message = "Número de faixas é obrigatório")
    @Min(value = 1, message = "Número de faixas deve ser no mínimo 1")
    private Integer numeroFaixas;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = true, message = "Preço não pode ser negativo")
    private BigDecimal preco;

    // getters e setters
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public Integer getAnoLancamento() { return anoLancamento; }
    public void setAnoLancamento(Integer anoLancamento) { this.anoLancamento = anoLancamento; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getGravadora() { return gravadora; }
    public void setGravadora(String gravadora) { this.gravadora = gravadora; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Integer getNumeroFaixas() { return numeroFaixas; }
    public void setNumeroFaixas(Integer numeroFaixas) { this.numeroFaixas = numeroFaixas; }

    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
}
