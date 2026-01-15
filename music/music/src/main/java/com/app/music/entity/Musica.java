package com.app.music.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "musicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Musica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String titulo;
    
    private Integer duracaoSegundos; // duração em segundos
    
    @Column(name = "numero_faixa")
    private Integer numeroFaixa;
    
    @Column(length = 50)
    private String formato; // MP3, FLAC, WAV, etc.
    
    private Integer vezesTocada = 0;
    
    @Column(length = 500)
    private String letra;
    
    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;
    
    @ManyToMany
    @JoinTable(
        name = "musica_generos",
        joinColumns = @JoinColumn(name = "musica_id"),
        inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genero> generos = new ArrayList<>();
    
    // Método para obter duração formatada
    @Transient
    public String getDuracaoFormatada() {
        int minutos = duracaoSegundos / 60;
        int segundos = duracaoSegundos % 60;
        return String.format("%d:%02d", minutos, segundos);
    }
}