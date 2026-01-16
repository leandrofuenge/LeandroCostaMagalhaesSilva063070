package com.app.music.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")  // Note: 'artistas' em português
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String tipo; // SOLO, BANDA, DUPLA
    
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
    
    // Método helper para adicionar álbum
    public void addAlbum(Album album) {
        albuns.add(album);
        album.setArtista(this);
    }
    
    // Método helper para remover álbum
    public void removeAlbum(Album album) {
        albuns.remove(album);
        album.setArtista(null);
    }
}