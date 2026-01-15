package com.app.music.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "regionais")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}