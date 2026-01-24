package com.app.music.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 500)
    private String descricao;

    private LocalDate dataNascimento;

    @Column(length = 50)
    private String tipo;

    @Column(length = 100)
    private String paisOrigem;

    @Column(length = 100)
    private String website;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "regional_id")
    private Regional regional;

    @ManyToMany
    @JoinTable(
        name = "artista_generos",
        joinColumns = @JoinColumn(name = "artista_id"),
        inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genero> generos = new ArrayList<>();

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albuns = new ArrayList<>();

    // ============ CONSTRUTORES ============
    public Artista() {
    }

    public Artista(Long id, String nome, String descricao, LocalDate dataNascimento, 
                   String tipo, String paisOrigem, String website, Regional regional, 
                   List<Genero> generos, List<Album> albuns) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataNascimento = dataNascimento;
        this.tipo = tipo;
        this.paisOrigem = paisOrigem;
        this.website = website;
        this.regional = regional;
        this.generos = generos != null ? generos : new ArrayList<>();
        this.albuns = albuns != null ? albuns : new ArrayList<>();
    }

    // ============ GETTERS ============
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public String getWebsite() {
        return website;
    }

    public Regional getRegional() {
        return regional;
    }

    public List<Genero> getGeneros() {
        return generos;
    }

    public List<Album> getAlbuns() {
        return albuns;
    }

    // ============ SETTERS ============
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPaisOrigem(String paisOrigem) {
        this.paisOrigem = paisOrigem;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setRegional(Regional regional) {
        this.regional = regional;
    }

    public void setGeneros(List<Genero> generos) {
        this.generos = generos != null ? generos : new ArrayList<>();
    }

    public void setAlbuns(List<Album> albuns) {
        this.albuns = albuns != null ? albuns : new ArrayList<>();
    }

    // ============ MÉTODOS HELPER ============
    public void addGenero(Genero genero) {
        if (generos == null) {
            generos = new ArrayList<>();
        }
        if (genero != null && !generos.contains(genero)) {
            generos.add(genero);
        }
    }

    public void removeGenero(Genero genero) {
        if (generos != null && genero != null) {
            generos.remove(genero);
        }
    }

    public void addAlbum(Album album) {
        if (albuns == null) {
            albuns = new ArrayList<>();
        }
        if (album != null && !albuns.contains(album)) {
            albuns.add(album);
            album.setArtista(this);
        }
    }

    public void removeAlbum(Album album) {
        if (albuns != null && album != null) {
            albuns.remove(album);
            album.setArtista(null);
        }
    }

    // ============ toString() ============
    @Override
    public String toString() {
        return "Artista{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}