package com.app.music.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "generos")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}