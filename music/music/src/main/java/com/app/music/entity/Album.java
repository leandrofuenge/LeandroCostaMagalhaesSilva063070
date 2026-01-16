package com.app.music.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "albuns")  // 'albuns' (plural irregular em português)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String titulo;
    
    @Column(name = "ano_lancamento", nullable = false)  // snake_case para coluna
    private Integer anoLancamento;
    
    @Column(length = 50)
    private String tipo; // STUDIO, AO_VIVO, EP, COMPILACAO
    
    @Column(length = 100)
    private String gravadora;
    
    @Column(length = 500)
    private String descricao;
    
    @Column(name = "duracao_total")  // snake_case
    private Integer duracaoTotal; // em segundos
    
    @Column(name = "numero_faixas")  // snake_case
    private Integer numeroFaixas;
    
    @Column(name = "capa_url", length = 200)  // snake_case
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
    
    // Método helper para adicionar música - **CORRIGIDO**
    public void addMusica(Musica musica) {
        if (!musicas.contains(musica)) {
            musicas.add(musica);
            musica.setAlbum(this);
        }
    }
    
    // Método helper para remover música - **CORRIGIDO**
    public void removeMusica(Musica musica) {
        if (musicas.contains(musica)) {
            musicas.remove(musica);
            musica.setAlbum(null);
        }
    }
    
    // Setter correto para artista - **CORRIGIDO**
    public void setArtista(Artista artista) {
        this.artista = artista;
    }
    
    // Calcula duração total automaticamente
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
}