package com.app.music.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class ArtistaRequest {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    private LocalDate dataNascimento;

    @NotBlank(message = "Tipo é obrigatório")
    private String tipo;

    @NotBlank(message = "País de origem é obrigatório")
    @Size(max = 100, message = "País de origem deve ter no máximo 100 caracteres")
    private String paisOrigem;

    @Size(max = 100, message = "Website deve ter no máximo 100 caracteres")
    private String website;

    private Long regionalId;

    private List<Long> generosIds;

    // getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getPaisOrigem() { return paisOrigem; }
    public void setPaisOrigem(String paisOrigem) { this.paisOrigem = paisOrigem; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public Long getRegionalId() { return regionalId; }
    public void setRegionalId(Long regionalId) { this.regionalId = regionalId; }

    public List<Long> getGenerosIds() { return generosIds; }
    public void setGenerosIds(List<Long> generosIds) { this.generosIds = generosIds; }
}
