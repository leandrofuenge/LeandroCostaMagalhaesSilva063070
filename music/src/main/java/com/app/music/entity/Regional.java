package com.app.music.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "regionais")
public class Regional {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true, length = 50)
    private String nome;
    
    @Column(length = 100)
    private String pais;
    
    @Column(length = 100)
    private String continente;
    
    @Column(length = 500)
    private String descricao;
    
    @Column(length = 100)
    private String idiomaPrincipal;
    
    @OneToMany(mappedBy = "regional")
    private List<Artista> artistas = new ArrayList<>();
    
    public Regional() {
    }
    
    public Regional(Long id, String nome, String pais, String continente, 
                   String descricao, String idiomaPrincipal, List<Artista> artistas) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
        this.continente = continente;
        this.descricao = descricao;
        this.idiomaPrincipal = idiomaPrincipal;
        this.artistas = artistas != null ? artistas : new ArrayList<>();
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
    
    public String getPais() {
        return pais;
    }
    
    public void setPais(String pais) {
        this.pais = pais;
    }
    
    public String getContinente() {
        return continente;
    }
    
    public void setContinente(String continente) {
        this.continente = continente;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getIdiomaPrincipal() {
        return idiomaPrincipal;
    }
    
    public void setIdiomaPrincipal(String idiomaPrincipal) {
        this.idiomaPrincipal = idiomaPrincipal;
    }
    
    public List<Artista> getArtistas() {
        return artistas;
    }
    
    public void setArtistas(List<Artista> artistas) {
        this.artistas = artistas != null ? artistas : new ArrayList<>();
    }
    
    public void addArtista(Artista artista) {
        if (!artistas.contains(artista)) {
            artistas.add(artista);
            artista.setRegional(this);
        }
    }
    
    public void removeArtista(Artista artista) {
        if (artistas.contains(artista)) {
            artistas.remove(artista);
            artista.setRegional(null);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Regional regional = (Regional) o;
        return id != null && id.equals(regional.id);
    }
    
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {
        return "Regional{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               ", pais='" + pais + '\'' +
               ", continente='" + continente + '\'' +
               '}';
    }
}