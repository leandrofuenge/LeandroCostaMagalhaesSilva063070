package com.app.music.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "musicas")
public class Musica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String titulo;
    
    @Column(name = "duracao_segundos")
    private Integer duracaoSegundos;
    
    @Column(name = "numero_faixa")
    private Integer numeroFaixa;
    
    @Column(length = 50)
    private String formato;
    
    @Column(name = "vezes_tocada")
    private Integer vezesTocada = 0;
    
    @Column(length = 500)
    private String letra;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;
    
    @ManyToMany
    @JoinTable(
        name = "musica_generos",
        joinColumns = @JoinColumn(name = "musica_id"),
        inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genero> generos = new ArrayList<>();
    
    public Musica() {
    }
    
    public Musica(Long id, String titulo, Integer duracaoSegundos, Integer numeroFaixa, 
                  String formato, Integer vezesTocada, String letra, Album album, 
                  List<Genero> generos) {
        this.id = id;
        this.titulo = titulo;
        this.duracaoSegundos = duracaoSegundos;
        this.numeroFaixa = numeroFaixa;
        this.formato = formato;
        this.vezesTocada = vezesTocada != null ? vezesTocada : 0;
        this.letra = letra;
        this.album = album;
        this.generos = generos != null ? generos : new ArrayList<>();
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
    
    public Integer getDuracaoSegundos() {
        return duracaoSegundos;
    }
    
    public void setDuracaoSegundos(Integer duracaoSegundos) {
        this.duracaoSegundos = duracaoSegundos;
    }
    
    public Integer getNumeroFaixa() {
        return numeroFaixa;
    }
    
    public void setNumeroFaixa(Integer numeroFaixa) {
        this.numeroFaixa = numeroFaixa;
    }
    
    public String getFormato() {
        return formato;
    }
    
    public void setFormato(String formato) {
        this.formato = formato;
    }
    
    public Integer getVezesTocada() {
        return vezesTocada;
    }
    
    public void setVezesTocada(Integer vezesTocada) {
        this.vezesTocada = vezesTocada != null ? vezesTocada : 0;
    }
    
    public String getLetra() {
        return letra;
    }
    
    public void setLetra(String letra) {
        this.letra = letra;
    }
    
    public Album getAlbum() {
        return album;
    }
    
    public void setAlbum(Album album) {
        this.album = album;
    }
    
    public List<Genero> getGeneros() {
        return generos;
    }
    
    public void setGeneros(List<Genero> generos) {
        this.generos = generos != null ? generos : new ArrayList<>();
    }
    
    public void addGenero(Genero genero) {
        if (!generos.contains(genero)) {
            generos.add(genero);
            if (!genero.getMusicas().contains(this)) {
                genero.getMusicas().add(this);
            }
        }
    }
    
    public void removeGenero(Genero genero) {
        generos.remove(genero);
        genero.getMusicas().remove(this);
    }
    
    public void incrementarVezesTocada() {
        if (this.vezesTocada == null) {
            this.vezesTocada = 1;
        } else {
            this.vezesTocada++;
        }
    }
    
    @Transient
    public String getDuracaoFormatada() {
        if (duracaoSegundos == null) return "0:00";
        int minutos = duracaoSegundos / 60;
        int segundos = duracaoSegundos % 60;
        return String.format("%d:%02d", minutos, segundos);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Musica musica = (Musica) o;
        return id != null && id.equals(musica.id);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "Musica{" +
               "id=" + id +
               ", titulo='" + titulo + '\'' +
               ", duracaoSegundos=" + duracaoSegundos +
               ", numeroFaixa=" + numeroFaixa +
               '}';
    }
}