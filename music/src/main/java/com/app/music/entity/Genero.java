package com.app.music.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "generos")
public class Genero {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String nome;
    
    @Column(length = 500)
    private String descricao;
    
    @Column(length = 100)
    private String origem;
    
    @Column(length = 50)
    private String decadaPopularizacao;
    
    @ManyToMany(mappedBy = "generos")
    private List<Artista> artistas = new ArrayList<>();
    
    @ManyToMany(mappedBy = "generos")
    private List<Album> albuns = new ArrayList<>();
    
    @ManyToMany(mappedBy = "generos")
    private List<Musica> musicas = new ArrayList<>();
    
    public Genero() {
    }
    
    public Genero(Long id, String nome, String descricao, String origem, String decadaPopularizacao, 
                  List<Artista> artistas, List<Album> albuns, List<Musica> musicas) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.origem = origem;
        this.decadaPopularizacao = decadaPopularizacao;
        this.artistas = artistas != null ? artistas : new ArrayList<>();
        this.albuns = albuns != null ? albuns : new ArrayList<>();
        this.musicas = musicas != null ? musicas : new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getOrigem() {
        return origem;
    }
    
    public void setOrigem(String origem) {
        this.origem = origem;
    }
    
    public String getDecadaPopularizacao() {
        return decadaPopularizacao;
    }
    
    public void setDecadaPopularizacao(String decadaPopularizacao) {
        this.decadaPopularizacao = decadaPopularizacao;
    }
    
    public List<Artista> getArtistas() {
        return artistas;
    }
    
    public void setArtistas(List<Artista> artistas) {
        this.artistas = artistas != null ? artistas : new ArrayList<>();
    }
    
    public List<Album> getAlbuns() {
        return albuns;
    }
    
    public void setAlbuns(List<Album> albuns) {
        this.albuns = albuns != null ? albuns : new ArrayList<>();
    }
    
    public List<Musica> getMusicas() {
        return musicas;
    }
    
    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas != null ? musicas : new ArrayList<>();
    }
    
    public void addArtista(Artista artista) {
        if (!artistas.contains(artista)) {
            artistas.add(artista);
            if (!artista.getGeneros().contains(this)) {
                artista.getGeneros().add(this);
            }
        }
    }
    
    public void removeArtista(Artista artista) {
        artistas.remove(artista);
        artista.getGeneros().remove(this);
    }
    
    public void addAlbum(Album album) {
        if (!albuns.contains(album)) {
            albuns.add(album);
            if (!album.getGeneros().contains(this)) {
                album.getGeneros().add(this);
            }
        }
    }
    
    public void removeAlbum(Album album) {
        albuns.remove(album);
        album.getGeneros().remove(this);
    }
    
    public void addMusica(Musica musica) {
        if (!musicas.contains(musica)) {
            musicas.add(musica);
            if (!musica.getGeneros().contains(this)) {
                musica.getGeneros().add(this);
            }
        }
    }
    
    public void removeMusica(Musica musica) {
        musicas.remove(musica);
        musica.getGeneros().remove(this);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genero genero = (Genero) o;
        return id != null && id.equals(genero.id);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "Genero{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               '}';
    }
}