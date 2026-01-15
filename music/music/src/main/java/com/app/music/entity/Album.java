package com.app.music.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albuns")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String titulo;
    
    @Column(nullable = false)
    private Integer anoLancamento;
    
    @Column(length = 50)
    private String tipo; // STUDIO, AO_VIVO, EP, COMPILACAO
    
    @Column(length = 100)
    private String gravadora;
    
    @Column(length = 500)
    private String descricao;
    
    private Integer duracaoTotal; // em segundos
    
    private Integer numeroFaixas;
    
    @Column(length = 200)
    private String capaUrl;
    
    private Double preco;
    
    @ManyToOne
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
    
    // Método helper para adicionar música
    public void addMusica(Musica musica) {
        musicas.add(musica);
        musica.setAlbum(this);
    }
    
    // Método helper para remover música
    public void removeMusica(Musica musica) {
        musicas.remove(musica);
        musica.setAlbum(null);
    }
    
    // Calcula duração total automaticamente
    @PostLoad
    @PostPersist
    @PostUpdate
    public void calcularDuracaoTotal() {
        this.duracaoTotal = musicas.stream()
            .mapToInt(Musica::getDuracaoSegundos)
            .sum();
        this.numeroFaixas = musicas.size();
    }
}