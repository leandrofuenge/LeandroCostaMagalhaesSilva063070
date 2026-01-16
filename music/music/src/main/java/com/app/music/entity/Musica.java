package com.app.music.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

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
    
    @Column(name = "duracao_segundos")  // snake_case
    private Integer duracaoSegundos; // duração em segundos
    
    @Column(name = "numero_faixa")  // snake_case
    private Integer numeroFaixa;
    
    @Column(length = 50)
    private String formato; // MP3, FLAC, WAV, etc.
    
    @Column(name = "vezes_tocada")  // snake_case
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
    
    // Setter correto para album - **CORRIGIDO**
    public void setAlbum(Album album) {
        this.album = album;
    }
    
    // Getter correto - **CORRIGIDO**
    public Integer getDuracaoSegundos() {
        return duracaoSegundos;
    }
    
    // Método para obter duração formatada
    @Transient
    public String getDuracaoFormatada() {
        if (duracaoSegundos == null) return "0:00";
        int minutos = duracaoSegundos / 60;
        int segundos = duracaoSegundos % 60;
        return String.format("%d:%02d", minutos, segundos);
    }
}