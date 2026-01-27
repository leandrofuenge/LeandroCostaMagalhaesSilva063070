package com.app.music.projection;

import java.math.BigDecimal;

public interface AlbumComArtistaProjection {

    Long getId();
    String getTitulo();
    Integer getAnoLancamento();
    String getTipo();
    String getGravadora();
    String getDescricao();
    Integer getDuracaoTotal();
    Integer getNumeroFaixas();
    BigDecimal getPreco();

    Long getArtistaId();
    String getArtistaNome();
}
