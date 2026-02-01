package com.app.music.integration;

import com.app.music.regional.dto.RegionalDTO;
import java.util.List;

public interface ArgusClient {
    List<RegionalDTO> buscarRegionais();
}
