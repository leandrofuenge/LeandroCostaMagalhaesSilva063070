package com.app.music.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albuns")
public class Album {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String titulo;
    
    @Column(name = "ano_lancamento", nullable = false)
    private Integer anoLancamento;
    
    @Column(length = 50)
    private String tipo;
    
    @Column(length = 100)
    private String gravadora;
    
    @Column(length = 500)
    private String descricao;
    
    @Column(name = "duracao_total")
    private Integer duracaoTotal;
    
    @Column(name = "numero_faixas")
    private Integer numeroFaixas;
    
    @Column(name = "capa_url", length = 200)
    private String capaUrl;
    
    private BigDecimal preco;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artista_id", nullable = false)
    private Artista artista;
    
    @ManyToMany
    @JoinTable(
        name = "album_generos",
        joinColumns = @JoinColumn(name = "album_id"),
        inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genero> generos = new ArrayList<>();
    
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("numeroFaixa ASC")
    private List<Musica> musicas = new ArrayList<>();
    
    public Album() {
    }
    
    public Album(Long id, String titulo, Integer anoLancamento, String tipo, String gravadora, 
                 String descricao, Integer duracaoTotal, Integer numeroFaixas, String capaUrl, 
                 BigDecimal preco, Artista artista, List<Genero> generos, List<Musica> musicas) {
        this.id = id;
        this.titulo = titulo;
        this.anoLancamento = anoLancamento;
        this.tipo = tipo;
        this.gravadora = gravadora;
        this.descricao = descricao;
        this.duracaoTotal = duracaoTotal;
        this.numeroFaixas = numeroFaixas;
        this.capaUrl = capaUrl;
        this.preco = preco;
        this.artista = artista;
        this.generos = generos != null ? generos : new ArrayList<>();
        this.musicas = musicas != null ? musicas : new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public Integer getDuracaoTotal() {
        return duracaoTotal;
    }
    
    public void setDuracaoTotal(Integer duracaoTotal) {
        this.duracaoTotal = duracaoTotal;
    }
    
    public Integer getNumeroFaixas() {
        return numeroFaixas;
    }
    
    public void setNumeroFaixas(Integer numeroFaixas) {
        this.numeroFaixas = numeroFaixas;
    }
    
    public String getCapaUrl() {
        return capaUrl;
    }
    
    public void setCapaUrl(String capaUrl) {
        this.capaUrl = capaUrl;
    }
    
    public BigDecimal getPreco() {
        return preco;
    }
    
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    
    public Artista getArtista() {
        return artista;
    }
    
    public void setArtista(Artista artista) {
        this.artista = artista;
    }
    
    public List<Genero> getGeneros() {
        return generos;
    }
    
    public void setGeneros(List<Genero> generos) {
        this.generos = generos != null ? generos : new ArrayList<>();
    }
    
    public List<Musica> getMusicas() {
        return musicas;
    }
    
    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas != null ? musicas : new ArrayList<>();
    }
    
    public void addMusica(Musica musica) {
        if (!musicas.contains(musica)) {
            musicas.add(musica);
            musica.setAlbum(this);
        }
    }
    
    public void removeMusica(Musica musica) {
        if (musicas.contains(musica)) {
            musicas.remove(musica);
            musica.setAlbum(null);
        }
    }
    
    public void addGenero(Genero genero) {
        if (!generos.contains(genero)) {
            generos.add(genero);
        }
    }
    
    public void removeGenero(Genero genero) {
        generos.remove(genero);
    }
    
    @PostLoad
    @PostPersist
    @PostUpdate
    private void calcularDuracaoTotal() {
        if (musicas != null && !musicas.isEmpty()) {
            this.duracaoTotal = musicas.stream()
                .mapToInt(m -> m.getDuracaoSegundos() != null ? m.getDuracaoSegundos() : 0)
                .sum();
            this.numeroFaixas = musicas.size();
        } else {
            this.duracaoTotal = 0;
            this.numeroFaixas = 0;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return id != null && id.equals(album.id);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "Album{" +
               "id=" + id +
               ", titulo='" + titulo + '\'' +
               ", anoLancamento=" + anoLancamento +
               '}';
    }
}