-- Tabela de Regionais
CREATE TABLE regionais (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL UNIQUE,
    pais VARCHAR(100),
    continente VARCHAR(100),
    descricao VARCHAR(500),
    idioma_principal VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de Gêneros
CREATE TABLE generos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao VARCHAR(500),
    origem VARCHAR(100),
    decada_popularizacao VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de Artistas
CREATE TABLE artistas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    data_nascimento DATE,
    tipo VARCHAR(50),
    pais_origem VARCHAR(100),
    website VARCHAR(100),
    regional_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (regional_id) REFERENCES regionais(id)
);

-- Tabela de Álbuns
CREATE TABLE albuns (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    ano_lancamento INT NOT NULL,
    tipo VARCHAR(50),
    gravadora VARCHAR(100),
    descricao VARCHAR(500),
    duracao_total INT,
    numero_faixas INT,
    capa_url VARCHAR(200),
    preco DECIMAL(10,2),
    artista_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (artista_id) REFERENCES artistas(id)
);

-- Tabela de Músicas
CREATE TABLE musicas (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    duracao_segundos INT,
    numero_faixa INT,
    formato VARCHAR(50),
    vezes_tocada INT DEFAULT 0,
    letra TEXT,
    album_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (album_id) REFERENCES albuns(id)
);

-- Tabelas de relacionamento muitos-para-muitos

-- Artista - Gênero
CREATE TABLE artista_generos (
    artista_id BIGINT NOT NULL,
    genero_id BIGINT NOT NULL,
    PRIMARY KEY (artista_id, genero_id),
    FOREIGN KEY (artista_id) REFERENCES artistas(id) ON DELETE CASCADE,
    FOREIGN KEY (genero_id) REFERENCES generos(id) ON DELETE CASCADE
);

-- Álbum - Gênero
CREATE TABLE album_generos (
    album_id BIGINT NOT NULL,
    genero_id BIGINT NOT NULL,
    PRIMARY KEY (album_id, genero_id),
    FOREIGN KEY (album_id) REFERENCES albuns(id) ON DELETE CASCADE,
    FOREIGN KEY (genero_id) REFERENCES generos(id) ON DELETE CASCADE
);

-- Música - Gênero
CREATE TABLE musica_generos (
    musica_id BIGINT NOT NULL,
    genero_id BIGINT NOT NULL,
    PRIMARY KEY (musica_id, genero_id),
    FOREIGN KEY (musica_id) REFERENCES musicas(id) ON DELETE CASCADE,
    FOREIGN KEY (genero_id) REFERENCES generos(id) ON DELETE CASCADE
);

-- Índices para melhor performance
CREATE INDEX idx_artistas_nome ON artistas(nome);
CREATE INDEX idx_albuns_titulo ON albuns(titulo);
CREATE INDEX idx_albuns_artista_id ON albuns(artista_id);
CREATE INDEX idx_musicas_titulo ON musicas(titulo);
CREATE INDEX idx_musicas_album_id ON musicas(album_id);
CREATE INDEX idx_generos_nome ON generos(nome);